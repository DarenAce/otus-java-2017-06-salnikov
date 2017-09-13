package ru.otus.cache;

import java.lang.ref.*;
import java.util.*;
import java.util.function.*;

public class SoftRefCacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<CacheElement<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public SoftRefCacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    @Override
    public void put(CacheElement<K, V> element) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        K key = element.getKey();
        elements.put(key, new SoftReference<>(element));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    @Override
    public V get(K key) {
        SoftReference<CacheElement<K, V>> elementReference = elements.get(key);
        if (elementReference == null) {
            miss++;
            return null;
        }

        CacheElement<K, V> element = elementReference.get();
        if (element != null) {
            hit++;
            element.setAccessed();
            return element.getValue();
        } else {
            miss++;
            elements.remove(key);
            return null;
        }
    }

    @Override
    public List<V> getAll() {
        List<V> result = new ArrayList<>();
        elements.values().forEach(value -> {
            if (value != null && value.get() != null) {
                result.add(value.get().getValue());
            }
        });
        return result;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                SoftReference<CacheElement<K, V>> elementReference = elements.get(key);
                if (elementReference == null || elementReference.get() == null
                        || isT1BeforeT2(timeFunction.apply(elementReference.get()), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}

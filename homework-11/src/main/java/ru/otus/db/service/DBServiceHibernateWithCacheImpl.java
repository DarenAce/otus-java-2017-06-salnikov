package ru.otus.db.service;

import org.hibernate.*;
import org.hibernate.boot.registry.*;
import org.hibernate.cfg.*;
import org.hibernate.service.*;
import ru.otus.cache.*;
import ru.otus.db.dao.*;
import ru.otus.db.dataset.*;

import java.util.List;
import java.util.function.Function;

public class DBServiceHibernateWithCacheImpl implements DBService {
    private final SessionFactory sessionFactory;
    private final CacheEngine<Long, UserDataSet> userCache;

    public DBServiceHibernateWithCacheImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        sessionFactory = createSessionFactory(configuration);
        userCache = new SoftRefCacheEngineImpl<>(100, 60_000, 15_000, false);
    }

    public DBServiceHibernateWithCacheImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
        userCache = new SoftRefCacheEngineImpl<>(100, 60_000, 15_000, false);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public void saveUser(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            UserDAO dao = new UserDAO(session);
            dao.save(dataSet);
        }
        userCache.put(new CacheElement<>(dataSet.getId(), dataSet));
    }

    @Override
    public UserDataSet getUser(long id) {
        UserDataSet user = userCache.get(id);
        if (user == null) {
            user = runInSession(session -> {
                UserDAO dao = new UserDAO(session);
                return dao.load(id);
            });
        }
        return user;
    }

    @Override
    public UserDataSet getUserByName(String name) {
        List<UserDataSet> users = userCache.getAll();
        UserDataSet result = null;
        for (UserDataSet user : users) {
            if (user.getName().equals(name)) {
                result = user;
                break;
            }
        }
        if (result == null) {
            result = runInSession(session -> {
                UserDAO dao = new UserDAO(session);
                return dao.loadByName(name);
            });
        }
        return result;
    }

    @Override
    public List<UserDataSet> getAllUsers() {
        List<UserDataSet> users;
        users = runInSession(session -> {
            UserDAO dao = new UserDAO(session);
            return dao.loadAll();
        });
        users.forEach(user -> userCache.put(new CacheElement<>(user.getId(), user)));
        return users;
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
        userCache.dispose();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    public long getCacheHitCount() {
        return userCache.getHitCount();
    }

    public long getCacheMissCount() {
        return userCache.getMissCount();
    }
}

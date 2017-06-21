package ru.otus.homework_03;

import java.util.Collection;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Arrays;
import java.util.NoSuchElementException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MyArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private int size;
    private Object[] items;

    public Iterator<T> iterator() {
        return new MyListIterator(0);
    }

    public ListIterator<T> listIterator() {
        return new MyListIterator(0);
    }

    public ListIterator<T> listIterator(int index) {
        return new MyListIterator(index);
    }

    public MyArrayList() {
        this.items = EMPTY_ARRAY;
    }

    public MyArrayList(int arrayLength) {
        if (arrayLength > 0) {
            this.items = new Object[arrayLength];
        } else if (arrayLength == 0) {
            this.items = EMPTY_ARRAY;
        } else {
            throw new IllegalArgumentException("Array length " + arrayLength + " is incorrect.");
        }
    }

    private class MyIterator implements Iterator<T> {
        int index;
        int lastUsedIndex = -1;

        MyIterator(int index) {
            if (index < 0 || index > size - 1) {
                throw new IndexOutOfBoundsException();
            }
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            if (index >= size) {
                throw new NoSuchElementException();
            }
            lastUsedIndex = index++;
            return (T) items[lastUsedIndex];
        }

        @Override
        public void remove() {
            if (lastUsedIndex != -1) {
                MyArrayList.this.remove(lastUsedIndex);
                index = lastUsedIndex;
                lastUsedIndex = -1;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private class MyListIterator extends MyIterator implements ListIterator<T> {
        public MyListIterator(int index) {
            super(index);
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public T previous() {
            if (index < 0) {
                throw new NoSuchElementException();
            }
            lastUsedIndex = index--;
            return (T) items[lastUsedIndex];
        }

        @Override
        public int nextIndex() {
            throw new NotImplementedException();
//            return 0;
        }

        @Override
        public int previousIndex() {
            throw new NotImplementedException();
//            return 0;
        }

        @Override
        public void set(T t) {
            MyArrayList.this.set(lastUsedIndex, t);
        }

        @Override
        public void add(T t) {
            throw new NotImplementedException();
        }
    }

    private boolean grow(int minArrayLength) {
        int newArrayLength = items.length * 2;
        if (minArrayLength > newArrayLength) {
            newArrayLength = minArrayLength;
        }

        items = Arrays.copyOf(items, newArrayLength);
        return true;
    }

    private void checkArrayLength(int minArrayLength) {
        if (items == EMPTY_ARRAY) {
            minArrayLength = Math.max(minArrayLength, DEFAULT_CAPACITY);
        }
        if (minArrayLength > items.length) {
            grow(minArrayLength);
        }
    }

    private void checkArrayIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
//        return false;
    }

    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    public <T> T[] toArray(T[] a) {
        throw new NotImplementedException();
//        return null;
    }

    public boolean add(T t) {
        checkArrayLength(size + 1);
        items[size++] = t;
        return true;
    }

    public void add(int index, T element) {
        checkArrayIndex(index);
        checkArrayLength(size + 1);
        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = element;
        size++;
    }

    public boolean addAll(Collection<? extends T> c) {
        checkArrayLength(size + c.size());
        Iterator<? extends T> iterator = c.iterator();
        while (iterator.hasNext()) {
            items[size++] = iterator.next();
        }
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        checkArrayIndex(index);
        checkArrayLength(size + c.size());
        System.arraycopy(items, index, items, index + c.size(), size - index);
        Iterator<? extends T> iterator = c.iterator();
        while (iterator.hasNext()) {
            items[index++] = iterator.next();
        }
        size += c.size();
        return true;
    }

    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
//        return false;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }
        size = 0;
    }

    public T get(int index) {
        checkArrayIndex(index);
        return (T) items[index];
    }

    public T set(int index, T element) {
        checkArrayIndex(index);
        T oldValue = (T) items[index];
        items[index] = element;
        return oldValue;
    }

    public T remove(int index) {
        checkArrayIndex(index);
        T result = (T) items[index];
        if (index < size - 1) {
            System.arraycopy(items, index + 1, items, index, size - (index + 1));
        }
        items[--size] = null;
        return result;
    }

    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
//        return false;
    }

    public int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (o.equals(items[i])) {
                    return i;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (items[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        if (o != null) {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(items[i])) {
                    return i;
                }
            }
        }
        for (int i = size - 1; i >= 0; i--) {
            if (items[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
//        return null;
    }
}

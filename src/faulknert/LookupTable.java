/*
 * Course: CS2852 - 071
 * Spring 2020
 * Morse Encoder Lab
 * Name: Tyler Faulkner
 * Created: 05/07/2020
 */
package faulknert;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

/**
 * A map that sorts each item in order.
 * @param <K> needs to extend comparable
 * @param <V> can be any type
 */
public class LookupTable<K extends Comparable<? super K>, V> implements Map<K, V> {

    private class Entry<K extends Comparable<? super K>, V>
            implements Comparable<Entry<K, V>>, Map.Entry {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            Object temp = value;
            this.value = (V) value;
            return temp;
        }

        public K getKey() {
            return key;
        }

        @Override
        public int compareTo(Entry<K, V> o) {
            return key.compareTo(o.getKey());
        }
    }

    private List<Entry<K, V>> list;

    /**
     * Instantiates the look up table class.
     */
    public LookupTable() {
        list = new ArrayList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        int i = Collections.binarySearch(list, new Entry<K, V>((K) key, null));
        if(i < 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object key) {
        int i = Collections.binarySearch(list, new Entry<K, V>((K) key, null));
        if (i < 0) {
            return null;
        }
        return list.get(i).getValue();
    }

    private int binarySearch(Entry entry, int lowIndex, int highIndex){
        int range = highIndex - lowIndex;
        if(range <= 0){
            return -1;
        }
        if(range == 1){
            if(list.get(lowIndex).compareTo(entry) == 0){
                return lowIndex;
            } else if (list.get(highIndex).compareTo(entry) == 0){
                return highIndex;
            } else {
                return -1;
            }
        } else {
            int middle = (lowIndex + highIndex) >>> 1;
            int compare = list.get(middle).compareTo(entry);
            if (compare == 0) {
                return middle;
            } else if (compare > 0) {
                return binarySearch(entry, lowIndex, middle);
            } else {
                return binarySearch(entry, middle, highIndex);
            }
        }
    }

    @Override
    public V put(K key, V value) {
        Entry newEntry = new Entry(key, value);
        if (list.size() == 0) {
            list.add(newEntry);
            return null;
        }
        int i = findPlace(newEntry);
        if (i < list.size()) {
            V temp = list.get(i).getValue();
            if(i-1 >= 0){
                list.add(i-1, newEntry);
            } else {
                list.add(0, newEntry);
            }
            return temp;
        } else {
            list.add(newEntry);
            return null;
        }
    }

    private int findPlace(Entry entry) {
        int i = 0;
        int compare = list.get(i).compareTo(entry);
        for (i = 0; i < list.size() && compare < 0; ++i){
            compare = list.get(i).compareTo(entry);
        }
        return i;
    }

    @Override
    public V remove(Object key) {
        int i = Collections.binarySearch(list, new Entry<K, V>((K) key, null));
        if (i < 0) {
            return null;
        } else {
            V temp = list.get(i).getValue();
            list.remove(i);
            return temp;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (Entry entry : list) {
            set.add(entry);
        }
        return set;
    }

}

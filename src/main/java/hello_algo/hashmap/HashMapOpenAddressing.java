package hello_algo.hashmap;

import java.util.ArrayList;
import java.util.List;

public class HashMapOpenAddressing {
    int size;
    double loadThres;
    int capacity;
    int extendRatio;
    Pair[] buckets;
    Pair removed = new Pair(-1, "-1");

    public HashMapOpenAddressing() {
        size = 0;
        loadThres = 2 / 3.0;
        capacity = 4;
        extendRatio = 2;
        buckets = new Pair[capacity];
    }

    private int hashFunc(int key) {
        return key % capacity;
    }

    public void put(int key, String val) {
        if(size / capacity > loadThres) {
            extend();
        }
        int index = hashFunc(key);
        while (buckets[index] != null && buckets[index] != removed) {
            if(buckets[index].key == key){
                buckets[index].val = val;
                return;
            }
            index = (index + 1) % capacity;
        }
        Pair pair = new Pair(key, val);
        buckets[index] = pair;
        size++;
    }

    public String get(int key) {
        int index = hashFunc(key);
        index = index % capacity;
        while (buckets[index] != null || buckets[index] == removed) {
            if(buckets[index].key == key){
                return buckets[index].val;
            }
            index++;
        }
        return null;
    }

    public void remove(int key) {
        int index = hashFunc(key);
        index = index % capacity;
        while (buckets[index] == null || buckets[index] == removed) {
            index++;
        }
        if(buckets[index].key == key) {
            buckets[index] = removed;
            size--;
        }
    }

    public void extend() {
        Pair[] temp = buckets;
        capacity = capacity * extendRatio;
        buckets = new Pair[capacity];
        size = 0;
        for(int i = 0; i < temp.length; i++) {
            put(temp[i].key, temp[i].val);
        }
    }

    public void print() {
       for(Pair pair : buckets) {
           if(pair != null) {
               System.out.println(pair.key + "->" + pair.val);
           }else {
               System.out.println("null");
           }
       }
    }

    public static void main(String[] args) {
        // 初始化哈希表
        HashMapOpenAddressing hashmap = new HashMapOpenAddressing();

        // 添加操作
        // 在哈希表中添加键值对 (key, val)
        hashmap.put(12836, "小哈");
        hashmap.put(15937, "小啰");
        hashmap.put(16750, "小算");
        hashmap.put(13276, "小法");
        hashmap.put(10583, "小鸭");
        System.out.println("\n添加完成后，哈希表为\nKey -> Value");
        hashmap.print();

        // 查询操作
        // 向哈希表中输入键 key ，得到值 val
        String name = hashmap.get(13276);
        System.out.println("\n输入学号 13276 ，查询到姓名 " + name);

        // 删除操作
        // 在哈希表中删除键值对 (key, val)
        hashmap.remove(16750);
        System.out.println("\n删除 16750 后，哈希表为\nKey -> Value");
        hashmap.print();
    }
}

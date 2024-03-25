package hello_algo.hashmap;

import java.util.ArrayList;
import java.util.List;

public class HashMapChaining {
    List<List<Pair>> buckets;
    int size;
    double loadThres;
    int extendRatio;
    int capacity;

    public HashMapChaining() {
        size = 0;
        capacity = 4;
        extendRatio = 2;
        loadThres = 2 / 3.0;
        buckets = new ArrayList<>();
        for(int i = 0; i < capacity; i++) {
            buckets.add(new ArrayList<>());
        }
    }

    private int hashFunc(int key) {
        return key % capacity;
    }

    public void extend() {
        if(size / capacity > loadThres) {
            capacity = capacity * extendRatio;
            List<List<Pair>> tempList = buckets;
            buckets = new ArrayList<>(capacity);
            for(int i = 0; i < capacity; i++) {
                buckets.add(new ArrayList<>());
            }
            size = 0;
            for(List<Pair> bucket : tempList) {
                for(Pair pair : bucket) {
                    put(pair.key, pair.val);
                }
            }
        }
    }

    public void put(int key, String val) {
        extend();
        int index = hashFunc(key);
        List<Pair> pairs = buckets.get(index);
        for(Pair tempPair : pairs) {
            if(tempPair.key == key) {
                tempPair.val = val;
                return;
            }
        }
        Pair pair = new Pair(key, val);
        pairs.add(pair);
        size++;
    }

    public String get(int key) {
        int index = hashFunc(key);
        List<Pair> pairs = buckets.get(index);
        for(Pair pair : pairs) {
            if(pair.key == key) {
                return pair.val;
            }
        }
        return null;
    }

    public void remove(int key) {
        int index = hashFunc(key);
        List<Pair> pairs = buckets.get(index);
        for(Pair pair : pairs) {
            if(pair.key == key) {
                pairs.remove(pair);
                size--;
                break;
            }
        }
    }

    public void print() {
        List<String> res = new ArrayList<>();
        for(List<Pair> bucket : buckets) {
            for(Pair pair : bucket) {
                res.add(pair.key + "->" + pair.val);
            }
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        /* 初始化哈希表 */
        HashMapChaining map = new HashMapChaining();

        /* 添加操作 */
        // 在哈希表中添加键值对 (key, value)
        map.put(12836, "小哈");
        map.put(15937, "小啰");
        map.put(16750, "小算");
        map.put(13276, "小法");
        map.put(10583, "小鸭");
        System.out.println("\n添加完成后，哈希表为\nKey -> Value");
        map.print();

        /* 查询操作 */
        // 向哈希表中输入键 key ，得到值 value
        String name = map.get(13276);
        System.out.println("\n输入学号 13276 ，查询到姓名 " + name);

        /* 删除操作 */
        // 在哈希表中删除键值对 (key, value)
        map.remove(12836);
        System.out.println("\n删除 12836 后，哈希表为\nKey -> Value");
        map.print();
    }
}

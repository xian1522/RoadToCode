package hello_algo.hashmap;

import java.util.ArrayList;
import java.util.List;

public class ArrayHashMap {

    private List<Pair> buckets;

    public ArrayHashMap() {
        buckets = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            buckets.add(null);
        }
    }

    private int hashFunc(int key) {
        return key % 100;
    }

    private void put(int key, String val) {
        int index = hashFunc(key);
        Pair pair = new Pair(key, val);
        buckets.set(index, pair);
    }

    private String get(int key) {
        int index = hashFunc(key);
        Pair pair = buckets.get(index);
        if(pair != null) {
            return pair.val;
        }
        return null;
    }

    private void remove(int key) {
        key = hashFunc(key);
        buckets.remove(key);
    }

    private void print() {
        for(Pair pair : pairSet()) {
            System.out.println(pair.key + " -> " + pair.val);
        }
    }

    private List<Pair> pairSet() {
        List<Pair> pairs = new ArrayList<>();
        for(Pair pair : buckets) {
            if(pair != null) {
                pairs.add(pair);
            }
        }
        return pairs;
    }

    private List<Integer> keySet() {
        List<Integer> pairs = new ArrayList<>();
        for(Pair pair : buckets) {
            if(pair != null) {
                pairs.add(pair.key);
            }
        }
        return pairs;
    }

    private List<String> valueSet() {
        List<String> list = new ArrayList<>();
        for(Pair pair : buckets) {
            if(pair != null) {
                list.add(pair.val);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        /* 初始化哈希表 */
        ArrayHashMap map = new ArrayHashMap();

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
        String name = map.get(15937);
        System.out.println("\n输入学号 15937 ，查询到姓名 " + name);

        /* 删除操作 */
        // 在哈希表中删除键值对 (key, value)
        map.remove(10583);
        System.out.println("\n删除 10583 后，哈希表为\nKey -> Value");
        map.print();

        /* 遍历哈希表 */
        System.out.println("\n遍历键值对 Key->Value");
        for (Pair kv : map.pairSet()) {
            System.out.println(kv.key + " -> " + kv.val);
        }
        System.out.println("\n单独遍历键 Key");
        for (int key : map.keySet()) {
            System.out.println(key);
        }
        System.out.println("\n单独遍历值 Value");
        for (String val : map.valueSet()) {
            System.out.println(val);
        }
    }
}

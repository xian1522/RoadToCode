package hello_algo;

import hello_algo.array_and_linkedlist.LinkedList;

import java.util.ArrayList;
import java.util.List;

public class PrintUtil {

    public static void printLinkedList(ListNode listNode) {
        List<String> list = new ArrayList<>();
        ListNode temp = listNode;
        while (temp != null){
            list.add(String.valueOf(temp.val));
            temp = temp.next;
        }
        System.out.println(String.join("->", list));
    }
}

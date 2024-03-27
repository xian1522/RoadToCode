package hello_algo.graph;

import hello_algo.PrintUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 邻接矩阵无向图
 */
public class GraphAdjMat {
    private List<Integer> vertices; //顶点列表
    private List<List<Integer>> adjMat; // 邻接矩阵

    public GraphAdjMat(int[] vertices, int[][] edges) {
        this.vertices = new ArrayList<>();
        this.adjMat = new ArrayList<>();

        for(int val: vertices) {
            addVertex(val);
        }
        for(int[] edge : edges) {
            addEdge(edge[0] , edge[1]);
        }
    }

    //顶点数量
    public int size() {
        return  vertices.size();
    }

    public void addEdge(int i, int j) {
        if(i < 0 || j < 0 || i >= size() || j >= size() || i == j) {
            throw new IndexOutOfBoundsException();
        }
        adjMat.get(i).set(j, 1);
        adjMat.get(j).set(i, 1);
    }

    public void addVertex(int val) {
        int n = size();
        vertices.add(val);

        //添加行
        List<Integer> newRow = new ArrayList<>(n);
        for(int i = 0; i < n; i++) {
            newRow.add(0);
        }
        adjMat.add(newRow);
        //添加列
        for(List<Integer> row : adjMat) {
            row.add(0);
        }
    }

    public void removeVertex(int index) {
        if(index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        vertices.remove(index);
        adjMat.remove(index);
        for(List<Integer> row : adjMat) {
            row.remove(index);
        }
    }

    public void removeEdge(int i, int j) {
        if(i < 0 || j < 0 || i >= size() || j >= size() || i == j) {
            throw new IndexOutOfBoundsException();
        }
        adjMat.get(i).set(j, 0);
        adjMat.get(j).set(i, 0);
    }

    /* 打印邻接矩阵 */
    public void print() {
        System.out.print("顶点列表 = ");
        System.out.println(vertices);
        System.out.println("邻接矩阵 =");
        PrintUtil.printMatrix(adjMat);
    }


    public static void main(String[] args) {
        /* 初始化无向图 */
        // 请注意，edges 元素代表顶点索引，即对应 vertices 元素索引
        int[] vertices = { 1, 3, 2, 5, 4 };
        int[][] edges = { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 2, 3 }, { 2, 4 }, { 3, 4 } };
        GraphAdjMat graph = new GraphAdjMat(vertices, edges);
        System.out.println("\n初始化后，图为");
        graph.print();

        /* 添加边 */
        // 顶点 1, 2 的索引分别为 0, 2
        graph.addEdge(0, 2);
        System.out.println("\n添加边 1-2 后，图为");
        graph.print();

        /* 删除边 */
        // 顶点 1, 3 的索引分别为 0, 1
        graph.removeEdge(0, 1);
        System.out.println("\n删除边 1-3 后，图为");
        graph.print();

        /* 添加顶点 */
        graph.addVertex(6);
        System.out.println("\n添加顶点 6 后，图为");
        graph.print();

        /* 删除顶点 */
        // 顶点 3 的索引为 1
        graph.removeVertex(1);
        System.out.println("\n删除顶点 3 后，图为");
        graph.print();
    }
}

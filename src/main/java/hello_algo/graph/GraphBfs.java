package hello_algo.graph;

import java.util.*;

public class GraphBfs {
    public static List<Vertex> graphBFS(GraphAdjList graphAdjList, Vertex startVertex) {
        List<Vertex> res = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        visited.add(startVertex);

        Queue<Vertex> queue = new LinkedList<>();
        queue.offer(startVertex);
        while (!queue.isEmpty()) {
            Vertex poll = queue.poll();
            res.add(poll);
            List<Vertex> vertices = graphAdjList.adjList.get(poll);
            for(Vertex vertex : vertices) {
                if(visited.contains(vertex)) {
                    continue;
                }
                queue.offer(vertex);
                visited.add(vertex);
            }
        }
        return res;
    }


    public static void main(String[] args) {
        /* 初始化无向图 */
        Vertex[] v = Vertex.valsToVets(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
        Vertex[][] edges = { { v[0], v[1] }, { v[0], v[3] }, { v[1], v[2] }, { v[1], v[4] },
                { v[2], v[5] }, { v[3], v[4] }, { v[3], v[6] }, { v[4], v[5] },
                { v[4], v[7] }, { v[5], v[8] }, { v[6], v[7] }, { v[7], v[8] } };
        GraphAdjList graph = new GraphAdjList(edges);
        System.out.println("\n初始化后，图为");
        graph.print();

        /* 广度优先遍历 */
        List<Vertex> res = graphBFS(graph, v[8]);
        System.out.println("\n广度优先遍历（BFS）顶点序列为");
        System.out.println(Vertex.vetsToVals(res));
    }
}

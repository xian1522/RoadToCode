package hello_algo.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphDfs {
    public static List<Vertex> graphDFS(GraphAdjList graph, Vertex startVertex) {
        List<Vertex> res = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        dfs(graph, res, visited, startVertex);
        return res;
    }

    static void dfs(GraphAdjList graph, List<Vertex> res, Set<Vertex> visited, Vertex startVertex) {
        res.add(startVertex);
        visited.add(startVertex);

        List<Vertex> vertices = graph.adjList.get(startVertex);
        for(Vertex vertex : vertices) {
            if(visited.contains(vertex)){
                continue;
            }
            dfs(graph, res, visited, vertex);
        }
    }

    public static void main(String[] args) {
        /* 初始化无向图 */
        Vertex[] v = Vertex.valsToVets(new int[] { 0, 1, 2, 3, 4, 5, 6 });
        Vertex[][] edges = { { v[0], v[1] }, { v[0], v[3] }, { v[1], v[2] },
                { v[2], v[5] }, { v[4], v[5] }, { v[5], v[6] } };
        GraphAdjList graph = new GraphAdjList(edges);
        System.out.println("\n初始化后，图为");
        graph.print();

        /* 深度优先遍历 */
        List<Vertex> res = graphDFS(graph, v[0]);
        System.out.println("\n深度优先遍历（DFS）顶点序列为");
        System.out.println(Vertex.vetsToVals(res));
    }
}

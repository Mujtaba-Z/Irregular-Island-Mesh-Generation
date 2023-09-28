package GraphADT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class Algorithm implements pathfinder{

    @Override
    public HashMap<Node, Node> ShortestPath(Graph G, Node source) {
        if (source == null) {
            return null;
        }
        HashMap<Node, Node> path = new HashMap<>();
        path.put(source, source);
        HashMap<Node, Integer> cost = new HashMap<>();
        cost.put(source, 0);

        int tempriority = 10000;
        for (Node node : G.getNodes()) {
            cost.put(node, tempriority);
            tempriority++;
        }
        cost.put(source, 0);

        MinPriorityQueue Q = new MinPriorityQueue();
        Q.enqueue(source, 0);


        while (!Q.isEmpty()) {
            Node current = Q.dequeue();
            for (Edge edge : G.getEdges(current)) {
                if (cost.get(current) == null || cost.get(edge.getEdgenode()) == null) {
                    return null;
                }
                if ((cost.get(current) + edge.getWeight()) < cost.get(edge.getEdgenode())) {
                    path.put(edge.getEdgenode(), current);
                    cost.put(edge.getEdgenode(), cost.get(current) + edge.getWeight());
                    Q.enqueue(edge.getEdgenode(), cost.get(edge.getEdgenode()));
                }
            }
        }

        return path;
    }

}

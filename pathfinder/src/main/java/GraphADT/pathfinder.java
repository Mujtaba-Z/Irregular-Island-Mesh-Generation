package GraphADT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface pathfinder {

    public HashMap<Node, Node> ShortestPath(Graph G, Node source);

}

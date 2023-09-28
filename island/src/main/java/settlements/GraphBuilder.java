package settlements;

import GraphADT.Graph;
import GraphADT.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.terrain.Tile;

import java.util.List;

public class GraphBuilder {

    Graph G;
    List<Tile> tileList;
    Structs.Mesh.Builder aMesh;

    public GraphBuilder(Graph G, List<Tile> tileList, Structs.Mesh.Builder aMesh) {
        this.G = G;
        this.tileList = tileList;
        this.aMesh = aMesh;
    }


    public Graph buildGraph() {
        for (Tile t : this.tileList) {
            if (t.isEndorheic() || t.isLake() || !t.isIsland()) {
                continue;
            }
            for (Structs.Polygon p : aMesh.getPolygonsList()) {
                if (t.getCentroidIdx() == p.getCentroidIdx()) {
                    for (Integer segID : p.getSegmentIdxsList()) {
                        int v1ID = aMesh.getSegments(segID).getV1Idx();
                        int v2ID = aMesh.getSegments(segID).getV2Idx();

                        Structs.Vertex V1 = aMesh.getVertices(v1ID);
                        Structs.Vertex V2 = aMesh.getVertices(v2ID);

                        Node filler = new Node(t.getElevation(), "Filler", V1.getX(), V1.getY(), t.hasHub, true, v1ID); //turn both true to get wild roads
                        Node filler2 = new Node(t.getElevation(), "Filler", V2.getX(), V2.getY(), t.hasHub, true, v2ID);

                        int distance = (int) Math.sqrt( Math.pow( Math.abs(V1.getX() - V2.getX()) , 2) + Math.pow( Math.abs(V1.getY() - V2.getY()) , 2) );

                        G.addNode(filler);
                        G.addNode(filler2);
                        G.addEdge(filler, filler2, distance, 0);

                    }
                }
            }
        }
        return G;
    }


}

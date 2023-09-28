package settlements;

import GraphADT.Graph;
import GraphADT.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.terrain.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SettlementBuilder {

    private List<Tile> tileList;
    private Structs.Mesh.Builder aMesh;
    private int numofsettlements;
    private List<String> citynames = new ArrayList<>();

    private List<Structs.Vertex> newCities = new ArrayList<>();

    private Graph G = new Graph();

    public SettlementBuilder(List<Tile> tileList, Structs.Mesh.Builder aMesh, int cities) {
        this.tileList = tileList;
        this.aMesh = aMesh;
        this.numofsettlements = cities;
    }

    private void populateCitynames() {
        citynames.add("Toronto");
        citynames.add("NYC");
        citynames.add("London");
        citynames.add("Paris");
        citynames.add("Amsterdam");
        citynames.add("Copenhagen");
        citynames.add("Lyon");
        citynames.add("Marseille");
        citynames.add("Milan");
        citynames.add("Rome");
        citynames.add("Helsinki");
        citynames.add("Dubai");
        citynames.add("Islamabad");
    }

    public List<Structs.Vertex> BuildSettlements() {
        List<Structs.Polygon> polygonsList = aMesh.getPolygonsList();
        Random r = new Random();
        boolean isHub = false;
        boolean hubReached = false;
        populateCitynames();


        int counter = 0;
        for (Tile t : this.tileList) {
            if (counter == this.numofsettlements) {
                break;
            }

            if (t.isIsland() && !t.hasCity && !t.isLake() && !t.isEndorheic()) {
                for (Structs.Polygon p : polygonsList) {
                    if (t.getCentroidIdx() == p.getCentroidIdx()) {
                        t.hasCity = true;
                        setNeighbours(t);

                        int size = r.nextInt(10) + 5;
                        int vID = aMesh.getSegments(p.getSegmentIdxs(0)).getV1Idx();
                        Structs.Vertex cityvertex = aMesh.getVertices(vID);

                        if (counter == this.numofsettlements/2 && !hubReached) {
                            isHub = true;
                            t.hasHub = true;
                            hubReached = true;
                        }
                        decorateVertexCity(cityvertex, size, isHub);
                        Node citynode = new Node(t.getElevation(), this.citynames.get(r.nextInt(this.citynames.size())), cityvertex.getX(), cityvertex.getY(), isHub, true, vID);

                        isHub = false;

                        G.addNode(citynode);
                        counter++;
                        break;
                    }

                }
            }
        }
        return newCities;
    }

    private void setNeighbours(Tile tile) {
        for (Integer neighbourTileIdx : tile.getNeighbours()) {
            if (!tileList.get(neighbourTileIdx).hasCity) {
                tileList.get(neighbourTileIdx).hasCity = true;
            }
        }
    }

    public List<Tile> getTileList() {
        return tileList;
    }

    private void decorateVertexCity(Structs.Vertex v, int size, boolean isHub) {
        Structs.Vertex.Builder vc = Structs.Vertex.newBuilder(v);
        String color;

        if (isHub) {
            color = "181,0,0";
        } else {
            color = "255,183,0";
        }

        Structs.Property p = Structs.Property.newBuilder().setKey("rgb_color").setValue(color).build();
        vc.addProperties(p);
        p = Structs.Property.newBuilder().setKey("size").setValue(String.valueOf(size)).build();
        vc.addProperties(p);
        Structs.Vertex newv = vc.build();
        newCities.add(newv);
    }

    public Graph returnGraph() {
        return G;
    }

}

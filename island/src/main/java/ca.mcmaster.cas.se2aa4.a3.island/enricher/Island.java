package ca.mcmaster.cas.se2aa4.a3.island.enricher;

import GraphADT.Graph;
import GraphADT.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.dimensions.Bounds;
import ca.mcmaster.cas.se2aa4.a3.island.shapes.Circle;
import ca.mcmaster.cas.se2aa4.a3.island.shapes.Rectangle;
import ca.mcmaster.cas.se2aa4.a3.island.shapes.Shape;
import ca.mcmaster.cas.se2aa4.a3.island.dimensions.Dimensons;

import ca.mcmaster.cas.se2aa4.a3.island.terrain.Tile;
import org.locationtech.jts.geom.Geometry;
import settlements.GraphBuilder;
import settlements.RoadBuilder;
import settlements.SettlementBuilder;
import water.Aquifier;
import water.Lakes;
import water.Rivers;
import whitaker.WhitakerDiagram;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

public class Island implements Enricher {

    public Structs.Mesh originalMesh = null;

    private RectangularShape shapeIsland;

    public Structs.Mesh.Builder aMesh = Structs.Mesh.newBuilder();
    private Dimensons meshDimensions;
    public String elevation;
    public String biome;
    private String aquifiers;
    public List<Tile> tileList = new ArrayList<>();
    private List<Structs.Segment> riverSegments = new ArrayList<>();
    public String lakes;
    private String rivers;
    private String soil;
    private int cities;

    private Graph G = new Graph();

    public Island(Structs.Mesh aMesh, String cities, String shape, String elevation, String biome, String lakes, String aquifiers, String rivers, String soil) {
        this.originalMesh = aMesh;
        this.aMesh.addAllVertices(aMesh.getVerticesList());
        this.aMesh.addAllSegments(aMesh.getSegmentsList());
        this.meshDimensions = new Dimensons(aMesh.getVerticesList());
        this.elevation = elevation;
        this.biome = biome;
        this.lakes = lakes;
        this.aquifiers = aquifiers;
        this.rivers = rivers;
        this.soil = soil;
        this.cities = Integer.parseInt(cities);

        if (shape.equals("circle")) {
            this.shapeIsland = (Ellipse2D) new Circle(this.meshDimensions.height(), this.meshDimensions.width(), this.meshDimensions.width()/4).createSelf();
        } else if (shape.equals("rectangle")){
            this.shapeIsland = (Rectangle2D) new Rectangle(this.meshDimensions.height(), this.meshDimensions.width(), 250).createSelf();
        }
    }

    public void colorPolygons() {
        String color = "";
        for (Tile tile : this.tileList) {
            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(this.originalMesh.getPolygonsList().get(tile.getID()));
            color = tile.getColor();

            Structs.Property p = Structs.Property.newBuilder()
                    .setKey("rgb_color")
                    .setValue(color)
                    .build();
            pc.addProperties(p);
            this.aMesh.addPolygons(pc);
        }
    }

    @Override
    public void process() {
        int numPolygon = 0;

        for (Structs.Polygon poly: this.originalMesh.getPolygonsList()) {

            double centroid_x = this.aMesh.getVerticesList().get(poly.getCentroidIdx()).getX();
            double centroid_y = this.aMesh.getVerticesList().get(poly.getCentroidIdx()).getY();

            Tile tile = new Tile(this.biome, this.shapeIsland, centroid_x, centroid_y, poly.getNeighborIdxsList(), numPolygon, poly.getCentroidIdx(), this.soil);
            this.tileList.add(tile);

            ++numPolygon;
        }
    }

    @Override
    public Structs.Mesh buildNewMesh() {
        this.process();
        this.buildLakes();
        this.buildAquifier();
        this.elevateIsland();
        this.buildRivers();
        this.colorPolygons();
        this.buildSettlements();
        this.buildGraph();
        this.buildRoads();
        return this.aMesh.build();
    }

    private void buildSettlements() {
        SettlementBuilder SB = new SettlementBuilder(this.tileList, this.aMesh, this.cities);
        List<Structs.Vertex> newVertices = SB.BuildSettlements();
        this.aMesh.addAllVertices(newVertices);
        this.tileList = SB.getTileList();
        this.G = SB.returnGraph();
    }
    private void buildGraph() {
        GraphBuilder GB = new GraphBuilder(G, tileList, aMesh);
        this.G = GB.buildGraph();
    }

    private void buildRoads() {
        RoadBuilder RB = new RoadBuilder(this.G, this.aMesh);
        List<Structs.Segment> newSegments = RB.BuildRoads();
        this.aMesh.addAllSegments(newSegments);
    }
    private void elevateIsland() {
        for (Tile t : this.tileList) {
            if (this.elevation.equals("volcano")) {
                t.volcanizer();
            } else if (this.elevation.equals("flatland")) {
                t.flatlander();
            } else if (this.elevation.equals("hills")) {
                t.hiller();
            }
        }
    }


    public void buildLakes() {
        if (Integer.parseInt(this.lakes) > 0) {
            Lakes lakes = new Lakes(this.originalMesh.getPolygonsList(), this.tileList, Integer.parseInt(this.lakes));
            this.tileList = lakes.createLakes();
        }
    }

    private void buildAquifier() {
        if (Integer.parseInt(this.aquifiers) > 0) {
            Aquifier aquifier = new Aquifier(this.originalMesh.getPolygonsList(), this.tileList, Integer.parseInt(this.aquifiers));
            this.tileList = aquifier.createAquifiers();
        }
    }

    private void buildRivers() {
        if (Integer.parseInt(this.rivers) > 0) {
            Rivers river = new Rivers(this.originalMesh.getPolygonsList(), this.tileList, Integer.parseInt(this.rivers));
            this.riverSegments = river.createRivers();
        }
        this.aMesh.addAllSegments(this.riverSegments);
    }

}
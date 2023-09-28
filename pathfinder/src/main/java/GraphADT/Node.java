package GraphADT;

import java.util.*;

public class Node {

    private int elevation;
    private String city;

    private double xpos;
    private double ypos;

    private boolean isHub;

    private int vertexID;
    private boolean isRealCity;

    public Node(int elevation, String city) {
        this.elevation = elevation;
        this.city = city;
    }

    public Node(int elevation, String city, double xcoordinate, double ycoordinate, boolean isHub, boolean isRealCity, int vertexID) {
        this.elevation = elevation;
        this.city = city;
        this.xpos = xcoordinate;
        this.ypos = ycoordinate;
        this.isHub = isHub;
        this.isRealCity = isRealCity;
        this.vertexID = vertexID;
    }

    public int getVertexID() {
        return vertexID;
    }

    public boolean isHub() {
        return isHub;
    }

    public boolean isRealCity() {
        return isRealCity;
    }

    public int getElevation() {
        return elevation;
    }

    public String getCity() {
        return city;
    }

    public double getXpos() {
        return xpos;
    }

    public double getYpos() {
        return ypos;
    }

    public Edge asEdge(int weight) {
        Edge nodeasedge = new Edge(this, weight);
        return nodeasedge;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Node other = (Node) obj;

        if (this.xpos != other.xpos) {
            return false;
        }

        if (this.ypos != other.ypos) {
            return false;
        }

        return true;

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) Math.round(this.xpos*10000);
        hash = 53 * hash + (int) Math.round(this.ypos*10000);

        return hash;
    }

}

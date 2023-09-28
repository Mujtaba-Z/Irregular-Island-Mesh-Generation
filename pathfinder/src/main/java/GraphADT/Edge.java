package GraphADT;

public class Edge {

    private Node edgenode;
    private int weight;

    public Edge(Node node, int weight) {
        this.edgenode = node;
        this.weight = weight;
    }

    public Node getEdgenode() {
        return edgenode;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Edge other = (Edge) obj;
        if ((this.edgenode == null) ? (other.edgenode != null) : !this.edgenode.equals(other.edgenode)) {
            return false;
        }

        if (this.weight != other.weight) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.edgenode != null ? this.edgenode.hashCode() : 0);
        hash = 53 * hash + this.weight;
        return hash;
    }

}

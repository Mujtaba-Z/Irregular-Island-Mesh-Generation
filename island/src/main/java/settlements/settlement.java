package settlements;

import GraphADT.Edge;
import GraphADT.Node;

public class settlement {

    Node settlementnode;
    int size;

    public settlement(Node city, int size) {
        this.settlementnode = city;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Node getSettlementnode() {
        return settlementnode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final settlement other = (settlement) obj;
        if ((this.settlementnode == null) ? (other.settlementnode != null) : !this.settlementnode.equals(other.settlementnode)) {
            return false;
        }

        if (this.size != other.size) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 101 * hash + (this.settlementnode != null ? this.settlementnode.hashCode() : 0);
        hash = 101 * hash + this.size;

        return hash;
    }

}

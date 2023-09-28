package GraphADT;

import java.util.*;

public class MinPriorityQueue{

    HashMap<Integer, Node> HM = new HashMap<>();
    int priority = 100000;

    public void enqueue(Node node, int priority) {
        this.HM.put(priority, node);
        this.priority = Math.min(this.priority, priority);
    }

    public Node dequeue() {
        Node dequeued = this.HM.remove(this.priority);
        this.priority = 100000;
        for (int prior : this.HM.keySet()) {
            this.priority = Math.min(this.priority, prior);
        }
        return dequeued;
    }

    public Node minprioritypeek() {
        return this.HM.get(this.priority);
    }

    boolean isEmpty() {
        return this.HM.isEmpty();
    }

}

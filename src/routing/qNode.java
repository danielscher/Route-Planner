package routing;



public class qNode implements nodeAdapterInterface, Comparable<qNode> {

    private Node n;
    private qNode pred = null;
    private double disFromSource = Double.POSITIVE_INFINITY;
    private double heuristicDis;
    
    public qNode(Node n){
        this.n = n;
    }

    public Node getNode() {
        return n;
    }

    @Override
    public void setDisFromSource(double dist) {
        this.disFromSource = dist;
    }

    @Override
    public double getDisFromSource() {
        return this.disFromSource;
    }

    @Override
    public void setPredecessor(qNode pred) {
        this.pred = pred;
    }

    @Override
    public qNode getPredecessor() {
        return this.pred;
    }

    @Override
    public int compareTo(qNode node2) {
        if (this.getHeuristicDis() < node2.getHeuristicDis()){
            return -1;
        }
        if (this.getHeuristicDis() > node2.getHeuristicDis()){
            return 1;
        }
        return 0;
    }

    @Override
    public void setHeuristicDis(double disToEnd) {
        this.heuristicDis = disToEnd + disFromSource;
    }

    @Override
    public double getHeuristicDis() {
        return this.heuristicDis;
    }
    
}

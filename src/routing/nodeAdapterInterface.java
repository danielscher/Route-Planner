package routing;

public interface nodeAdapterInterface {
    /**Adapter class for Node. 
     * Allows to get and change the additional fields
     * needed for the Route Algorithem
     */
    public Node getNode();
    public void setDisFromSource(double distFromSource);
    public double getDisFromSource();
    public void setPredecessor(qNode pred);
    public qNode getPredecessor(); 
    public void setHeuristicDis(double disToEnd);
    public double getHeuristicDis();

    
}

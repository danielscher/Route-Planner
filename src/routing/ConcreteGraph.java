package routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ConcreteGraph implements Graph {
    private HashMap<Long,Node> nodes;
    private int numNodes;
    private int numEdges;

    

    public ConcreteGraph(HashMap<Long,Node> nodes, int numNodes, int numEdges) {
        this.nodes = nodes;
        this.numNodes = numNodes;
        this.numEdges = numEdges;
    }

    @Override
    public Node getNode(long id) {
        if (!nodes.containsKey(id)){
            return null;
        }
        return nodes.get(id);
    }

    @Override   
    public Coordinate getNWCoordinate() {
        Iterator<Node> itr = this.iterator();
        Node first = null;
        if(itr.hasNext()){
            first = itr.next();
        }
        double maxLat = first.getCoordinate().getLatitude();
        double maxLon = first.getCoordinate().getLongitude();
        for ( Node n : nodes.values()){
            if  (n.getCoordinate().getLatitude() > maxLat){
                maxLat = n.getCoordinate().getLatitude();
            }
            if (n.getCoordinate().getLongitude() > maxLon){
                maxLon = n.getCoordinate().getLongitude();
            }
        }
        
        return new Coordinate(maxLat, maxLon);
    }

    @Override
    public Coordinate getSECoordinate() {
        Iterator<Node> itr = this.iterator();
        Node first = null;
        if(itr.hasNext()){
            first = itr.next();
        }
        double minLat = first.getCoordinate().getLatitude();
        double minLon = first.getCoordinate().getLongitude();
        for ( Node n : nodes.values()){
            if  (n.getCoordinate().getLatitude() < minLat){
                minLat = n.getCoordinate().getLatitude();
            }
            if (n.getCoordinate().getLongitude() < minLon){
                minLon = n.getCoordinate().getLongitude();
            }
        }
        return new Coordinate(minLat, minLon);
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.values().iterator();
    }

    @Override
    public int numEdges() {
        return this.numEdges;
    }

    @Override
    public int numNodes() {
        return nodes.size();
    }

    @Override
    public int removeIsolatedNodes() {


        HashMap<Long,Node> newHash = new HashMap<Long,Node>();
        int newAdded = 0;
        int removed = 0;
        int numNodes = this.numNodes;
        for ( Node n : nodes.values()){
            if (n.numEdges() != 0){
                long id = n.getId();
                newHash.put(id,n);
                newAdded++;
            }
        }
        this.nodes = newHash;
        removed = numNodes - newAdded;
        this.numNodes = newAdded;
        return removed;
    }

    @Override
    public int removeUntraversableEdges(RoutingAlgorithm ra, TravelType tt) {
        ArrayList<Integer> toRemove = new ArrayList<Integer>();
        int removed = 0;
        if (!ra.isBidirectional()){
            for ( Node n : nodes.values()){
                Node x = n;
                if (x.numEdges() == 0){continue;}
                Iterator<Edge> it = x.iterator();
                while (it.hasNext()){
                    if(!it.next().allowsTravelType(tt, Direction.FORWARD)){
                        it.remove();
                        removed++;
                    }
                }
            }
        this.numEdges -= removed;
        return removed;
        }

        else if (ra.isBidirectional()){
            for ( Node n : nodes.values()){
                int id = 0;
                Node x = n;
                for(Edge e : x){
                    if (!e.allowsTravelType(tt, Direction.FORWARD) || !e.allowsTravelType(tt, Direction.BACKWARD)){
                        toRemove.add(id);
                    }
                    id++;
                }
                for (Integer i : toRemove){
                    x.removeEdge(i);
                }
                removed += toRemove.size();
                toRemove.clear();
            }
        }
        return removed;
    }

    @Override
    public boolean isOverlayGraph() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Node getNodeInUnderlyingGraph(long id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}

package routing;

import java.util.ArrayList;
import java.util.Iterator;

public class ConcreteNode implements Node{
    final private long id;
    final private double lat;
    final private double lon;
    private ArrayList<Edge> edges;



    public ConcreteNode(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.edges = new ArrayList<Edge>();
    }

    @Override
    public Coordinate getCoordinate() {
        return new Coordinate(this.lat, this.lon);
    }

    @Override
    public Edge getEdge(int idx) { 
        return edges.get(idx);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public Iterator<Edge> iterator() {
        return edges.iterator();
    }

    @Override
    public int numEdges() {
        return this.edges.size();
    }

    @Override
    public void addEdge(Edge e) {
        this.edges.add(e);
        
    }

    @Override
    public void removeEdge(int i) {
        this.edges.remove(i);
        
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConcreteNode other = (ConcreteNode) obj;
        if (id != other.id)
            return false;
        return true;
    }
}

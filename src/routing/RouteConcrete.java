package routing;

import java.util.ArrayList;
import java.util.Iterator;

public class RouteConcrete extends RouteBase {
    ArrayList<RouteLeg> legs;
    TravelType tt;


    public RouteConcrete(ArrayList<RouteLeg> legs, TravelType tt) {
        this.legs = legs;
        this.tt = tt;
    }

    @Override
    public double distance() {
        double dis = 0;
        Iterator<RouteLeg> it = this.iterator();
        while (it.hasNext()){
            dis += it.next().getDistance();
        }
        return dis;
    }

    @Override
    public Node getEndNode() {
        int idx = legs.size() - 1;
        return legs.get(idx).getEndNode();
    }

    @Override
    public Node getStartNode() {
        return legs.get(0).getStartNode();
    }

    @Override
    public TravelType getTravelType() {
        return this.tt;
    }

    @Override
    public Iterator<RouteLeg> iterator() {
        return this.legs.iterator();
    }

    @Override
    public int size() {
        return legs.size();
    }
    
}

package routing;

import java.util.ArrayList;
import java.util.Iterator;

public class RouteLegConcrete extends RouteLegBase{
    double distance;
    ArrayList<Node> nodes = new ArrayList<Node>();

    public RouteLegConcrete() {
    }

    @Override
    public double getDistance() {
        return this.distance;
    }

    @Override
    public Node getEndNode() {
        int idx = nodes.size() - 1;
        return this.nodes.get(idx);
    }

    @Override
    public Node getStartNode() {
        return this.nodes.get(0);
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

    @Override
    public int size() {
        return nodes.size();
    }
    
}

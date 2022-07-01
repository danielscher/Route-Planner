package routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;


public class ra implements RoutingAlgorithm{

    @Override
    public Route computeRoute(Graph g, List<Node> nodes, TravelType tt) throws NoSuchRouteException {
        NodeFinder nf = Factory.createNodeFinder(g);
        ArrayList<RouteLeg> rlList = new ArrayList<RouteLeg>();
        for (int i = 0; i < nodes.size() - 1; i++){
            Node node1 = nf.getNodeForCoordinates(nodes.get(i).getCoordinate());
            Node node2 = nf.getNodeForCoordinates(nodes.get(i+1).getCoordinate());
            RouteLeg rl = computeRouteLeg(g, node1, node2, tt);
            rlList.add(rl);
        }
        return new RouteConcrete(rlList,tt);
    }

    @Override
    public RouteLeg computeRouteLeg(Graph g, long startId, long endId, TravelType tt) throws NoSuchRouteException {
        if (g.getNode(startId) == null || g.getNode(endId) == null){
            throw new NoSuchRouteException();
        }
        Node start_node = g.getNode(startId);
        Node end_node = g.getNode(endId);
        return computeRouteLeg(g, start_node, end_node, tt);
        
    }

    @Override
    public RouteLeg computeRouteLeg(Graph G, Node start_node, Node end_node, TravelType TT)
            throws NoSuchRouteException {
                if (G.getNode(start_node.getId()) == null || G.getNode(end_node.getId()) == null){
                    throw new NoSuchRouteException();
                }
                HashSet<Long> visited = new HashSet<Long>();               //all IDs of nodes visited.
                PriorityQueue<qNode> seen =  new PriorityQueue<qNode>();   //all outgoing edges of curr visited node
                qNode S = new qNode(start_node);                                    //wraps in wrapper class for extra methods.
                qNode E = null;
                S.setDisFromSource(0);                                     //sets the distance of start node to 0.
                S.setHeuristicDis(getDisToEnd(S.getNode(), end_node));
                seen.offer(S);                                             //adds it to PQ.
        
                //loops while all seen nodes are visited;
                while (!seen.isEmpty()){
                    qNode w = seen.poll();                                 //gets the closest node from source node from QP.
                    if (w.getNode() == end_node){
                        E = w;
                        break;
                    }
                    visited.add(w.getNode().getId());                      //adds the node polled to visited.    
                    //iterates over all outgoing edges in the node and adds only the closest to end node.
                    for (Edge e : w.getNode()){
                        if (e.allowsTravelType(TT, Direction.FORWARD)){
                            Node T = e.getEnd();
                            if (visited.contains(T.getId())){
                                continue;
                            }                             
                            qNode t = new qNode(T);
                            double currDist = t.getDisFromSource();
                            double newDist = e.getLength() + w.getDisFromSource();
                            if (newDist < currDist){
                                t.setDisFromSource(newDist);
                                t.setHeuristicDis(getDisToEnd(t.getNode(), end_node));
                                t.setPredecessor(w);
                                seen.offer(t);
                            }
                        }
                 
                    }       
                }
                if( E == null){
                    throw new NoSuchRouteException();
                }
                //computes Routeleg
                //Routeleg leg = //new RouteLegConcrete(distance, endNode, startNode)
                RouteLegConcrete leg = new RouteLegConcrete();
                qNode n = E;
                leg.distance = n.getDisFromSource();
                leg.nodes.add(n.getNode());
                while(n.getPredecessor() != null){
                    n = n.getPredecessor();
                    leg.nodes.add(n.getNode());
                }
                Collections.reverse(leg.nodes);
        
        
                return leg;
            

    }

    @Override
    public boolean isBidirectional() {
        // TODO Auto-generated method stub
        return false;
    }

    double getDisToEnd (Node current, Node end){
        return current.getCoordinate().getDistance(end.getCoordinate());
    }

    
}

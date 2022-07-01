package routing;


import java.util.ArrayList;



public class ConcreteEdge implements Edge {
    private Node start;
    private Node end;
    private double length;
    private ArrayList<TravelType> forward;
    private ArrayList<TravelType> backward;


    

    public ConcreteEdge(Node start, Node end, double length, ArrayList<TravelType> forward,
            ArrayList<TravelType> backward) {
        this.start = start;
        this.end = end;
        this.length = length;
        this.forward = forward;
        this.backward = backward;
    }


    @Override
    public boolean allowsTravelType(TravelType tt, Direction dir) {
        switch (dir){
            case FORWARD:
                if (forward == null || forward.size() == 0){return false;}
                if (tt == TravelType.ANY){return true;}
                return forward.contains(tt) || forward.contains(TravelType.ANY);
            
            case BACKWARD:
                if (backward == null || backward.size() == 0 ){return false;}
                if (tt == TravelType.ANY){return true;}
                return backward.contains(tt)|| backward.contains(TravelType.ANY);
                            
            case ANY:
                if ((backward == null || backward.size() == 0 ) 
                    && (forward == null || forward.size() == 0)){return false;}
                if (tt == TravelType.ANY){return true;}
                return forward.contains(tt) || backward.contains(tt);
        }
        return false;
    }


    @Override
    public Node getEnd() {
        return this.end;
    }

    @Override
    public double getLength() {
        return this.length;
    }

    @Override
    public Node getStart() {
        return this.start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public void setLength(double length) {
        this.length = length;
    }
 
}

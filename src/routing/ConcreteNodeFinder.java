package routing;

import java.lang.Math;
import java.util.Iterator;



public class ConcreteNodeFinder implements NodeFinder {
    Coordinate ne;
    Coordinate nw;
    Coordinate sw;
    Coordinate se;
    double boxsize;
    double boxw;
    double boxh;
    double mapH;
    double mapW;
    double coordsInc;
    double latInc;
    double lonInc;
    int divider;
    Node nearest;
    Cell[][] grid;
    Graph g;
    int nodesAdded = 0;



    public void addNode(Node n){
        double deltaLong = n.getCoordinate().getLongitude() - nw.getLongitude();
        double deltaLat = n.getCoordinate().getLongitude() - nw.getLongitude();
        int col = (int) (deltaLong/coordsInc);
        int row = (int) ((divider)- deltaLat/coordsInc);
        if (col >= 0 && col < grid[0].length && row >= 0 && row < grid.length){
            grid[row][col].ids.add(n.getId());
        }
 


/*         for (int row = 0; row < divider; row++){
            for (int col = 0; col < divider; col++){
                if (grid[row][col].coorBound.contains(n.getCoordinate())){
                    grid[row][col].ids.add(n.getId());
                    this.nodesAdded++;
                    return;
                }
            }
        }
        System.out.println(n.getCoordinate()); */
    }

    public int[] searchStartCell(Coordinate c){
        double deltaLong = c.getLongitude() - nw.getLongitude();
        double deltaLat = c.getLatitude() - nw.getLatitude();
        int col = (int) (deltaLong/coordsInc);
        int row = (int) ((divider)- deltaLat/coordsInc);
        int[] coords = {row, col};
        return coords;



/*         for (int row = 0; row < divider; row++){
            for (int col = 0; col < divider; col++){
                if (grid[row][col].coorBound.contains(c)){
                    int[] coords = {row, col};
                    return coords;
                }
            }
        }
        return null; */
    }

    ConcreteNodeFinder(Graph g){
        //calculates single box size and sets params.
        Coordinate ne = g.getNWCoordinate();
		Coordinate sw = g.getSECoordinate();
		Coordinate se = new Coordinate(sw.getLatitude(), ne.getLongitude());
		Coordinate nw = new Coordinate(ne.getLatitude(), sw.getLongitude());
        double w = se.getDistance(sw);
        double h = se.getDistance(ne);
        this.divider = (int) Math.sqrt(g.numNodes());
        double boxw = (w/divider);
        double boxh = (h/divider);
        this.coordsInc = boxw > boxh ? (ne.getLongitude() - nw.getLongitude())/divider : (nw.getLatitude() - sw.getLatitude())/divider;; // squaring the box by picking the larger side.
        this.mapH = h;
        this.mapW = w;
        this.sw = sw;
        this.se = se;
        this.nw = nw;
        this.ne = ne;
        this.boxw = boxw;
        this.boxh = boxh;
        //this.latInc = (nw.getLatitude() - sw.getLatitude())/divider;
        //this.lonInc = (ne.getLongitude() - nw.getLongitude())/divider;
        this.grid = new Cell[divider + 1][divider + 1];
        this.g = g;


        //build cells
        for (int row = 0; row < grid.length; row++){
            for (int col = 0; col < grid[0].length; col++){//todo: get coords from dist
                Coordinate upper = new Coordinate (nw.getLatitude()+row*coordsInc,nw.getLongitude()+(col+1)*coordsInc);
                Coordinate lower = new Coordinate (nw.getLatitude()-(row+1)*coordsInc,nw.getLongitude()+(col)*coordsInc);
                Cell cell = new Cell(lower, upper);
                grid[row][col] = cell;
            }
        }


        //populating cells with id's
        //assert (g.numNodes()/boxes.keySet().size()) >= 1;
        Iterator<Node> it = g.iterator();
        while(it.hasNext()){
            Node n = it.next();
            addNode(n);
        }
        //assert nodesAdded == g.numNodes();
    }


    @Override
    public Node getNodeForCoordinates(Coordinate c) {
        //get the starting box indexes
        int[] StartCoords = searchStartCell(c);
        int row = StartCoords[0];
        int col = StartCoords[1];
        //spiral search
        SpiralSearch sprAlgo = new SpiralSearch(row, col, c, grid, g);
        Node nearest = sprAlgo.antiSpiralTraversal(c);

        
        //if node exists in starting box check all nodes 

        //spiral pattern
        
        return nearest;
    }
    
}

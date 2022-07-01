package routing;

import java.util.ArrayList;

public class Cell {
    CoordinateBox coorBound;
    ArrayList<Long> ids;

    public Cell(Coordinate lower, Coordinate upper){
        coorBound = new CoordinateBox(lower, upper);
        //double h = coorBound.getHeight();
        //Double.compare(h, boxh);
        //assert h == boxh;
        //assert (coorBound.getWidth() == boxw);
        ids = new ArrayList<Long>();
    }

}

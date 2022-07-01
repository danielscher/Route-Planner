package routing;

public class SpiralSearch {
    Cell mat[][];
    int rowS;
    int colS;
    int inc = 1;
    int m ;
    int n ;
    int currCol;
    int currRow; 
    Graph g;
    Coordinate s;
    Node nearestNode = null;
    final double INF = Double.POSITIVE_INFINITY;
    double minDis = INF;
    int cellVisits = 0;
    int cellsToVisit = 1;
    int nodesFound = 0;
    int segIndex = 1;
    int endFlag = 0;
    int cycleComplete = 0;
    double cellSize;
    double currSearchRad;



    SpiralSearch(int rowS, int colS, Coordinate s, Cell[][] grid, Graph g){
        this.rowS = rowS;
        this.colS = colS;
        this.currCol = colS;
        this.currRow = rowS;
        this.s = s;
        this.mat = grid;
        this.m = grid.length - 1;
        this.n = grid[0].length -1;
        this.g = g;
        this.cellSize = grid[0][0].coorBound.getWidth();
        this.currSearchRad = (grid[0][0].coorBound.getWidth())/2; //width and height are the same.
    }

    public void setG(Graph g) {
        this.g = g;
    }

    
    public Node antiSpiralTraversal(Coordinate c){
        

        //m = max row
        //n = max col
        
        while(cellVisits < (m+1)*(n+1)){ 

            rightDown();
            if (endFlag == 1){break;}
            leftUp();
            if (endFlag == 1){break;}

            
        }
        return nearestNode;
    }

    public void incCol(){
        this.currCol++;
    }
    public void incRow(){
        this.currRow++;
    }
    public void decCol(){
        this.currCol--;
    }
    public void decRow(){
        this.currRow--;
    }
    public void incInc(){
        this.inc++;
    }
    public void setDis(double d){
        minDis = d;
    }
    public double getDis(){
        return this.minDis;
    }
    public void setNN(Node n){
        this.nearestNode = n;
    }
    public void searchCurrCell(int row, int col){
        this.cellVisits++;
        this.cellsToVisit--;
        int sz = mat[row][col].ids.size();
        for (int i = 0; i< sz; i++){
           Long id = mat[row][col].ids.get(i);
           Coordinate PotentialCoords = g.getNode(id).getCoordinate();
            if ( PotentialCoords.getDistance(this.s) < this.minDis){
                setDis(PotentialCoords.getDistance(this.s));
                setNN(g.getNode(id));
                //System.out.println(minDis + " row: " + row + " col: " + col + " cells visited: " + cellVisits);
                this.nodesFound++;
            }
        }
        if (cellsToVisit == 0){
            this.segIndex += 2;
            this.cellsToVisit = (this.segIndex - 1)*4;
            this.currSearchRad += cellSize;

            if( nodesFound >= 1 && cycleComplete == 1 && currSearchRad > minDis){
                endFlag = 1;
            }

            if (nodesFound >= 1){
                cycleComplete = 1;
            }
            
        }
    } 

     




    public int getCurrCol() {
        return currCol;
    }

    public int getCurrRow() {
        return currRow;
    }

    private void rightDown() {
            int limitCol = currCol + inc;
                for (int col = this.currCol ; col < limitCol; ++col){
                    if (currRow <= m && currRow >= 0 && col <= n && col >= 0 ){
                        //System.out.print(this.mat[currRow][col]);
                        searchCurrCell(currRow,col);
                    }
                    //System.out.println("coord: "+ getCurrRow()+ ","+ getCurrCol());
                    this.incCol();
                    if (endFlag == 1){return;}
                }

                int limitRow = currRow + inc;
                for( int row = this.currRow ; row < limitRow; ++row){
                    if (currCol<= n && currCol >= 0 && row <= n && row >= 0 ){
                        //System.out.print(this.mat[row][currCol]);
                        searchCurrCell(row, currCol);
                    }
                    //System.out.println("coord: "+ getCurrRow()+ ","+ getCurrCol());
                    this.incRow();
                    if (endFlag == 1){return;}
                }
        this.incInc();
    }

    
    private void leftUp(){
        int limitCol = currCol - inc;
        int limitRow = currRow - inc;
            for (int col = currCol ; col > limitCol; --col){
                if (currRow <= m && currRow >= 0 && col <= n && col >= 0 ){
                    //System.out.print(this.mat[currRow][col]);
                    searchCurrCell(currRow, col);
                }
               // System.out.println("coord: "+ getCurrRow()+ ","+ getCurrCol());
                this.decCol();
                if (endFlag == 1){return;}

            }
    

            for( int row = currRow ; row > limitRow; --row){
                if (currCol<= n && currCol >= 0 && row <= n && row >= 0 ){
                    //System.out.print(this.mat[row][currCol]);
                    searchCurrCell(row, currCol);
                }
               // System.out.println("coord: "+ getCurrRow()+ ","+ getCurrCol());
                this.decRow();
                if (endFlag == 1){return;}

            }
        this.incInc();


    }
    
}

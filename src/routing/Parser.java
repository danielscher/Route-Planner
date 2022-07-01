package routing;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;


public class Parser {
    //initialization of containers
    private  HashMap<Long,Node> nodes;
    private  int numNodes = 0;
    private  int numEdges = 0;

    public HashMap<Long,Node> parseMap(String fileName)throws FileNotFoundException{
        Path input = Paths.get(fileName);   //file path 
        nodes = new HashMap<Long,Node>();   //creating a hash to store all the nods read
        if (!Files.exists(input)){
            throw new FileNotFoundException();
        }

        
        try (Stream<String> lines = Files.lines(input)) {   //reads files and filters the nodes
            lines.filter(s -> s.startsWith("N"))
                 .forEach(s -> NodeParser(s));

        } catch (IOException e) {
            System.out.println("file cannot be read.");
        }
        try (Stream<String> lines = Files.lines(input)) {   //reads files and filters the edges
            lines.filter(s -> s.startsWith("E"))
                 .forEach(s -> EdgeParser(s));

        } catch (IOException e) {
            System.out.println("file cannot be read.");
        }
        return nodes;
    }

    public void NodeParser(String line){ //parses the data for nodes
        numNodes++;
        String[] nodeData = line.split("\\s+");
        if (nodeData.length < 4 ){
            System.out.println("Bad node data.");
            return;
        }
        nodes.put(Long.parseLong(nodeData[1]), //stores nodes in the hashmap
            new ConcreteNode(Long.parseLong(nodeData[1]),
             Double.parseDouble(nodeData[2]), Double.parseDouble(nodeData[3])));
    }

    public void EdgeParser(String line){ //parses the data for the edges
        numEdges++; //increase #edges for every edge obj created (2)
        numEdges++;
        Edge edge = null , edge2 = null;
        Node start= null, end = null;
        Coordinate s = null;
        ArrayList<TravelType> f = new ArrayList<TravelType>();
        ArrayList<TravelType> b = new ArrayList<TravelType>();
        String[] edgeData = line.split("\\s+");
        if (edgeData.length < 9 ){
            System.out.println("Bad node data.");
            return;
        }
        long s_id = Long.parseLong(edgeData[1]);       //gets the id of the nodes in the edge
        long e_id = Long.parseLong(edgeData[2]);       //
        if (nodes.containsKey(s_id)){ start  = nodes.get(s_id);}    //checks if nodes exist in the node hashmap
        if (nodes.containsKey(e_id)){ end    = nodes.get(e_id);}    //and sets them to start and end nodes
        s = start.getCoordinate();
        double length   = s.getDistance(end.getCoordinate());       //gets the distance bwtn s node and e node
        if (Integer.parseInt(edgeData[3]) == 1 && Integer.parseInt(edgeData[5]) == 1 && Integer.parseInt(edgeData[7]) == 1){
            f.add(TravelType.ANY);}
        else {
            if (Integer.parseInt(edgeData[3]) == 1){
                f.add(TravelType.CAR);}
            if (Integer.parseInt(edgeData[5]) == 1){
                f.add(TravelType.BIKE);}
            if (Integer.parseInt(edgeData[7]) == 1){
                f.add(TravelType.FOOT);}
        }
        if (Integer.parseInt(edgeData[4]) == 1 && Integer.parseInt(edgeData[6]) == 1 && Integer.parseInt(edgeData[8]) == 1){
            b.add(TravelType.ANY);}
        else {
            if (Integer.parseInt(edgeData[4]) == 1){
                b.add(TravelType.CAR);}
            if (Integer.parseInt(edgeData[6]) == 1){
                b.add(TravelType.BIKE);}
            if (Integer.parseInt(edgeData[8]) == 1){
                b.add(TravelType.FOOT);}
        }
        edge = new ConcreteEdge(start, end, length, f, b);  //creates two edge objects
        edge2 = new ConcreteEdge(end, start, length, b, f); // one is reversed
        start.addEdge(edge);                                //puts the edge object in the nodes
        end.addEdge(edge2);                                 //  the reversed for the e node
    }
    public int getNumEdgesRead() {
        return numEdges;
    }
    public  int getNumNodesRead() {
        return numNodes;
    }
}



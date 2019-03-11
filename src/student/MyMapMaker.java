package student;

import model.*;

import java.io.*;
import java.util.*;

/**
 * MyMapMaker is the class used to create a map from the text file
 * @author srikanthtumati
 */
public class MyMapMaker implements model.MapMaker {
    /**
     * readMap is the method used to properly read a text file and build the map
     * @param in The {@link InputStream} used to read the {@link RailroadMap
     * map} data.
     * @return the RailroadMap constructed from the text file
     * @throws RailroadBaronsException if the map cannot be properly configured
     */
    public RailroadMap readMap(InputStream in) throws RailroadBaronsException{
        /** spaces is the arraylist of spaces in the game*/
        ArrayList<MySpace> spaces = new ArrayList<>();
        /** stations is the arraylist of stations in the game*/
        ArrayList<MyStation> stations = new ArrayList<>();
        /** routes is the arraylist of routes in the game*/
        ArrayList<Route> routes = new ArrayList<>();
        /** locations the arraylist of locations in the game*/
        ArrayList<MySpace> locations = new ArrayList<>();
        /** east is the number of columns used in the game*/
        int east=0;
        /** south is the number of rows used in the game*/
        int south=0;
        int s = 0;

        //file read...
        BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                while (true) {

                    String line = reader.readLine();
                    if (line==null){
                        break;
                    }
                    String[] partitions = line.split(" ");
                    if(partitions.length<3){
                        continue;
                    }
                    else if (!partitions[2].equals("UNCLAIMED")){
                        if (Integer.parseInt(partitions[1])>east){
                            east = Integer.parseInt(partitions[1]);
                        }
                        if (Integer.parseInt(partitions[2])>south){
                            south = Integer.parseInt(partitions[2]);
                        }
                        locations.add(new MySpace(Integer.parseInt(partitions[1]), Integer.parseInt(partitions[2])));
                        stations.add(new MyStation(partitions[3], Integer.parseInt(partitions[1]), Integer.parseInt(partitions[2])));
                    }

                    else{
                        MyRoute temp = new MyRoute(Baron.UNCLAIMED, stations.get(Integer.parseInt(partitions[0])), stations.get(Integer.parseInt(partitions[1])));
                        routes.add(temp);
                        for (Track t : temp.getTracks()){
                            locations.add(new MySpace(t.getRow(), t.getCol()));
                        }
                    }
                }

            }
            catch(Exception ex){
                ex.printStackTrace();
                throw new RailroadBaronsException("MyMapMaker.java: Error in reading map file!");
            }
            finally {
                try {
                    in.close();
                } catch (IOException ab) {
                }
            }
            Boolean contains;
        for (int i = 0; i < east; i ++){
                for (int z = 0; z < south; z++){
                    contains=false;
                    for (MySpace space : locations){
                        if (space.collocated(new MySpace(i, z))){
                            contains=true;
                        }

                    }
                    if (!contains){
                        spaces.add(new MySpace(i, z));
                    }
                }
        }
        return new MyRailroadMap(spaces, stations, routes);

    }

    /**
     * writeMap is the method used to write the map to a external file
     *
     * @param map The {@link RailroadMap map} to write out to the
     * {@link OutputStream}.
     * @param out The {@link OutputStream} to which the
     * {@link RailroadMap map} data should be written.
     *
     */
    public void writeMap(RailroadMap map, OutputStream out){
        Set<Route> routes = new TreeSet<>();
        ArrayList<MyStation> stations = new ArrayList<>();
        try {
            PrintWriter pw = new PrintWriter(out);
            int counter = 0;
            for (int i = 0; i < map.getRows(); i++ ){
                for (int z = 0; z < map.getCols(); z++){
                    if (map.getSpace(i ,z) instanceof MyStation){
                        stations.add((MyStation) map.getSpace(i , z));
                        pw.println(counter + " " + map.getSpace(i, z).getRow() + " " + map.getSpace(i, z).getCol() + " " + ((MyStation) map.getSpace(i, z)).getName());
                        counter+=1;
                    }
                    else if(map.getSpace(i, z) instanceof MyTrack){
                        routes.add(((MyTrack) map.getSpace(i, z)).getRoute());
                    }
                }
            }
            pw.println("##ROUTES##");
            for (Route r : routes){
                pw.println(stations.indexOf(r.getOrigin()) + " " + stations.indexOf(r.getDestination()) + ((r.getBaron()==Baron.UNCLAIMED) ? " UNCLAIMED": " "+r.getBaron()));
            }
        }
        catch(Exception ex){}
    }
}

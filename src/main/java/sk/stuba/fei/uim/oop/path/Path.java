package sk.stuba.fei.uim.oop.path;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Path {
    private int size;
    private ArrayList<ArrayList<Vertex>> vertices;
    private Vertex startVertex;
    private Vertex finishVertex;
    public Path(int size) {
        this.size = size;
        initialiseVertices();
        int roll = ThreadLocalRandom.current().nextInt(size);
        startVertex = vertices.get(roll).get(0);
        roll = ThreadLocalRandom.current().nextInt(size);
        finishVertex = vertices.get(roll).get(size - 1);
        layPath(startVertex);
        drainEntirePath();
        rotateAllVerticesRandomly();
    }
    private void initialiseVertices() {
        vertices = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Vertex> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(new Vertex(j,i));
            }
            vertices.add(row);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int[][] cds = {{j + 1, i}, {j, i + 1}, {j - 1, i}, {j, i - 1}};
                for (int[] cd : cds) {
                    Vertex vertex = vertices.get(i).get(j);
                    Vertex whoseConn;
                    if(!isOutOfBounds(cd[0],cd[1]))
                        whoseConn = vertices.get(cd[1]).get(cd[0]);
                    else
                        whoseConn = null;
                    vertex.putConnection(whoseConn);
                }
            }
        }
    }
    private boolean layPath(Vertex vertex) {
        vertex.flood();
        if(vertex.equals(finishVertex))
            return true;
        Vertex nextVertex = getRandomUnfloodedNeighbour(vertex);
        while (nextVertex != null) {
            vertex.fauxConnect(nextVertex);
            nextVertex.fauxConnect(vertex);
            if(layPath(nextVertex))
                return true;
            vertex.fauxDisconnect(nextVertex);
            nextVertex.fauxDisconnect(vertex);
            nextVertex = getRandomUnfloodedNeighbour(vertex);
        }
        return false;
    }
    private Vertex getRandomUnfloodedNeighbour(Vertex vertex) {
        ArrayList<Vertex> neighbours = new ArrayList<>();
        int x = vertex.getX();
        int y = vertex.getY();
        int[][] candidateCds = {{x+1, y}, {x, y+1}, {x-1, y}, {x, y-1}};
        for(int[] cd : candidateCds){
            if(!isOutOfBounds(cd[0], cd[1])){
                Vertex vxRef = vertices.get(cd[1]).get(cd[0]);
                if(!vxRef.isWet())
                    neighbours.add(vxRef);
            }
        }
        if (neighbours.isEmpty())
            return null;
        Collections.shuffle(neighbours);
        return neighbours.get(0);
    }
    public boolean isOutOfBounds(int x, int y) {
        if(!(x >= 0 && x <= size-1))
            return true;
        if(!(y >= 0 && y <= size-1))
            return true;
        return false;
    }
    private void drainEntirePath(){
        for (ArrayList<Vertex> row : vertices)
            for (Vertex vertex : row)
                vertex.drain();
    }
    private void rotateAllVerticesRandomly() {
        for (ArrayList<Vertex> row : vertices) {
            for (Vertex vertex : row) {
                int roll = ThreadLocalRandom.current().nextInt(4);
                for(int i = 0 ; i < roll; i++)
                    vertex.rotate();
            }
        }
    }
    public boolean floodPathTillBlockage() {
        drainEntirePath();
        return attemptToFloodVertex(startVertex);
    }
    private boolean attemptToFloodVertex(Vertex curr){
        curr.flood();
        if (curr.equals(finishVertex))
            return true;
        for (Vertex cand : curr.getAllConnections()) {
            if (cand == null || cand.isWet())
                continue;
            boolean isNeighbourConnected = false;
            int deltaX = cand.getX() - curr.getX();
            int deltaY = cand.getY() - curr.getY();
            if (deltaX == 1)
                isNeighbourConnected = (curr.getMask() & 0b0001) != 0 && (cand.getMask() & 0b0100) != 0;
            if (deltaX == -1)
                isNeighbourConnected = (curr.getMask() & 0b0100) != 0 && (cand.getMask() & 0b0001) != 0;
            if (deltaY == 1)
                isNeighbourConnected = (curr.getMask() & 0b0010) != 0 && (cand.getMask() & 0b1000) != 0;
            if (deltaY == -1)
                isNeighbourConnected = (curr.getMask() & 0b1000) != 0 && (cand.getMask() & 0b0010) != 0;
            if(isNeighbourConnected && attemptToFloodVertex(cand))
                return true;
        }
        return false;
    }
}

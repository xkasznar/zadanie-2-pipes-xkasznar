package sk.stuba.fei.uim.oop.path;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Vertex {
    private int x;
    private int y;
    private boolean wet;
    private ArrayList<Vertex> allConnections;
    private int mask;
    private boolean demarcated;
    Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        wet = false;
        allConnections = new ArrayList<>();
        mask = 0b0000;
        demarcated = false;
    }
    void flood(){
        wet = true;
    }
    void drain(){
        wet = false;
    }
    void putConnection(Vertex vertex){
        allConnections.add(vertex);
    }
    void fauxConnect(Vertex vertex){
        int fx = vertex.getX();
        int fy = vertex.getY();
        if(fx > x)
            mask |= 0b0001;
        if(fy > y)
            mask |= 0b0010;
        if(fx < x)
            mask |= 0b0100;
        if(fy < y)
            mask |= 0b1000;
    }
    void fauxDisconnect(Vertex vertex){
        int fx = vertex.getX();
        int fy = vertex.getY();
        if(fx > x)
            mask &= ~0b0001;
        if(fy > y)
            mask &= ~0b0010;
        if(fx < x)
            mask &= ~0b0100;
        if(fy < y)
            mask &= ~0b1000;
    }
    public void rotate(){
        int fourthBit = mask & 0b1000;
        mask = ((mask << 1) & 0b1111) | (fourthBit >> 3);
    }
    public void demarcate(){
        demarcated = true;
    }
    public void undemarcate(){
        demarcated = false;
    }
}

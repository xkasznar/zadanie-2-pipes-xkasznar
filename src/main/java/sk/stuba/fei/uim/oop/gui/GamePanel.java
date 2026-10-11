package sk.stuba.fei.uim.oop.gui;

import lombok.Getter;
import sk.stuba.fei.uim.oop.controls.CustomMouseAdapter;
import sk.stuba.fei.uim.oop.controls.CustomMouseMotionAdapter;
import sk.stuba.fei.uim.oop.path.Path;
import sk.stuba.fei.uim.oop.path.Vertex;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int FACTOR = 6;
    @Getter
    private Path path;
    @Getter
    private int squareSide;
    private int subsquareSide;

    GamePanel(Path path) {
        this.path = path;

        setBackground(Color.orange);
        squareSide = GameFrame.GAME_PANEL_SIDE / path.getSize();
        subsquareSide = squareSide / FACTOR;

        CustomMouseMotionAdapter customMouseMotionAdapter = new CustomMouseMotionAdapter(this);
        addMouseMotionListener(customMouseMotionAdapter);
        CustomMouseAdapter customMouseAdapter = new CustomMouseAdapter(this);
        addMouseListener(customMouseAdapter);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < path.getSize(); i++) {
            for (int j = 0; j < path.getSize(); j++) {
                Vertex vxRef = path.getVertices().get(i).get(j);
                int mask = vxRef.getMask();

                Color colorInside = vxRef.isWet() ? Color.blue : Color.darkGray;
                if(mask != 0) {
                    drawRectHere(j, i, FACTOR / 3 - 1, FACTOR / 3 - 1, FACTOR/3+2, FACTOR/3+2, Color.lightGray, g);
                    drawRectHere(j, i, FACTOR / 3, FACTOR / 3, FACTOR/3, FACTOR/3, colorInside, g);
                }
                if((mask & 0b0001)!=0) {
                    drawRectHere(j, i, (FACTOR / 3) * 2, FACTOR / 3 - 1, FACTOR / 3, FACTOR / 3 + 2, Color.lightGray, g);
                    drawRectHere(j, i, (FACTOR / 3) * 2, FACTOR / 3, FACTOR / 3, FACTOR / 3, colorInside, g);
                }
                if((mask & 0b0010)!=0) {
                    drawRectHere(j, i, (FACTOR / 3)-1, (FACTOR/3)*2, FACTOR / 3+2, FACTOR / 3, Color.lightGray, g);
                    drawRectHere(j, i, (FACTOR / 3), (FACTOR/3)*2, FACTOR / 3, FACTOR / 3, colorInside, g);
                }
                if((mask & 0b0100)!=0) {
                    drawRectHere(j, i, 0, FACTOR / 3 - 1, FACTOR / 3, FACTOR / 3 + 2, Color.lightGray, g);
                    drawRectHere(j, i, 0, FACTOR / 3, FACTOR / 3, FACTOR / 3, colorInside, g);
                }
                if((mask & 0b1000)!=0) {
                    drawRectHere(j, i, (FACTOR / 3)-1, 0, FACTOR / 3+2, FACTOR / 3, Color.lightGray, g);
                    drawRectHere(j, i, (FACTOR / 3), 0, FACTOR / 3, FACTOR / 3, colorInside, g);
                }
                if (vxRef.equals(path.getStartVertex()))
                    drawDemarcation(j, i, Color.green,g);
                if (vxRef.equals(path.getFinishVertex()))
                    drawDemarcation(j, i, Color.red, g);
                if(vxRef.isDemarcated())
                    drawDemarcation(j, i, Color.black, g);
            }
        }
    }
    private void drawCubeHere(int x, int y, int subX, int subY, Color c, Graphics g){
        g.setColor(c);
        int a = squareSide * x + subsquareSide * subX;
        int b = squareSide * (path.getSize()-y) - subsquareSide * (subY+1);
        g.fillRect(a, b, subsquareSide, subsquareSide);
    }
    private void drawRectHere(int x, int y, int subX, int subY, int xExtrusion, int yExtrusion, Color c, Graphics g){
        g.setColor(c);
        for(int i = 0; i < yExtrusion; i++)
            for(int j = 0; j < xExtrusion; j++)
                drawCubeHere(x, y, subX+j, subY+i,  c,g);
    }
    private void drawDemarcation(int x, int y, Color c, Graphics g){
        drawRectHere(x, y, 0,0,FACTOR, 1, c,g);
        drawRectHere(x, y, 0,0,1, FACTOR, c,g);

        drawRectHere(x, y, 0,FACTOR-1,FACTOR, 1, c,g);
        drawRectHere(x, y, FACTOR-1,0,1, FACTOR, c,g);
    }
}
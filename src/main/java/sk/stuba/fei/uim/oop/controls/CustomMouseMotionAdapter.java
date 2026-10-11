package sk.stuba.fei.uim.oop.controls;

import lombok.AllArgsConstructor;
import sk.stuba.fei.uim.oop.gui.GamePanel;
import sk.stuba.fei.uim.oop.path.Path;
import sk.stuba.fei.uim.oop.path.Vertex;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

@AllArgsConstructor
public class CustomMouseMotionAdapter extends MouseMotionAdapter {
    private GamePanel gamePanel;

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int squareSide = gamePanel.getSquareSide();
        Path path = gamePanel.getPath();

        int movedColumn = x / squareSide;
        int movedRow = (path.getSize() - (y / squareSide)) - 1;
        for(ArrayList<Vertex> row : path.getVertices())
            for(Vertex vertex : row)
                vertex.undemarcate();
        if (!path.isOutOfBounds(movedColumn, movedRow))
            path.getVertices().get(movedRow).get(movedColumn).demarcate();
        gamePanel.repaint();
    }
}

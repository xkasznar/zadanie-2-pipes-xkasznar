package sk.stuba.fei.uim.oop.controls;

import lombok.AllArgsConstructor;
import sk.stuba.fei.uim.oop.gui.GameFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@AllArgsConstructor
public class CustomKeyAdapter extends KeyAdapter {
    private GameFrame gameFrame;

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_R)
            gameFrame.resetGame(false);
        if (keyCode == KeyEvent.VK_ESCAPE)
            System.exit(0);
        if (keyCode == KeyEvent.VK_ENTER)
            gameFrame.checkGame();
    }
}

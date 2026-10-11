package sk.stuba.fei.uim.oop.controls;

import lombok.AllArgsConstructor;
import sk.stuba.fei.uim.oop.gui.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@AllArgsConstructor
public class CustomActionListener implements ActionListener {
    private GameFrame gameFrame;

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals(GameFrame.RESET_CAPTION))
            gameFrame.resetGame(false);
        if (e.getActionCommand().equals(GameFrame.CHECK_CAPTION))
            gameFrame.checkGame();
    }
}

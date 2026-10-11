package sk.stuba.fei.uim.oop.controls;

import lombok.AllArgsConstructor;
import sk.stuba.fei.uim.oop.gui.GameFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@AllArgsConstructor
public class CustomChangeListener implements ChangeListener {
    private GameFrame gameFrame;

    @Override
    public void stateChanged(ChangeEvent e){
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            gameFrame.setSize(source.getValue());
            gameFrame.resetGame(false);
        }
    }
}

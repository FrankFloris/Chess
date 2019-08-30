package com.chess.gui;

import com.chess.engine.Alliance;
import com.chess.engine.player.Player;

import javax.swing.*;
import java.awt.*;

import static com.chess.gui.Table.*;

class GameSetup extends JDialog {

    private PlayerType whitePlayerType = PlayerType.COMPUTER;
    private PlayerType blackPlayerType = PlayerType.COMPUTER;
    private JSpinner searchDepthSpinner;

    private int searchDepth = 1;

    private static final String HUMAN_TEXT = "Human";
    private static final String COMPUTER_TEXT = "Computer";
    private final JPanel myPanel = new JPanel(new GridLayout(0,1));

    GameSetup(final JFrame frame,
              final boolean modal){
        super(frame, modal);
        whitePlayerSetup();
        blackPlayerSetup();
        getContentPane().add(myPanel);
        myPanel.add(new JLabel("Search"));
        this.searchDepthSpinner = addLabeledSpinner(myPanel, "Search Depth", new SpinnerNumberModel(4, 0, 12, 1));

        final JButton cancelButton = new JButton("Cancel");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
//                whitePlayerType = whiteComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;
//                blackPlayerType = blackComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;

                searchDepth = getSearchDepth();
                GameSetup.this.setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            System.out.println("Cancel");
            GameSetup.this.setVisible(false);
        });

        myPanel.add(cancelButton);
        myPanel.add(okButton);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    private void whitePlayerSetup() {
        final JRadioButton whiteHumanButton = new JRadioButton(HUMAN_TEXT);
        final JRadioButton whiteComputerButton = new JRadioButton(COMPUTER_TEXT);
        whiteHumanButton.setActionCommand(HUMAN_TEXT);
        final ButtonGroup whiteGroup = new ButtonGroup();
        whiteGroup.add(whiteHumanButton);
        whiteGroup.add(whiteComputerButton);
//        whiteComputerButton.setSelected(true);
        myPanel.add(new JLabel("White"));
        myPanel.add(whiteHumanButton);
        myPanel.add(whiteComputerButton);
        whiteHumanButton.addActionListener(e -> whitePlayerType = PlayerType.HUMAN);
        whiteComputerButton.addActionListener(e -> whitePlayerType = PlayerType.COMPUTER);
    }

    private void blackPlayerSetup() {
        final JRadioButton blackHumanButton = new JRadioButton(HUMAN_TEXT);
        final JRadioButton blackComputerButton = new JRadioButton(COMPUTER_TEXT);
        final ButtonGroup blackGroup = new ButtonGroup();
        blackGroup.add(blackHumanButton);
        blackGroup.add(blackComputerButton);
//        blackComputerButton.setSelected(true);
        myPanel.add(new JLabel("Black"));
        myPanel.add(blackHumanButton);
        myPanel.add(blackComputerButton);
        blackHumanButton.addActionListener(e -> blackPlayerType = PlayerType.HUMAN);
        blackComputerButton.addActionListener(e -> blackPlayerType = PlayerType.COMPUTER);
    }


    void promptUser(){
        setVisible(true);
        repaint();
    }

    boolean isAIPlayer(final Player player){
        if(player.getAlliance() == Alliance.WHITE){
            return getWhitePlayerType() == PlayerType.COMPUTER;
        }
        return getBlackPlayerType() == PlayerType.COMPUTER;
    }

    private PlayerType getWhitePlayerType(){
        return this.whitePlayerType;
    }

    private PlayerType getBlackPlayerType(){
        return this.blackPlayerType;
    }

    private static JSpinner addLabeledSpinner(final Container c,
                                              final String label,
                                              final SpinnerModel model){
        final JLabel one = new JLabel(label);
        c.add(one);
        final JSpinner spinner = new JSpinner(model);
        one.setLabelFor(spinner);
        c.add(spinner);
        return spinner;
    }

    int getSearchDepth(){
        return (Integer)this.searchDepthSpinner.getValue();
    }

    public void setSearchDepth(int searchDepth) {
        this.searchDepth = searchDepth;
    }

}

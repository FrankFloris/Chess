package com.chess.gui.table;

import com.chess.engine.board.Board;
import com.chess.engine.board.moves.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.gui.GameHistoryPanel;
import com.chess.gui.GameSetup;
import com.chess.gui.TakenPiecesPanel;
import com.google.common.collect.Lists;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Table extends Observable {


    private final JFrame gameFrame;
    final GameHistoryPanel gameHistoryPanel;
    final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    final MoveLog moveLog;
    final GameSetup gameSetup;

    Board chessBoard;
    Tile sourceTile;
    Tile destinationTile;
    Piece humanMovedPiece;
    BoardDirection boardDirection;

    private Move computerMove;

    boolean highlightLegalMoves;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
    final Color lightTileColor = Color.decode("#FFFACD");
    final Color darkTileColor = Color.decode("#593E1A");


    private static final Table INSTANCE = new Table();

    private Table(){
        this.gameFrame = new JFrame("JavaChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandardBoard();
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel(this);
        this.moveLog = new MoveLog();
        this.addObserver(new TableGameAIWatcher());
        
        this.gameSetup = new GameSetup(this.gameFrame, true);
        setupWindowListeners();

        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;

        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.setVisible(true);
    }

    private void setupWindowListeners() {
        this.gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.gameFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?" + "\n" + "Unless you saved, your game will be lost!");
                if (i == 0){
                    System.exit(0);
                }
            }
        });
        this.gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Table.get().getGameSetup().setVisible(true);
            }
        });
    }

    public static Table get(){
        return INSTANCE;
    }

    public void show(){
         Table.get().getMoveLog().clear();
         Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
         Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
         Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
//         Table.get().getDebugPanel().redo();
    }

    GameSetup getGameSetup(){
        return this.gameSetup;
    }

    Board getGameBoard(){
        return this.chessBoard;
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createOptionsMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up that PGN file");
            }
        });
        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu createPreferencesMenu(){
        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoardMenuItem);

        preferencesMenu.addSeparator();

        final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight legal moves,", false);
        legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
            }
        });
        preferencesMenu.add(legalMoveHighlighterCheckbox);

        return preferencesMenu;
    }

    private JMenu createOptionsMenu(){

        final JMenu optionsMenu = new JMenu("Options");

        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
        setupGameMenuItem.addActionListener(e -> {
                Table.get().getGameSetup().promptUser();
                Table.get().setupUpdate(Table.get().getGameSetup());
        });
        optionsMenu.add(setupGameMenuItem);
//        optionsMenu.addSeparator();

        return optionsMenu;
    }

    private void setupUpdate(final GameSetup gameSetup){
        setChanged();
        notifyObservers(gameSetup);
    }

    void updateGameBoard(final Board board){
        this.chessBoard = board;
    }

    void updateComputerMove(final Move move){
        this.computerMove = move;
    }

    MoveLog getMoveLog(){
        return this.moveLog;
    }

    GameHistoryPanel getGameHistoryPanel(){
        return this.gameHistoryPanel;
    }

    TakenPiecesPanel getTakenPiecesPanel(){
        return this.takenPiecesPanel;
    }

    BoardPanel getBoardPanel(){
        return this.boardPanel;
    }

    void moveMadeUpdate(final PlayerType playerType){
        setChanged();
        notifyObservers(playerType);
    }

    public enum BoardDirection{
        NORMAL{
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED{
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();
    }

    public enum PlayerType{
        HUMAN,
        COMPUTER
    }

}

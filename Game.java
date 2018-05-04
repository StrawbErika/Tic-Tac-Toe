import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.*;

public class Game {
    public static int ROWS;
    public static int COLS;

    public JFrame frame;
    public JButton buttons[][];
    public Board tictactoe;
    public int action = 0;
    public int i = 0;
    public int j = 0;

    public Game() {
        ROWS = 3;
        COLS = ROWS;
        tictactoe = new Board();
        this.initializeUI();
    }
 
 
// ceate hashmap if tile -> specific icon
  public void initializeUI() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Who will play first? (1) Computer (2) Human?: ");
    action = sc.nextInt();
    

    frame = new JFrame("TicTacToe");

    buttons = new JButton[Game.ROWS][Game.COLS];

    Container pane = frame.getContentPane();
    JPanel panel = new JPanel(new GridLayout(ROWS, COLS));
    pane.add(panel);

    for (i = 0; i < Game.ROWS; i++) {
      for (j = 0; j < Game.COLS; j++) {
        JButton button = new JButton();
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(100, 100)); //tile size
        
        final int rowIndex = i;
        final int columnIndex = j;
        button.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {                        
            if(!tictactoe.isGameOver()){
              tictactoe.usersTurn(rowIndex,columnIndex);
              button.setBackground(Color.PINK);
              button.setEnabled(false);
              if(!tictactoe.isGameOver()){
                tictactoe.nodeScores = new HashMap<Point,Integer>();
                tictactoe.performMiniMax(0,1);
                Point bestMove = tictactoe.getBestMove();
                tictactoe.emulateTurn(bestMove.x, bestMove.y,1);
                buttons[bestMove.x][bestMove.y].setBackground(Color.GRAY);
                buttons[bestMove.x][bestMove.y].setEnabled(false);
              }
              if(tictactoe.isGameOver()){
                String prompt = "";
                switch(tictactoe.whoWon()){
                  case 0:
                    prompt = "It's a draw!";
                    break;
                  case 1: 
                    prompt = "You lost the game!";
                    break;
                  case 2:
                    prompt = "You won the game!";
                    break;
                } 
                JOptionPane.showMessageDialog(frame, prompt);
              }
            }
          }
          });
        panel.add(button);

        buttons[i][j] = button;
      }
    }
    if(action == 1){
      tictactoe.emulateTurn(1, 1, 1);
      buttons[1][1].setBackground(Color.GRAY);
      buttons[1][1].setEnabled(false);
    }
  
    frame.setResizable(false);
    frame.setFocusable(true);
    frame.pack();
    frame.setVisible(true);
  }

}

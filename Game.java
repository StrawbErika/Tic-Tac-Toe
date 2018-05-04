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

        Random r = new Random();
    
        frame = new JFrame("Sokoban");

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
                                tictactoe.performMiniMax(0,1);
                                tictactoe.emulateTurn(tictactoe.aiMove.x, tictactoe.aiMove.y, 1);
                                buttons[tictactoe.aiMove.x][tictactoe.aiMove.y].setBackground(Color.GRAY);
                                buttons[tictactoe.aiMove.x][tictactoe.aiMove.y].setEnabled(false);
                            }
                            if(tictactoe.isGameOver()){
                                JOptionPane.showMessageDialog(frame, "Game Over!");
                            }
                        }
                    }
                    });
                panel.add(button);

                buttons[i][j] = button;
            }
        }
        if(action == 1){
            int x = r.nextInt(3);
            int y = r.nextInt(3);
            tictactoe.emulateTurn(x, y, 1);
            buttons[x][y].setBackground(Color.GRAY);
            buttons[x][y].setEnabled(false);
            action++;
        }

        frame.setResizable(false);
        frame.setFocusable(true);
        frame.pack();
        frame.setVisible(true);
    }

}

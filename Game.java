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

    private JFrame frame;
    private JFrame solve;
    private JButton buttons[][];
    private JButton solveButtons[][];

    private int action = 0;

    private HashMap<String, ImageIcon> iconMap = new HashMap<String, ImageIcon>();

    private String direction;

    public Game() {
        ROWS = 3;
        COLS = ROWS;
        this.initializeUI();
    }
 
 
// ceate hashmap if tile -> specific icon
    public void initializeUI() {
        frame = new JFrame("Sokoban");

        buttons = new JButton[Game.ROWS][Game.COLS];

        Container pane = frame.getContentPane();
        JPanel panel = new JPanel(new GridLayout(ROWS, COLS));
        pane.add(panel);

        for (int i = 0; i < Game.ROWS; i++) {
            for (int j = 0; j < Game.COLS; j++) {
                JButton button = new JButton();
                button.setFocusable(false);
                button.setPreferredSize(new Dimension(100, 100)); //tile size
                
                button.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        if(action%2 == 0){
                            button.setBackground(Color.PINK);
                            button.setEnabled(false);
                        }
                        else{
                            button.setBackground(Color.GRAY);
                            button.setEnabled(false);
                        }
                        action ++;
                    }
                    });
                panel.add(button);

                buttons[i][j] = button;
            }
        }

        frame.setResizable(false);
        frame.setFocusable(true);
        frame.pack();
        frame.setVisible(true);
    }

}

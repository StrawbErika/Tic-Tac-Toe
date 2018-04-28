import java.util.Random;
import java.util.Scanner;

public class Game {
  public static void main(String[] args){

    Board tictactoe = new Board();
    Random r = new Random();
    Scanner sc = new Scanner(System.in);

    System.out.println("Who will play first? (1) Computer (2) Human?: ");
    int choice = sc.nextInt();
    if(choice == 1){ // if computer will take a move first
      tictactoe.emulateTurn(r.nextInt(3), r.nextInt(3), 1);
      tictactoe.printBoard();
    }

    // while there is no winner or the game has not yet come to a draw
    while( !tictactoe.isGameOver()){ 
      tictactoe.usersTurn(); 
      if(tictactoe.isGameOver()) break;

      tictactoe.performMiniMax(0,1);
      tictactoe.emulateTurn(tictactoe.aiMove.x, tictactoe.aiMove.y, 1);
      tictactoe.printBoard();
    }

    //print results
    if( tictactoe.whoWon() == 1) System.out.println("You lost the game!");
    else if( tictactoe.whoWon() == 2) System.out.println("You won!");
    else System.out.println("Draw!");
  }
}
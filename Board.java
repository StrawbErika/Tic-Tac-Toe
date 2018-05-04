import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

// 1 => X (Computer)
// 2 => O (Human)

class Board {

  int[][] board;
  HashMap<Point,Integer> nodeScores;

  public Board(){
    this.board = new int[3][3]; 
  }


  public boolean isGameOver() {
    if( whoWon() == 1 || whoWon() == 2  || getPossibleStates().size() == 0) return true;
    else return false;
  }

  //returns 1 if computer won, 2 if human won, otherwise 0
  public int whoWon(){

    int player = checkDiagonal(); 
    if( player != 0) return player;

    player = checkRowandCol();
    return player;

  }

  //check for rows and columns if a player won 
  public int checkRowandCol(){
    for (int i = 0; i < 3; ++i) {
      if (this.board[i][0] == this.board[i][1] && this.board[i][0] == this.board[i][2]) return this.board[i][0];
      else if(this.board[0][i] == this.board[1][i] && this.board[0][i] == this.board[2][i]) return this.board[0][i];
    }
    return 0;
  }
  
  //check diagonals of the board if a player won
  public int checkDiagonal(){
    if(this.board[0][0] == this.board[1][1] && this.board[1][1] == this.board[2][2])
      return this.board[0][0];
    else if( this.board[0][2] == this.board[1][1] && this.board[1][1] == this.board[2][0])
      return this.board[0][2];
    else return 0;
  }

  //looks for spots/positions on the board that was not yet marked
  public ArrayList<Point> getPossibleStates(){
    
    ArrayList<Point> emptySpots = new ArrayList<Point>();
    for(int i = 0; i < 3; i++){
      for( int j = 0; j< 3; j++){
        if( board[i][j] == 0) emptySpots.add(new Point(i,j));
      }
    }
    return emptySpots;
  }


  public Point getBestMove(){
    Point bestPoint = new Point();
    int max = Integer.MIN_VALUE;
    for( Point point : nodeScores.keySet()){
      if(max < nodeScores.get(point)){
        max = nodeScores.get(point);
        bestPoint = point;
      }
    }
    return bestPoint;
  }

  //put the move on the board
  public void emulateTurn(int x, int y, int player){
    board[x][y] = player;
  }

  //asks for human's move then simulate the move
  public void usersTurn(int r, int c){
    emulateTurn(r,c,2);
    printBoard();
  }

  public void printBoard(){
    System.out.println("");

    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        if(board[i][j]==1){
            System.out.print("X "); // computer
        }else if(board[i][j]==2){
            System.out.print("O "); // human
        }else{
            System.out.print("- "); //empty spot
        }
      }
      System.out.println();
    }
  }

  public int getScore(ArrayList<Integer> scores, String type){
    Collections.sort(scores); 
    return type == "max"?  scores.get(scores.size()-1): scores.get(0);
  }
  public int performMiniMax( int depth, int whoseTurn){    
    ArrayList<Integer> scores = new ArrayList<Integer>();
    //solves for the utility value
 
    if( whoseTurn == 1 && whoWon() == 1) return +1; // 1 => X
    else if ( whoseTurn == 2 && whoWon() == 2) return -1; // 2 => O
    else if ( getPossibleStates().size() == 0) return 0;

    for( int i = 0; i < getPossibleStates().size(); i++){
      Point point = getPossibleStates().get(i);

      if(whoseTurn == 1){ // gets the max value
          emulateTurn(point.x,point.y,1);
          int score = performMiniMax(depth+1, 2);
          scores.add(score);
          if(depth == 0 ) nodeScores.put(point,score);
      }else if( whoseTurn == 2){ //gets the min value
          emulateTurn(point.x,point.y,2);
          scores.add(performMiniMax(depth + 1, 1));
      }
      board[point.x][point.y] = 0;
    }
    return whoseTurn == 1? getScore(scores,"max"): getScore(scores,"min");
  }
}
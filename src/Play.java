import java.util.ArrayList;
import java.util.Scanner;

public class Play {


    public static void main(String[] args){
        int testNumber = 1000;
        int currentTestNumber = 0;
        int numberOfWin = 0;
        int numberOfDraw = 0;
        Mark playerTurn = Mark.X;
        CPUPlayer cpuPlayer1 = new CPUPlayer(Mark.O);
        CPUPlayer cpuPlayer2 = new CPUPlayer(Mark.X);

        Board board = new Board();
        boolean won = false;

        Scanner in = new Scanner(System.in);
        String input;
        while(testNumber > currentTestNumber){

            if (playerTurn == Mark.X){

                ArrayList<Move> moveList = cpuPlayer2.getNextMoveAB(board);
                int index = (int)(Math.random() * moveList.size());
                board.play(moveList.get(index), playerTurn);
                playerTurn = Mark.O;
            } else {
                ArrayList<Move> moveList = cpuPlayer1.getNextMoveAB(board);
                int index = (int)(Math.random() * moveList.size());
                board.play(moveList.get(index), playerTurn);
                playerTurn = Mark.X;
            }
            if (board.evaluate(Mark.X) == 100){
                board = new Board();
                currentTestNumber++;
                numberOfWin++;
                System.out.println("Test number: " + currentTestNumber);
                //System.out.println("Explored nodes : "+cpuPlayer1.getNumOfExploredNodes());
            }
            else if (board.evaluate(Mark.O) == 100){
                board = new Board();
                currentTestNumber++;
                numberOfWin++;
                System.out.println("Test number: "+currentTestNumber);
                //System.out.println("Explored nodes : "+cpuPlayer1.getNumOfExploredNodes());
            } else if (board.isDraw()) {
                board = new Board();
                currentTestNumber++;
                numberOfDraw++;
                System.out.println("Test number: "+currentTestNumber);
                //System.out.println("Explored nodes :"+cpuPlayer1.getNumOfExploredNodes());

            }
        }
        System.out.println("Number of Win " + numberOfWin);
        System.out.println("Number of Draw " + numberOfDraw);
    }
}

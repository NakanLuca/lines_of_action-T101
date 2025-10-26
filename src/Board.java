import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class Board
{
    private Mark[][] board;

    // Ne pas changer la signature de cette méthode
    public Board() {

        board = new Mark[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Mark.EMPTY;
            }
        }
    }

    // Place la pièce 'mark' sur le plateau, à la
    // position spécifiée dans Move
    //
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark){
        board[m.getRow()][m.getCol()] = mark;

    }

    public void undo(Move m){
        board[m.getRow()][m.getCol()] = Mark.EMPTY;
    }


    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark){
        // rows
        for (int r = 0; r < 3; r++) {
            int mine = 0, opp = 0;
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == mark) { mine++; opp = 0; }
                else if (board[r][c] == Mark.EMPTY) { mine = 0; opp = 0; }
                else { opp++; mine = 0; }
            }
            if (mine == 3) return 100;
            if (opp == 3)  return -100;
        }

        // cols
        for (int c = 0; c < 3; c++) {
            int mine = 0, opp = 0;
            for (int r = 0; r < 3; r++) {
                if (board[r][c] == mark) { mine++; opp = 0; }
                else if (board[r][c] == Mark.EMPTY) { mine = 0; opp = 0; }
                else { opp++; mine = 0; }
            }
            if (mine == 3) return 100;
            if (opp == 3)  return -100;
        }

        // main diagonal
        {
            int mine = 0, opp = 0;
            for (int i = 0; i < 3; i++) {
                if (board[i][i] == mark) { mine++; opp = 0; }
                else if (board[i][i] == Mark.EMPTY) { mine = 0; opp = 0; }
                else { opp++; mine = 0; }
            }
            if (mine == 3) return 100;
            if (opp == 3)  return -100;
        }

        // anti-diagonal
        {
            int mine = 0, opp = 0;
            for (int i = 0; i < 3; i++) {
                int r = i, c = 2 - i;
                if (board[r][c] == mark) { mine++; opp = 0; }
                else if (board[r][c] == Mark.EMPTY) { mine = 0; opp = 0; }
                else { opp++; mine = 0; }
            }
            if (mine == 3) return 100;
            if (opp == 3)  return -100;
        }

        return 0;
    }

    public ArrayList<Move> getMoves(){
        ArrayList<Move> possibleMove = new ArrayList<Move>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Mark.EMPTY){
                    possibleMove.add(new Move(i,j));
                }
            }
        }



        return possibleMove;
    }

    public boolean isFinal(){
        return Math.abs(evaluate(Mark.X)) == 100;
    }

    public boolean isDraw(){
        return getMoves().isEmpty() && !isFinal();
    }


    public void print(){
        for (int i = 0; i < 3; i++) {
            System.out.printf(" %s | %s | %s %n",
                    display(board[i][0]),
                    display(board[i][1]),
                    display(board[i][2]));
            if (i < 2) {
                System.out.println("---+---+---");
            }
        }
    }

    private String display(Mark mark) {
        return (mark == Mark.EMPTY) ? " " : mark.toString();
    }
}

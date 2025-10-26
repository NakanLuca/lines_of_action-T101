import java.util.ArrayList;
import java.util.List;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class CPUPlayer
{

    // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;

    private int currentDepth;
    private final Mark playerMark;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu){
        playerMark = cpu;
    }

    // Ne pas changer cette méthode
    public int  getNumOfExploredNodes(){
        return numExploredNodes;
    }

    // Retourne la liste des coups possibles. Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board) {
        numExploredNodes = 0;
        currentDepth = 0;
        Node result = miniMax(board, playerMark);
        return result.moves;
    }

    public Node miniMax(Board board, Mark currentPlayer) {
        numExploredNodes++;
        //System.out.println("Node explored: " + numExploredNodes);
        //System.out.println("Current depth: " + currentDepth);
        if (board.isFinal()) {
            return new Node(board.evaluate(playerMark), (Move) null);
        }

        List<Move> moves = board.getMoves();
        if (moves.isEmpty()) {
            return new Node(0, (Move) null);
        }

        boolean isMax = (currentPlayer == playerMark);
        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        ArrayList<Move> bestMoves = new ArrayList<>();

        for (Move move : moves) {

            board.play(move, currentPlayer);
            // Change to the next player
            currentDepth++;
            Node child = miniMax(board, opp(currentPlayer));
            currentDepth--;
            board.undo(move);

            int score = child.score;

            if (isMax) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMoves.clear();
                    bestMoves.add(move);
                } else if (score == bestScore) {
                    bestMoves.add(move);
                }
            } else { // MIN
                if (score < bestScore) {
                    bestScore = score;
                    bestMoves.clear();
                    bestMoves.add(move);
                } else if (score == bestScore) {
                    bestMoves.add(move);
                }
            }
        }

        return new Node(bestScore, bestMoves);
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board){
        numExploredNodes = 0;
        Node result = alphaBeta(board, playerMark, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return result.moves;
    }

    private Node alphaBeta(Board board, Mark currentPlayer, int alpha, int beta) {
        numExploredNodes++;
        //System.out.println("Node explored: " + numExploredNodes);
        //System.out.println("Current depth: " + currentDepth);
        if (board.isFinal()) {
            return new Node(board.evaluate(playerMark), (Move) null);
        }

        List<Move> moves = board.getMoves();
        if (moves.isEmpty()) {
            return new Node(0, (Move) null);
        }

        boolean isMax = (currentPlayer == playerMark);
        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        ArrayList<Move> bestMoves = new ArrayList<>();

        for (Move move : moves) {

            board.play(move, currentPlayer);
            // Change to the next player
            //currentDepth++;
            Node child = alphaBeta(board, opp(currentPlayer), alpha, beta);
            //currentDepth--;
            board.undo(move);

            int score = child.score;

            if (isMax) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMoves.clear();
                    bestMoves.add(move);
                } else if (score == bestScore) {
                    bestMoves.add(move);
                }
                alpha = Math.max(alpha, bestScore);
            } else { // MIN
                if (score < bestScore) {
                    bestScore = score;
                    bestMoves.clear();
                    bestMoves.add(move);
                } else if (score == bestScore) {
                    bestMoves.add(move);
                }
                beta = Math.min(beta, bestScore);
            }
            if (beta < alpha) {
                break;
            }
        }

        return new Node(bestScore, bestMoves);
    }


    public static Mark opp(Mark mark){
        return mark == Mark.X ? Mark.O : Mark.X;
    }

}

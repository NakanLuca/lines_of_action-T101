import java.util.ArrayList;

class Node {
    int score;
    ArrayList<Move> moves;

    Node(int score, Move move) {
        this.score = score;
        this.moves = new ArrayList<>();
        if (move != null) this.moves.add(move);
    }

    Node(int score, ArrayList<Move> moves) {
        this.score = score;
        this.moves = new ArrayList<>(moves);
    }
}
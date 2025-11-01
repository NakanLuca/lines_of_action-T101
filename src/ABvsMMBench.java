import java.util.*;
import java.util.concurrent.TimeUnit;

public class ABvsMMBench {

    static class RunResult {
        long nanos;
        int nodes;
        ArrayList<Move> bestMoves;
        RunResult(long nanos, int nodes, ArrayList<Move> bestMoves) {
            this.nanos = nanos; this.nodes = nodes; this.bestMoves = bestMoves;
        }
    }

    static RunResult runMinimax(Board b, Mark toMove) {
        CPUPlayer ai = new CPUPlayer(toMove);
        long t0 = System.nanoTime();
        ArrayList<Move> best = ai.getNextMoveMinMax(b);
        long t1 = System.nanoTime();
        return new RunResult(t1 - t0, ai.getNumOfExploredNodes(), best);
    }

    static RunResult runAlphaBeta(Board b, Mark toMove) {
        CPUPlayer ai = new CPUPlayer(toMove);
        long t0 = System.nanoTime();
        ArrayList<Move> best = ai.getNextMoveAB(b);
        long t1 = System.nanoTime();
        return new RunResult(t1 - t0, ai.getNumOfExploredNodes(), best);
    }


    static Board boardFromRows(String r0, String r1, String r2) {
        Board b = new Board();
        for (int r = 0; r < 3; r++) {
            String row = switch (r) { case 0 -> r0; case 1 -> r1; default -> r2; };
            for (int c = 0; c < 3; c++) {
                char ch = row.charAt(c);
                if (ch == 'X') b.play(new Move(r, c), Mark.X);
                else if (ch == 'O') b.play(new Move(r, c), Mark.O);
            }
        }
        return b;
    }

    static String movesToString(List<Move> ms) {
        ArrayList<String> out = new ArrayList<>();
        for (Move m : ms) out.add("(" + m.getRow() + "," + m.getCol() + ")");
        Collections.sort(out);
        return out.toString();
    }

    static void benchOne(String name, Board b, Mark toMove) {
        // Warm up (JIT) a bit
        for (int i = 0; i < 3; i++) { runAlphaBeta(b, toMove); runMinimax(b, toMove); }

        RunResult mm = runMinimax(b, toMove);
        RunResult ab = runAlphaBeta(b, toMove);

        double mmMs = mm.nanos / 1_000_000.0;
        double abMs = ab.nanos / 1_000_000.0;
        double speedup = mmMs / Math.max(1e-9, abMs);
        double prune = 1.0 - (ab.nodes / (double)Math.max(1, mm.nodes));

        // Compare move sets (order-agnostic)
        String mmMoves = movesToString(mm.bestMoves);
        String abMoves = movesToString(ab.bestMoves);
        boolean sameMoves = mmMoves.equals(abMoves);

        System.out.println("=== " + name + " (to move: " + toMove + ") ===");
        System.out.printf("Minimax: nodes=%d, time=%.3f ms, bestMoves=%s%n", mm.nodes, mmMs, mmMoves);
        System.out.printf("AlphaBeta: nodes=%d, time=%.3f ms, bestMoves=%s%n", ab.nodes, abMs, abMoves);
        System.out.printf("Speedup: ×%.2f, Prune ratio: %.1f%%%n", speedup, 100.0 * prune);
        System.out.println("Same optimal move set? " + (sameMoves ? "YES" : "NO (BUG)"));
        System.out.println();
    }

    public static void main(String[] args) {
        benchOne("Empty board O", boardFromRows("X..", "...", "..."), Mark.O);
        benchOne("Empty board X", boardFromRows("...", "...", "..."), Mark.X);
        //benchOne("Early midgame", boardFromRows("X..", ".O.", "..."), Mark.X);
        //benchOne("Late game", boardFromRows("XO.", "OX.", "..X"), Mark.O);
        //benchOne("Immediate win threat", boardFromRows("XX.", ".O.", "..."), Mark.X);
        //benchOne("Block needed", boardFromRows("OO.", ".X.", "..."), Mark.X);
    }
}
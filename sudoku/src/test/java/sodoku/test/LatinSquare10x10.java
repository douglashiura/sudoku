package sodoku.test;
import java.util.*;

public class LatinSquare10x10 {
    private static final int N = 16;
    private int[][] board = new int[N][N];
    private Random rnd;

    public LatinSquare10x10(long seed) {
        rnd = new Random(seed);
    }

    public LatinSquare10x10() {
        rnd = new Random();
    }

    // tenta preencher o board com um Latin Square via backtracking
    public boolean generateSolved() {
        // inicializar zeros
        for (int i = 0; i < N; i++)
            Arrays.fill(board[i], 0);
        return fillCell(0, 0);
    }

    private boolean fillCell(int row, int col) {
        if (row == N) return true; // terminado

        int nextRow = (col == N - 1) ? row + 1 : row;
        int nextCol = (col == N - 1) ? 0 : col + 1;

        // números embaralhados 1..N para aleatoriedade
        List<Integer> candidates = new ArrayList<>();
        for (int v = 1; v <= N; v++) candidates.add(v);
        Collections.shuffle(candidates, rnd);

        for (int value : candidates) {
            if (isSafe(row, col, value)) {
                board[row][col] = value;
                if (fillCell(nextRow, nextCol)) return true;
                board[row][col] = 0; // backtrack
            }
        }
        return false; // sem valor válido aqui
    }

    // checa se value já existe na linha ou coluna
    private boolean isSafe(int row, int col, int value) {
        for (int i = 0; i < N; i++) {
            if (board[row][i] == value) return false;
            if (board[i][col] == value) return false;
        }
        return true;
    }

    // remove 'count' células do tabuleiro definindo-as como 0 (esconder)
    public void removeCells(int count) {
        if (count <= 0) return;
        int removed = 0;
        List<int[]> positions = new ArrayList<>();
        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++)
                positions.add(new int[]{r, c});
        Collections.shuffle(positions, rnd);

        for (int[] pos : positions) {
            if (removed >= count) break;
            int r = pos[0], c = pos[1];
            if (board[r][c] != 0) {
                board[r][c] = 0;
                removed++;
            }
        }
    }

    public int[][] getBoardCopy() {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) System.arraycopy(board[i], 0, copy[i], 0, N);
        return copy;
    }

    public static void printBoard(int[][] b) {
        for (int r = 0; r < b.length; r++) {
            for (int c = 0; c < b[r].length; c++) {
                System.out.printf("%2d ", b[r][c]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        LatinSquare10x10 gen = new LatinSquare10x10(); // ou new LatinSquare10x10(1234L) para reprodutibilidade
        boolean ok = gen.generateSolved();
        if (!ok) {
            System.out.println("Falha ao gerar Latin square.");
            return;
        }

        System.out.println("=== Tabuleiro resolvido (completo) ===");
        printBoard(gen.getBoardCopy());

        // Faça uma cópia do resolvido se quiser manter solução separada
        int[][] solution = gen.getBoardCopy();

        // Transformar em puzzle: remover X células (0 = a descobrir)
        int removals = 30; // ajuste quantidade de zeros (dificuldade)
        gen.removeCells(removals);

        System.out.println("\n=== Puzzle (zeros = células a descobrir) ===");
        printBoard(gen.getBoardCopy());

        System.out.println("\n=== Solução (para referência) ===");
        printBoard(solution);
    }
}

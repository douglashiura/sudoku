package sudoku.dynamic;

import java.util.Random;

public class SimulatedAnnealingSudoku {
	private static final Random RANDOM = new Random();
	public int[][] board;
	public boolean[][] fixedTiles;
	private final int size;

	public SimulatedAnnealingSudoku(int[][] input) {
		size = input.length;
		board = input;
		startCandidates();
	}

	private SimulatedAnnealingSudoku(int[][] cloneBoard, boolean[][] fixed) {
		size = cloneBoard.length;
		this.board = cloneBoard;
		this.fixedTiles = fixed;
	}

	public int costFunction() {
		int sum = 0;
		for (int row = 0; row < size; row++) {
			int rowCost[] = new int[size];
			for (int column = 0; column < size; column++) {
				if (rowCost[board[row][column] - 1] != 0) {
					sum++;
				}
				rowCost[board[row][column] - 1] = 1;
			}
		}
		for (int row = 0; row < size; row++) {
			int rowCost[] = new int[size];
			for (int column = 0; column < size; column++) {
				if (rowCost[board[column][row] - 1] != 0) {
					sum++;
				}
				rowCost[board[column][row] - 1] = 1;
			}
		}
		return sum;
	}

	private void startCandidates() {
		fixedTiles = new boolean[size][size];
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				fixedTiles[row][column] = board[row][column] != 0;
			}
		}
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (board[row][column] == 0) {
					for (int candidate = 1; candidate <= size; candidate++) {
						if (!hasConflict(row, column, candidate)) {
							board[row][column] = candidate;
							break;
						}
					}
				}
			}
		}
	}

	public void swap() {
		int row;
		int column;
		do {
			row = RANDOM.nextInt(size);
			column = RANDOM.nextInt(size);

		} while (fixedTiles[row][column]);

		int rowTwo;
		int columnTwo;
		do {
			rowTwo = RANDOM.nextInt(size);
			columnTwo = RANDOM.nextInt(size);
		} while (fixedTiles[rowTwo][columnTwo]);
		int swp = board[row][column];
		board[row][column] = board[rowTwo][columnTwo];
		board[rowTwo][columnTwo] = swp;
	}

	private boolean hasConflict(int testRow, int testColumn, int candidate) {
		int midRegion = (int) Math.sqrt(size);
		int lr = testRow / midRegion, cr = testColumn / midRegion;
		for (int row = lr * midRegion; row < (lr + 1) * midRegion; row++) {
			for (int column = cr * midRegion; column < (cr + 1) * midRegion; column++) {
				if (board[row][column] == candidate) {
					return true;
				}
			}
		}
		return false;
	}

	private int[][] cloneBoard() {
		int[][] newBoard = new int[size][size];
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				newBoard[row][column] = board[row][column];
			}
		}
		return newBoard;
	}

	public SimulatedAnnealingSudoku newClone() {
		return new SimulatedAnnealingSudoku(cloneBoard(), fixedTiles);
	}

}

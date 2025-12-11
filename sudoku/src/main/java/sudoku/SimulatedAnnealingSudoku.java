package sudoku;

import java.util.Random;

public class SimulatedAnnealingSudoku {
	private static final Random RANDOM = new Random();
	public int[][] board;
	public boolean[][] fixedTiles;
	private final static int SIZE=9;
	public SimulatedAnnealingSudoku(int[][] input) {
		board = input;
		startCandidates();
	}
	private  SimulatedAnnealingSudoku(int[][] cloneBoard, boolean[][] fixed) {
		this.board = cloneBoard;
		this.fixedTiles = fixed;
	}

	public int costFunction() {
		int sum = 0;
		for (int row = 0; row < 9; row++) {
			int rowCost[] = new int[9];
			for (int column = 0; column < 9; column++) {
				if (rowCost[board[row][column] - 1] != 0) {
					sum++;
				}
				rowCost[board[row][column] - 1] = 1;
			}
		}
		for (int row = 0; row < 9; row++) {
			int rowCost[] = new int[9];
			for (int column = 0; column < 9; column++) {
				if (rowCost[board[column][row] - 1] != 0) {
					sum++;
				}
				rowCost[board[column][row] - 1] = 1;
			}
		}
		return sum;
	}

	private void startCandidates() {
		fixedTiles = new boolean[9][9];
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				fixedTiles[row][column] = board[row][column] != 0;
			}
		}
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				if (board[row][column] == 0) {
					for (int candidate = 1; candidate <= 9; candidate++) {
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
			row = RANDOM.nextInt(SIZE);
			column = RANDOM.nextInt(SIZE);

		} while (fixedTiles[row][column]);

		int rowTwo;
		int columnTwo;
		do {
			rowTwo = RANDOM.nextInt(SIZE);
			columnTwo = RANDOM.nextInt(SIZE);
		} while (fixedTiles[rowTwo][columnTwo]);
		int swp = board[row][column];
		board[row][column] = board[rowTwo][columnTwo];
		board[rowTwo][columnTwo] = swp;
	}

	private boolean hasConflict(int testRow, int testColumn, int candidate) {
		int lr = testRow / 3, cr = testColumn / 3;
		for (int row = lr * 3; row < (lr + 1) * 3; row++) {
			for (int column = cr * 3; column < (cr + 1) * 3; column++) {
				if (board[row][column] == candidate) {
					return true;
				}
			}
		}
		return false;
	}

	private int[][] cloneBoard() {
		int[][] newBoard = new int[9][9];
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				newBoard[row][column] = board[row][column];
			}
		}
		return newBoard;
	}

	public SimulatedAnnealingSudoku newClone() {
		return new SimulatedAnnealingSudoku(cloneBoard(), fixedTiles);
	}

}

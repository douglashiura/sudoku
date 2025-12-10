package sudoku.four;

import java.util.Random;

public class SimulatedAnnealingFourSudoku {
	private static final Random RANDOM = new Random();
	public int[][] board;
	public boolean[][] fixedTiles;
	private static final int SIZE =4;
	
	public SimulatedAnnealingFourSudoku(int[][] input) {
		board = input;
		startCandidates();
	}
	private  SimulatedAnnealingFourSudoku(int[][] cloneBoard, boolean[][] fixed) {
		this.board = cloneBoard;
		this.fixedTiles = fixed;
	}

	public int costFunction() {
		int sum = 0;
		for (int row = 0; row < SIZE; row++) {
			int rowCost[] = new int[SIZE];
			for (int column = 0; column < SIZE; column++) {
				if (rowCost[board[row][column] - 1] != 0) {
					sum++;
				}
				rowCost[board[row][column] - 1] = 1;
			}
		}
		for (int row = 0; row < SIZE; row++) {
			int rowCost[] = new int[SIZE];
			for (int column = 0; column < SIZE; column++) {
				if (rowCost[board[column][row] - 1] != 0) {
					sum++;
				}
				rowCost[board[column][row] - 1] = 1;
			}
		}
		return sum;
	}

	private void startCandidates() {
		fixedTiles = new boolean[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				fixedTiles[row][column] = board[row][column] != 0;
			}
		}
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				if (board[row][column] == 0) {
					for (int candidate = 1; candidate <= SIZE; candidate++) {
						if (!hasConflict(row, column, candidate)) {
							board[row][column] = candidate;
							break;
						}
					}
				}
			}
		}
	}

	public void swap(int row, int column) {
		int mid = (int) Math.sqrt(SIZE);
		int lr = row / mid, cr = column / mid;
		while (fixedTiles[row][column]) {
			row = RANDOM.nextInt(mid) + lr * mid;
			column = RANDOM.nextInt(mid) + cr * mid;
		}
		int rowTwo = row, columnTwo = column;
		while (fixedTiles[rowTwo][columnTwo]) {
			rowTwo = RANDOM.nextInt(mid) + lr * mid;
			columnTwo = RANDOM.nextInt(mid) + cr * mid;
		}
		int swp = board[row][column];
		board[row][column] = board[rowTwo][columnTwo];
		board[rowTwo][columnTwo] = swp;
	}

	private boolean hasConflict(int testRow, int testColumn, int candidate) {
		int mid = (int) Math.sqrt(SIZE);
		int lr = testRow / mid, cr = testColumn / mid;
		for (int row = lr * mid; row < (lr + 1) * mid; row++) {
			for (int column = cr * mid; column < (cr + 1) * mid; column++) {
				if (board[row][column] == candidate) {
					return true;
				}
			}
		}
		return false;
	}

	private int[][] cloneBoard() {
		int[][] newBoard = new int[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				newBoard[row][column] = board[row][column];
			}
		}
		return newBoard;
	}

	public SimulatedAnnealingFourSudoku newClone() {
		return new SimulatedAnnealingFourSudoku(cloneBoard(), fixedTiles);
	}

}

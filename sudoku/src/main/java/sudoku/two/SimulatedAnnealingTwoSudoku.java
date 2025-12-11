package sudoku.two;

import java.util.Random;

import sudoku.BoardPrinter;

public class SimulatedAnnealingTwoSudoku {
	private static final Random RANDOM = new Random();
	public int[][] board;
	public boolean[][] fixedTiles;
	private static final int SIZE = 2;

	public SimulatedAnnealingTwoSudoku(int[][] input) {
		board = input;
		startCandidates();
	}

	private SimulatedAnnealingTwoSudoku(int[][] cloneBoard, boolean[][] fixed) {
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
		BoardPrinter.print(board);
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
		for (int column = 0; column < SIZE; column++) {
			if(board[testRow][column]==candidate) {
				return true;
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

	public SimulatedAnnealingTwoSudoku newClone() {
		return new SimulatedAnnealingTwoSudoku(cloneBoard(), fixedTiles);
	}

}

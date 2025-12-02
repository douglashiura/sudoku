package sudoku;

public class SudokuCombinatory {
	final int ROW = 0, COLUMN = 1, CANDIDATE = 2;

	private int[][][] candidateBoard;
	public int nodes = 0;
	public int[][] output;
	public int solutions = 0;
	public int kicks = 0;

	public SudokuCombinatory(int[][] board) {
		output = board;
		this.candidateBoard = new int[9][9][10];
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				this.candidateBoard[row][column][0] = board[row][column];
			}
		}
		auxiliaryState();
	}

	public boolean resolve() {
		nodes++;
		boolean response = false;
		int[] emptyBoard = fillNextTile();
		int[] single = nextSingle();
		int row = 0, column = 0;
		if (emptyBoard[ROW] == 9) {
			storeASolution();
			response = true;
		} else if (single[CANDIDATE] != 0) {
			row = single[ROW];
			column = single[COLUMN];
			candidateBoard[row][column][0] = single[CANDIDATE];
			removeCandidateAuxiliary(row, column, single[CANDIDATE]);
			response = resolve();
			candidateBoard[row][column][single[CANDIDATE]] = 1;
			candidateBoard[row][column][0] = 0;
			auxiliaryState();
		} else {
			for (int n = 1; n <= 9; n++) {
				kicks++;
				row = emptyBoard[ROW];
				column = emptyBoard[COLUMN];
				if (nonConflict(candidateBoard, row, column, n)) {
					candidateBoard[row][column][0] = n;
					removeCandidateAuxiliary(row, column, n);
					if (resolve()) {
						response = true;
						break; // so a primeira resposta
					}
					candidateBoard[row][column][0] = 0;
					auxiliaryState();

				}
			}
		}
		return response;
	}

	private int[] fillNextTile() {
		int minCandidate = 10;
		for (int row = 0; row < 9; row++)
			for (int column = 0; column < 9; column++)
				if (candidateBoard[row][column][0] == 0) {
					int state = 0;
					for (int candidate = 1; candidate <= 9; candidate++)
						if (candidateBoard[row][column][candidate] == 1)
							state++;
					if (state < minCandidate)
						minCandidate = state;
				}

		for (int row = 0; row < 9; row++)
			for (int column = 0; column < 9; column++)
				if (candidateBoard[row][column][0] == 0) {
					int est = 0;
					for (int n = 1; n <= 9; n++)
						if (candidateBoard[row][column][n] == 1)
							est++;
					if (est == minCandidate)
						return new int[] { row, column };
				}
		return new int[] { 9, 9 };

	}

	private int[] nextSingle() {
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				if (candidateBoard[row][column][0] == 0) {
					int state = 0;
					int rowCandidate = 0;
					for (int candidate = 1; candidate <= 9; candidate++) {
						if (candidateBoard[row][column][candidate] != 0) {
							state++;
							rowCandidate = candidate;
							if (hasSingle(row, column, candidate)) {
								state = 1;
								break;
							}
						}
					}
					if (state == 1 && nonConflict(candidateBoard, column, row, rowCandidate)) {
						return new int[] { row, column, rowCandidate };
					}
				}
			}

		}
		return new int[] { 9, 9, 0 };
	}

	private void removeCandidateAuxiliary(int row, int column, int candidate) {
		for (int iColumn = 0; iColumn < 9; iColumn++) {
			candidateBoard[row][iColumn][candidate] = 0;
		}
		for (int l = 0; l < 9; l++) {
			candidateBoard[l][column][candidate] = 0;
		}
		int lr = row / 3, cr = column / 3;
		for (int iRow = lr * 3; iRow < (lr + 1) * 3; iRow++) {
			for (int iColumn = cr * 3; iColumn < (cr + 1) * 3; iColumn++) {
				candidateBoard[iRow][iColumn][candidate] = 0;
			}
		}
	}

	private boolean hasSingle(int row, int column, int candidate) {
		int nRow = 0, nColumn = 0, nBlock = 0;
		for (int iColumn = 0; iColumn < 9; iColumn++)
			if (candidateBoard[row][iColumn][candidate] != 0)
				nColumn++;
		if (nColumn == 1)
			return true;

		for (int iRow = 0; iRow < 9; iRow++)
			if (candidateBoard[iRow][column][candidate] != 0)
				nRow++;
		if (nRow == 1)
			return true;
		int lr = row / 3, cr = column / 3;
		for (int iRow = lr * 3; iRow < (lr + 1) * 3; iRow++)
			for (int iColumn = cr * 3; iColumn < (cr + 1) * 3; iColumn++)
				if (candidateBoard[iRow][iColumn][candidate] != 0)
					nBlock++;
		if (nBlock == 1)
			return true;
		return false;
	}

	private void auxiliaryState() {
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				for (int candidate = 1; candidate <= 9; candidate++) {
					if (this.candidateBoard[row][column][0] == 0
							&& nonConflict(candidateBoard, row, column, candidate)) {
						this.candidateBoard[row][column][candidate] = 1;
					}
				}
			}
		}
	}

	public static boolean nonConflict(int[][][] board, int row, int column, int candidate) {
		for (int iColumn = 0; iColumn < 9; iColumn++) {
			if (board[row][iColumn][0] == candidate) {
				return false;
			}
		}
		for (int iRow = 0; iRow < 9; iRow++) {
			if (board[iRow][column][0] == candidate) {
				return false;
			}
		}
		int lr = row / 3, cr = column / 3;
		for (int iRow = lr * 3; iRow < (lr + 1) * 3; iRow++) {
			for (int iColumn = cr * 3; iColumn < (cr + 1) * 3; iColumn++) {
				if (board[iRow][iColumn][0] == candidate) {
					return false;
				}
			}
		}
		return true;
	}

	private void storeASolution() {
		solutions++;
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				output[row][column] = candidateBoard[row][column][0];
			}
		}
	}

}
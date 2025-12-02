package sudoku;

public class SudokuRecursive {

	private int[][] board;
	public int nodes = 0;
	public int[][] output = new int[9][9];
	public int solutions = 0;

	public SudokuRecursive(int[][] board) {
		this.board = board;
	}

	boolean nonConflict(int row, int column, int candidate) {
		for (int iColumn = 0; iColumn < 9; iColumn++)
			if (board[row][iColumn] == candidate)
				return false;
		for (int iRow = 0; iRow < 9; iRow++)
			if (board[iRow][column] == candidate)
				return false;
		int lr = row / 3, cr = column / 3;
		for (int iRow = lr * 3; iRow < (lr + 1) * 3; iRow++)
			for (int iColunm = cr * 3; iColunm < (cr + 1) * 3; iColunm++)
				if (board[iRow][iColunm] == candidate)
					return false;
		return true;
	}

	public boolean resolve(int row, int column) {
		nodes++;
		if (column == 9) {
			column = 0;
			row--;
			if (row == -1) {
				storeASolution();
				return true;
			}
		}
		if (board[row][column] != 0) // pula
			return resolve(row, column + 1);
		boolean isSolved = false;
		for (int n = 1; n <= 9; n++) {
			if (nonConflict(row, column, n)) {
				board[row][column] = n;
				if (resolve(row, column + 1)) { // todas as soluções
					isSolved = true;
				}
//				if (resolve(lin, col + 1)) { // uma solucao
//					resolvido = true;
//					break;
//				}
			}

		}
		board[row][column] = 0; // voltar estado original
		return isSolved;
	}

	private void storeASolution() {
		solutions++;
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				output[row][column] = board[row][column];
			}
		}
	}

}
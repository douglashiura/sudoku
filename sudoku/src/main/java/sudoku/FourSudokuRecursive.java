package sudoku;

public class FourSudokuRecursive {
private final int SIZE=4;
	private int[][] board;
	public int nodes = 0;
	public int[][] output = new int[4][4];
	public int solutions = 0;

	public FourSudokuRecursive(int[][] board) {
		this.board = board;
	}

	boolean nonConflict(int row, int column, int candidate) {
		for (int iColumn = 0; iColumn < SIZE; iColumn++)
			if (board[row][iColumn] == candidate)
				return false;
		for (int iRow = 0; iRow < SIZE; iRow++)
			if (board[iRow][column] == candidate)
				return false;
		int lr = row / 2, cr = column / 2;
		for (int iRow = lr * 2; iRow < (lr + 1) * 2; iRow++)
			for (int iColunm = cr * 2; iColunm < (cr + 1) * 2; iColunm++)
				if (board[iRow][iColunm] == candidate)
					return false;
		return true;
	}

	public boolean resolve(int row, int column) {
		nodes++;
		if (column == SIZE) {
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
		for (int n = 1; n <= SIZE; n++) {
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
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				output[row][column] = board[row][column];
			}
		}
	}

}
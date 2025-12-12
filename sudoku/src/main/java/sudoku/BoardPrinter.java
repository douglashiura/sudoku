package sudoku;

public class BoardPrinter {
	public static void print(int[][] board) {
		for (int column[] : board) {
			for (int row : column) {
				System.out.print(row);
				System.out.print("	" );
			}
			System.out.println();
		}
	}

	public static String asStringRow(int[][] board) {
		String inLine = "";
		for (int[] row : board) {
			for (int column : row) {
				inLine += column;
			}
		}
		return inLine;
	}
}

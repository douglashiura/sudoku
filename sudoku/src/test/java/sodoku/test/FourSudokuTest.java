package sodoku.test;

import org.junit.jupiter.api.Test;

import sudoku.BoardPrinter;
import sudoku.FourSudokuRecursive;

public class FourSudokuTest {

	@Test
	void backtracking() throws Exception {
		int[][] board = new int[][] { 
			{ 0, 3, 0, 2 }, 
			{ 0, 0, 4, 3 },
			{ 2, 0, 3, 0 },
			{ 3, 4, 0, 0 }		
		};
		StringBuilder response = new StringBuilder();
		String problem = BoardPrinter.asStringRow(board);
		FourSudokuRecursive sudoku = new FourSudokuRecursive(board);
		long ini = System.currentTimeMillis();
		sudoku.resolve(3, 0);
		long end = System.currentTimeMillis();
		response.append(problem);
		response.append(",	");
		response.append((end - ini));
		response.append(",	");
		response.append(sudoku.nodes);
		response.append(",	");
		response.append(sudoku.solutions);
		BoardPrinter.print(sudoku.output);
		System.out.println(response.toString());
		
	}

}

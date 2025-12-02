package sodoku.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Test;

import sudoku.BoardPrinter;
import sudoku.SudokuCombinatory;

public class CombinatorySudokuTest {

	@Test
	public void easy() throws IOException, URISyntaxException {
		List<int[][]> rawBoards = new BoardReader().boards();
		StringBuilder response = new StringBuilder();
		for (int[][] board : rawBoards) {
			String problem = BoardPrinter.asStringRow(board);
			long ini = System.currentTimeMillis();
			SudokuCombinatory sudoku = new SudokuCombinatory(board);
			sudoku.resolve();
			long end = System.currentTimeMillis();
			response.append("\n");
			response.append(problem);
			response.append(",	");
			response.append((end - ini));
			response.append(",	");
			response.append(sudoku.nodes);
			response.append(",	");
			response.append(sudoku.solutions);
			response.append(",	");
			response.append(sudoku.kicks);
		}
		System.out.println(response.toString());
	}

	@Test
	public void hard() throws IOException, URISyntaxException {
		List<int[][]> rawBoards = new InlineReader().boards();
		for (int[][] board : rawBoards) {
			StringBuilder response = new StringBuilder();
			String problem = BoardPrinter.asStringRow(board);
			long ini = System.currentTimeMillis();
			SudokuCombinatory sudoku = new SudokuCombinatory(board);
			sudoku.resolve();
			long end = System.currentTimeMillis();
			response.append(problem);
			response.append(",	");
			response.append((end - ini));
			response.append(",	");
			response.append(sudoku.nodes);
			response.append(",	");
			response.append(sudoku.solutions);
			response.append(",	");
			response.append(sudoku.kicks);
			System.out.println(response.toString());
		}
	}

}
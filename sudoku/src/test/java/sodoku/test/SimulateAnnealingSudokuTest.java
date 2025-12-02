package sodoku.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Test;

import sudoku.BoardPrinter;
import sudoku.SimulatedAnnealing;
import sudoku.SimulatedAnnealingSudoku;

public class SimulateAnnealingSudokuTest {


	@Test
	public void easy() throws IOException, URISyntaxException {
		List<int[][]> rawBoards = new BoardReader().boards();
		for (int[][] board : rawBoards) {
			StringBuilder response = new StringBuilder();
			String problem = BoardPrinter.asStringRow(board);
			long ini = System.currentTimeMillis();
			SimulatedAnnealingSudoku sudoku = new SimulatedAnnealingSudoku(board);
			SimulatedAnnealing annealing = new SimulatedAnnealing();
			SimulatedAnnealingSudoku best = annealing.simulate(sudoku);
			long end = System.currentTimeMillis();
			response.append("\n");
			response.append(problem);
			response.append(",	");
			response.append((end - ini));
			response.append(",	");
			response.append(best.costFunction());
			response.append(",	");
			response.append(BoardPrinter.asStringRow(sudoku.board));
			System.out.println(response.toString());
			BoardPrinter.print(best.board);
		}
	}

	@Test
	public void hard() throws IOException, URISyntaxException {
		List<int[][]> rawBoards = new InlineReader().boards();
		for (int[][] board : rawBoards) {
			StringBuilder response = new StringBuilder();
			String problem = BoardPrinter.asStringRow(board);
			long ini = System.currentTimeMillis();
			SimulatedAnnealingSudoku sudoku = new SimulatedAnnealingSudoku(board);
			SimulatedAnnealing annealing = new SimulatedAnnealing();
			SimulatedAnnealingSudoku best = annealing.simulate(sudoku);
			long end = System.currentTimeMillis();
			response.append(problem);
			response.append(",	");
			response.append((end - ini));
			response.append(",	");
			response.append(best.costFunction());
			response.append(",	");
			response.append(BoardPrinter.asStringRow(sudoku.board));
			System.out.println(response.toString());
			BoardPrinter.print(best.board);
		}
	}

}
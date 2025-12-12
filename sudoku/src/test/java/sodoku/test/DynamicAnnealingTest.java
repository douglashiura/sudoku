package sodoku.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Test;

import sudoku.BoardPrinter;
import sudoku.dynamic.SimulatedAnnealing;
import sudoku.dynamic.SimulatedAnnealingSudoku;

public class DynamicAnnealingTest {

	@Test
	public void easyNineByNine() throws IOException, URISyntaxException {
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
	void fourByFour() throws Exception {
		int[][] board = new int[][] { { 0, 3, 0, 2 }, { 0, 0, 4, 3 }, { 2, 0, 3, 0 }, { 3, 4, 0, 0 } };
		StringBuilder response = new StringBuilder();
		String problem = BoardPrinter.asStringRow(board);
		SimulatedAnnealingSudoku sudoku = new SimulatedAnnealingSudoku(board);
		SimulatedAnnealing annealing = new SimulatedAnnealing();
		long ini = System.currentTimeMillis();
		SimulatedAnnealingSudoku best = annealing.simulate(sudoku);
		long end = System.currentTimeMillis();
		response.append(problem);
		response.append(",	");
		response.append((end - ini));
		response.append(",	");
		response.append(best.costFunction());
		response.append(",	");
		BoardPrinter.print(best.board);
		System.out.println(response.toString());
	}
	

	@Test
	void sixteenBySixteen() throws Exception {
		int[][] board = {
			    {14,  1,  6, 15, 11, 13,  9, 12,  7, 10,  0,  4,  0,  2,  3, 16},
			    {10, 15,  4,  0,  9,  6, 16,  3,  5,  0,  2,  8,  7, 12, 14,  1},
			    {11,  4, 14, 10,  6,  1,  5, 15,  8,  2, 12, 16,  9,  3,  7, 13},
			    { 0,  0, 13,  0,  8,  5, 10,  7,  0,  1,  0,  2,  6, 11, 12,  3},
			    { 0,  3,  7, 12, 13, 16,  1, 10, 15,  4, 14,  6, 11,  8,  2,  0},
			    { 5,  0,  3, 16,  7, 11,  6,  4, 12, 14, 13,  0,  8,  1, 15,  2},
			    { 3, 13,  5,  6,  2, 14, 11,  9, 10, 16,  7, 15, 12,  4,  1,  8},
			    { 1, 10, 15,  3,  5,  4,  2, 11,  6, 13, 16,  9,  0,  7,  8, 12},
			    { 0,  2, 12,  5,  0, 15,  0,  6,  0,  8,  3, 11,  4,  9, 16, 10},
			    {13, 12,  1, 14,  3,  9,  8,  0, 11,  7,  6,  5, 10, 16,  0, 15},
			    {16,  6,  8,  1, 15, 12,  4, 14,  3,  5, 10,  7,  2, 13, 11,  0},
			    { 2,  7, 11,  8, 10,  3, 13,  5,  9, 15,  1, 12,  0, 14,  6,  4},
			    {12, 11, 10,  0, 14,  8,  3, 16,  2,  6,  9,  1, 13, 15,  5,  7},
			    { 6,  8,  2,  7, 16,  0, 12,  1,  4,  9, 11,  3, 15,  5, 13, 14},
			    { 8, 16,  9,  2,  4,  7, 15, 13,  1, 12,  5, 14,  3,  6,  0, 11},
			    { 4,  5,  0, 11, 12,  0,  7,  8, 14,  3, 15,  0,  0, 10,  0,  6}
			};


		StringBuilder response = new StringBuilder();
		String problem = BoardPrinter.asStringRow(board);
		SimulatedAnnealingSudoku sudoku = new SimulatedAnnealingSudoku(board);
		SimulatedAnnealing annealing = new SimulatedAnnealing();
		long ini = System.currentTimeMillis();
		SimulatedAnnealingSudoku best = annealing.simulate(sudoku);
		long end = System.currentTimeMillis();
		response.append(problem);
		response.append(",	");
		response.append((end - ini));
		response.append(",	");
		response.append(best.costFunction());
		response.append(",	");
		BoardPrinter.print(best.board);
		System.out.println(response.toString());
	}
}

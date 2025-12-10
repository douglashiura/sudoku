package sodoku.test;

import org.junit.jupiter.api.Test;

import sudoku.BoardPrinter;
import sudoku.four.FourSudokuRecursive;
import sudoku.four.SimulatedAnnealingFour;
import sudoku.four.SimulatedAnnealingFourSudoku;

public class FourSudokuTest {

	@Test
	void backtracking() throws Exception {
		int[][] board = new int[][] { { 0, 3, 0, 2 }, { 0, 0, 4, 3 }, { 2, 0, 3, 0 }, { 3, 4, 0, 0 } };
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

	@Test
	void simulateAnnealing() throws Exception {
		int[][] board = new int[][] { { 0, 3, 0, 2 }, { 0, 0, 4, 3 }, { 2, 0, 3, 0 }, { 3, 4, 0, 0 } };
		StringBuilder response = new StringBuilder();
		String problem = BoardPrinter.asStringRow(board);
		SimulatedAnnealingFourSudoku sudoku = new SimulatedAnnealingFourSudoku(board);
		SimulatedAnnealingFour annealing = new SimulatedAnnealingFour();
		long ini = System.currentTimeMillis();
		SimulatedAnnealingFourSudoku best = annealing.simulate(sudoku);
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

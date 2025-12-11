package sodoku.test;

import org.junit.jupiter.api.Test;

import sudoku.BoardPrinter;
import sudoku.two.SimulatedAnnealingTwo;
import sudoku.two.SimulatedAnnealingTwoSudoku;
import sudoku.two.TwoSudokuRecursive;

public class TwoSudokuTest {

	@Test
	void backtracking() throws Exception {
		int[][] board = new int[][] { { 0, 1 }, { 0, 0 } };
		StringBuilder response = new StringBuilder();
		String problem = BoardPrinter.asStringRow(board);
		TwoSudokuRecursive sudoku = new TwoSudokuRecursive(board);
		long ini = System.currentTimeMillis();
		sudoku.resolve(1, 0);
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
		int[][] board = new int[][] { { 0, 0 }, { 0, 0 } };
		StringBuilder response = new StringBuilder();
		String problem = BoardPrinter.asStringRow(board);
		SimulatedAnnealingTwoSudoku sudoku = new SimulatedAnnealingTwoSudoku(board);
		SimulatedAnnealingTwo annealing = new SimulatedAnnealingTwo();
		long ini = System.currentTimeMillis();
		SimulatedAnnealingTwoSudoku best = annealing.simulate(sudoku);
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

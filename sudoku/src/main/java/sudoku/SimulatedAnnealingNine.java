package sudoku;

import java.util.Random;

public class SimulatedAnnealingNine {
	private static final Random RANDOM = new Random();
	private float cooling = 0.0000009f;
	private double temperature = 1.5d;

	public SimulatedAnnealingSudokuNine simulate(SimulatedAnnealingSudokuNine currentSolution) {
		SimulatedAnnealingSudokuNine best = currentSolution.newClone();
		while (temperature > 1) {
			SimulatedAnnealingSudokuNine newSolution = currentSolution.newClone();
			newSolution.swap();
			int currentCost = currentSolution.costFunction();
			int newCost = newSolution.costFunction();
			if (acceptanceProbability(currentCost, newCost, temperature) > RANDOM.nextDouble()) {
				currentSolution = newSolution;
			}
			if (currentSolution.costFunction() < best.costFunction()) {
				best = currentSolution;
			}
			temperature *= 1 - cooling;
		}
		return best;
	}

	private static double acceptanceProbability(int currentDistance, int newDistance, double temperature) {
		// If the new solution is better, accept it
		if (newDistance < currentDistance) {
			return 1.0;
		}
		// If the new solution is worse, calculate an acceptance probability
		return Math.exp((currentDistance - newDistance) / temperature);
	}

}

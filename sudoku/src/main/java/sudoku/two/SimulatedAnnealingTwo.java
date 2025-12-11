package sudoku.two;

import java.util.Random;

public class SimulatedAnnealingTwo {
	private static final Random RANDOM = new Random();
	private float cooling = 0.0000998f;
	private double temperature = 10000000000000000.0d;

	public SimulatedAnnealingTwoSudoku simulate(SimulatedAnnealingTwoSudoku currentSolution) {
		SimulatedAnnealingTwoSudoku best = currentSolution.newClone();
		while (temperature > 1) {
			SimulatedAnnealingTwoSudoku newSolution = currentSolution.newClone();
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

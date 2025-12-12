package sudoku.dynamic;

import java.util.Random;

public class SimulatedAnnealing {
	private static final Random RANDOM = new Random();
	private float cooling = 0.000009f;
	private double temperature = 30000d;

	public SimulatedAnnealingSudoku simulate(SimulatedAnnealingSudoku currentSolution) {
		SimulatedAnnealingSudoku best = currentSolution.newClone();
		while (temperature > 1) {
			SimulatedAnnealingSudoku newSolution = currentSolution.newClone();
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

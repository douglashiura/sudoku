package sudoku.four;

import java.util.Random;

public class SimulatedAnnealingFour {
	private static final Random RANDOM = new Random();
	private float cooling = 0.0000998f;
	private double temperature = 10000000000000000000000.0d;

	public SimulatedAnnealingFourSudoku simulate(SimulatedAnnealingFourSudoku currentSolution) {
		SimulatedAnnealingFourSudoku best = currentSolution.newClone();
		while (temperature > 1) {
			SimulatedAnnealingFourSudoku newSolution = currentSolution.newClone();
			newSolution.swap(RANDOM.nextInt(4), RANDOM.nextInt(4));
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

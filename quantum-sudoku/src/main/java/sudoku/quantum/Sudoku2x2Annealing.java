package sudoku.quantum;

import org.redfx.strange.Program;
import org.redfx.strange.Qubit;
import org.redfx.strange.Result;
import org.redfx.strange.Step;
import org.redfx.strange.gate.Cnot;
import org.redfx.strange.gate.Hadamard;
import org.redfx.strange.gate.RotationX;
import org.redfx.strange.gate.RotationZ;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;

public class Sudoku2x2Annealing {
	public static void main(String[] args) {
		// 4 qubits representam as 4 posições:
		// [0] [1] -> Linha 0
		// [2] [3] -> Linha 1
		int nQubits = 4;
		Program program = new Program(nQubits);

		// Parâmetros do "Annealing" (podem ser ajustados)
		double gamma = 0.7; // Peso das restrições (Custo)
		double beta = 0.3; // Peso da superposição (Mistura)

		// PASSO 1: Preparação (Superposição total)
		// Como o board é 0000, todos os qubits começam em Hadamard
		Step prep = new Step();
		for (int i = 0; i < nQubits; i++) {
			prep.addGate(new Hadamard(i));
		}
		program.addStep(prep);

		// PASSO 2: Camada de Custo (Restrições de Sudoku)
		// Penalizamos estados onde qubits adjacentes são IGUAIS.
		// Pares que devem ser diferentes:
		int[][] constraints = { { 0, 1 }, // Linha 0
				{ 2, 3 }, // Linha 1
				{ 0, 2 }, // Coluna 0
				{ 1, 3 } // Coluna 1
		};

		for (int[] pair : constraints) {
			// Implementação da interação Z_i * Z_j (Ising)
			// Isso penaliza |00> e |11> (mesmo valor)
			program.addStep(new Step(new Cnot(pair[0], pair[1])));
			program.addStep(new Step(new RotationZ(gamma, pair[1])));
			program.addStep(new Step(new Cnot(pair[0], pair[1])));
		}

		// PASSO 3: Camada de Mistura (Quantum Tunneling)
		// Permite transitar entre configurações para achar o mínimo global
		Step mixer = new Step();
		for (int i = 0; i < nQubits; i++) {
			mixer.addGate(new RotationX(beta, i));
		}
		program.addStep(mixer);

		// EXECUÇÃO NO SIMULADOR
		SimpleQuantumExecutionEnvironment sqee = new SimpleQuantumExecutionEnvironment();
		Result result = sqee.runProgram(program);
		Qubit[] qubits = result.getQubits();

		// EXIBIÇÃO DOS RESULTADOS
		System.out.println("=== Resultado Sudoku 2x2 (Mapeamento 0->Val 1, 1->Val 2) ===");
		System.out.println("Probabilidades de conter o valor '2':");
		for (int i = 0; i < qubits.length; i++) {
			double prob1 = qubits[i].getProbability(); // Probabilidade do estado |1> (Valor 2)
			double prob0 = 1.0 - prob1; // Probabilidade do estado |0> (Valor 1)

			System.out.printf("Célula Q%d: Valor 1 (%.1f%%) | Valor 2 (%.1f%%)\n", i, prob0 * 100, prob1 * 100);
		}

		System.out.println("\nInterpretando o Board:");
		System.out.println("Se as probabilidades forem ~50%, o sistema encontrou as duas soluções válidas:");
		System.out.println("Solução A: [1 2] [2 1]  e  Solução B: [2 1] [1 2]");
	}
}
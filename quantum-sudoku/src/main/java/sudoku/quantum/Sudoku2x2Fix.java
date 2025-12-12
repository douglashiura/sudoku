package sudoku.quantum;

import org.redfx.strange.Program;
import org.redfx.strange.Qubit;
import org.redfx.strange.Result;
import org.redfx.strange.Step;
import org.redfx.strange.gate.Cnot;
import org.redfx.strange.gate.Hadamard;
import org.redfx.strange.gate.RotationX;
import org.redfx.strange.gate.RotationZ;
import org.redfx.strange.gate.X;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;

public class Sudoku2x2Fix {
	public static void main(String[] args) {
		int nQubits = 4;
		Program program = new Program(nQubits);

		// Ajuste de parâmetros para maior visibilidade no simulador
		double gamma = 1.2; // Aumentado para forçar a penalidade
		double beta = 0.8; // Aumentado para permitir maior transição de estados

		// --- PASSO 1: Preparação ---
		Step prep = new Step();
		prep.addGate(new X(0)); // Fixa Q0 = Valor 2
		prep.addGate(new Hadamard(1));
		prep.addGate(new Hadamard(2));
		prep.addGate(new Hadamard(3));
		program.addStep(prep);

		// --- CAMADAS DE ANNEALING ---
		int camadas = 50; // Aumentar o número de camadas ajuda na convergência

		for (int p = 0; p < camadas; p++) {
			// 2. CUSTO: Restrições de Sudoku
			// Aplicamos a interação em todas as restrições
			int[][] constraints = { { 0, 1 }, { 2, 3 }, { 0, 2 }, { 1, 3 } };
			for (int[] pair : constraints) {
				Step s1 = new Step(new Cnot(pair[0], pair[1]));
				program.addStep(s1);

				Step s2 = new Step(new RotationZ(gamma, pair[1]));
				program.addStep(s2);

				Step s3 = new Step(new Cnot(pair[0], pair[1]));
				program.addStep(s3);
			}

			// 3. MISTURA (Mixer)
			Step mixer = new Step();
			for (int i = 1; i < nQubits; i++) {
				mixer.addGate(new RotationX(beta, i));
			}
			program.addStep(mixer);

			// Decaimento dos parâmetros (Simula o resfriamento)
			gamma += 0.1;
			beta *= 0.9;
		}

		// Execução
		SimpleQuantumExecutionEnvironment sqee = new SimpleQuantumExecutionEnvironment();
		Result result = sqee.runProgram(program);
		Qubit[] qubits = result.getQubits();

		System.out.println("=== Sudoku 2x2: Fixado (0,0)=2 ===");
		for (int i = 0; i < qubits.length; i++) {
			double probValor2 = qubits[i].getProbability();
			// Arredondamento para facilitar a leitura
			System.out.printf("Célula Q%d: Valor 1 (%.1f%%) | Valor 2 (%.1f%%)\n", i, (1.0 - probValor2) * 100,
					probValor2 * 100);
		}
		// Execute o programa várias vezes para ver as soluções reais colapsarem
		System.out.println("\n=== Amostragem de Soluções (10 tentativas) ===");
		for (int shot = 0; shot < 10; shot++) {
		    Result shotResult = sqee.runProgram(program);
		    Qubit[] shotQubits = shotResult.getQubits();
		    
		    System.out.print("Tentativa " + shot + ": [");
		    for (int i = 0; i < nQubits; i++) {
		        // measure() retorna 0 ou 1, colapsando o estado
		        int valor = shotQubits[i].measure() + 1; // +1 para converter 0,1 em 1,2
		        System.out.print(valor + (i < nQubits - 1 ? " " : ""));
		    }
		    System.out.println("]");
		}

		
	}
}

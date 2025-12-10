package sudoku.quantum;

import java.util.ArrayList;
import java.util.List;

import org.redfx.strange.Gate;
import org.redfx.strange.Program;
import org.redfx.strange.QuantumExecutionEnvironment;
import org.redfx.strange.Qubit;
import org.redfx.strange.Result;
import org.redfx.strange.Step;
import org.redfx.strange.gate.Identity;
import org.redfx.strange.gate.X;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;

public class Main {

	private static String BOARD = """
			400600123
			000002070
			001030906
			700928000
			916000382
			000361004
			508010600
			090800000
			124006007""";

	public static void main(String[] args) {
		String inLine = BOARD.replaceAll("\\D", "");
		StringBuilder inLineBits = new StringBuilder();
		for (int i = 0; i < inLine.length(); i++) {
			Integer tile = Integer.valueOf(inLine.substring(i, i + 1));
			inLineBits.append(convertToFourBits(tile));
		}
		QuantumExecutionEnvironment simulator = new SimpleQuantumExecutionEnvironment();
		Program program = new Program(inLine.length());
		List<Gate> gates = toGates(inLineBits);
		Step input = new Step();
		for (Gate gate : gates) {
			input.addGate(gate);
		}
		program.addStep(input);
		Result result = simulator.runProgram(program);
		Qubit[] qubits = result.getQubits();
		String binary = toBinary(qubits);
		List<Integer> board = toDecimal(binary);
		System.err.println(board);
		// QuantumExecutionEnvironment simulator = new
		// SimpleQuantumExecutionEnvironment();
//		Program program = new Program(2);
//		Step step1 = new Step();
//		step1.addGate(new Hadamard(0));
//		step1.addGate(new Hadamard(1));
//		program.addStep(step1);
//		for (int i = 0; i < COUNT; i++) {
//			Result result = simulator.runProgram(program);
//			Qubit[] qubits = result.getQubits();
//			Qubit zero = qubits[0];
//			Qubit one = qubits[1];
//			boolean coinA = zero.measure() == 1;
//			boolean coinB = one.measure() == 1;
//			if (!coinA && !coinB)
//				results[0]++;
//			if (!coinA && coinB)
//				results[1]++;
//			if (coinA && !coinB)
//				results[2]++;
//			if (coinA && coinB)
//				results[3]++;
//		}
//		System.out.println("=======================================");
//		System.out.println("We did " + COUNT + " experiments.");
//		System.out.println("[AB]: 0 0 occurred " + results[0] + " times.");
//		System.out.println("[AB]: 0 1 occurred " + results[1] + " times.");
//		System.out.println("[AB]: 1 0 occurred " + results[2] + " times.");
//		System.out.println("[AB]: 1 1 occurred " + results[3] + " times.");
//		System.out.println("=======================================");
//		Renderer.renderProgram(program); 
//		Renderer.showProbabilities(program, 1000);
	}

	private static List<Integer> toDecimal(String binary) {
		List<Integer> board = new ArrayList<>(81);
		for (int i = 0; i < binary.length(); i = i + 4) {
			String tile = binary.substring(i, i+4);
			board.add(Integer.parseInt(tile, 2));
		}
		return board;
	}

	private static String toBinary(Qubit[] qubits) {
		StringBuffer binary = new StringBuffer();
		for (Qubit qubit : qubits) {
			binary.append(qubit.measure() == 1 ? "1" : "0");
		}
		return binary.toString();
	}

	private static List<Gate> toGates(StringBuilder inLineBits) {
		List<Gate> inputs = new ArrayList<>(inLineBits.length());
		for (int i = 0; i < inLineBits.length(); i++) {
			inputs.add(inLineBits.substring(i, i + 1) == "1" ? new X(i) : new Identity(i));
		}
		return inputs;

	}

	public static String convertToFourBits(int tile) {
		String binary = Integer.toBinaryString(tile);
		String format = String.format("%4s", binary);
		return format.replace(' ', '0');
	}
}

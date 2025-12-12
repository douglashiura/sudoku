package sudoku.quantum;

import java.util.ArrayList;
import java.util.List;

import org.redfx.strange.Gate;
import org.redfx.strange.Program;
import org.redfx.strange.QuantumExecutionEnvironment;
import org.redfx.strange.Qubit;
import org.redfx.strange.Result;
import org.redfx.strange.Step;
import org.redfx.strange.gate.Hadamard;
import org.redfx.strange.gate.Identity;
import org.redfx.strange.gate.RotationX;
import org.redfx.strange.gate.RotationZ;
import org.redfx.strange.gate.X;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;
import org.redfx.strangefx.render.Renderer;

public class MainTwoByTwo {

	private static String BOARD = """
			00
			00""";

	public static void main(String[] args) {
		String inLine = BOARD.replaceAll("\\D", "");
		StringBuilder inLineBits = new StringBuilder();
		for (int i = 0; i < inLine.length(); i++) {
			Integer tile = Integer.valueOf(inLine.substring(i, i + 1));
			inLineBits.append(convertToFourBits(tile));
		}
		QuantumExecutionEnvironment simulator = new SimpleQuantumExecutionEnvironment();
		Program program = new Program(inLineBits.length());
		List<Gate> gates = toGates(inLineBits);
		Step input = new Step();
		for (Gate gate : gates) {
			input.addGate(gate);
		}
		program.addStep(input);

		Step preparation = new Step();
		for (int i = 0; i < gates.size(); i++) {
			preparation.addGate(new Hadamard(i));
		}
		program.addStep(preparation);

		Step costStep = new Step();
		double gamma = 0.5;
		costStep.addGate(new RotationZ(gamma, 0)); // Aplica a fase ao qubit 0
		program.addStep(costStep);

		Step mixerStep = new Step();
		double beta = 0.2; // Parâmetro que seria otimizado classicamente
		for (int i = 0; i < gates.size(); i++) {
			mixerStep.addGate(new RotationX(beta, i));
		}
		program.addStep(mixerStep);

		Result result = simulator.runProgram(program);
		Qubit[] qubits = result.getQubits();
		String binary = toBinary(qubits);
		Renderer.renderProgram(program);
		Renderer.showProbabilities(program, 1000);
		List<Integer> board = toDecimal(binary);
		System.out.println(board);
	}

	private static List<Integer> toDecimal(String binary) {
		List<Integer> board = new ArrayList<>(4);
		for (int i = 0; i < binary.length(); i = i + 2) {
			String tile = binary.substring(i, i + 2);
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
		String format = String.format("%2s", binary);
		String twoBits = format.replace(' ', '0');
		return twoBits;
	}
}

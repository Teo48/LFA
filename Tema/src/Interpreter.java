import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {
	public static HashMap<Integer, Integer> leftParenthesisIndex = new HashMap<>();
	public static HashMap<Integer, Integer> rightParenthesisIndex = new HashMap<>();

	public static void main(String [] args) {
		final String fileName = args[0];
		Reader reader = Reader.getInstance(fileName);
		Encoder encoder = Encoder.getInstance();
		GlyphoStack glyphoStack = GlyphoStack.getInstance();
		final String code = reader.readLine();
		ArrayList<Character> instructionList = encoder.encode(code);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		InstructionExecutor instructionExecutor = InstructionExecutor.getInstance(br, glyphoStack);
		// Preprocess indices for parenthesis
//		for (var i : instructionList) {
//			System.out.print(i);
//		}
//		System.out.println();

		ArrayDeque<Integer> stack = new ArrayDeque<>();
		for (int i = 0 ; i < instructionList.size(); ++i) {
			if (instructionList.get(i) == '[') {
				stack.addLast(i);
			} else if (instructionList.get(i) == ']') {
				int leftIndex = stack.pollLast();
				leftParenthesisIndex.put(leftIndex, i);
				rightParenthesisIndex.put(i, leftIndex);
			}
		}

		//Collections.reverse(rightParenthesisIndex);

//		for (int i : leftParenthesisIndex) {
//			System.out.print(i + " ");
//		}
//		System.out.println();
//		for (int i : rightParenthesisIndex) {
//			System.out.print(i + " ");
//		}
//		System.out.println();
		for (int i = 0 ; i < instructionList.size();) {
			i = instructionExecutor.executeInstruction(instructionList.get(i), i);
		}

		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

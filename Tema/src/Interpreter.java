import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Teo
 * */

public class Interpreter {
	public static HashMap<Integer, Integer> leftParenthesisIndex = new HashMap<>();
	public static HashMap<Integer, Integer> rightParenthesisIndex = new HashMap<>();
	public static int base;

	public static void main(String [] args) {
		final String fileName = args[0];
		base = (args.length > 1) ? Integer.parseInt(args[1]) : 10;
		Reader reader = Reader.getInstance(fileName);
		Encoder encoder = Encoder.getInstance();
		GlyphoStack glyphoStack = GlyphoStack.getInstance();
		final String code = reader.readLine();
		/*
			Check if the length of the glypho code is a multiple of 4, otherwise throw an error.
		*/
		if ((code.length() & 3) != 0) {
			int errorLine = code.length() - (code.length() & 3) >> 2;
			System.err.println("Error:" + errorLine);
			System.exit(-1);
		}

		ArrayList<Character> instructionList = encoder.encode(code);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		InstructionExecutor instructionExecutor = InstructionExecutor.getInstance(br, glyphoStack);

		ArrayDeque<Integer> stack = new ArrayDeque<>();
		/*
			Preprocess indices for parenthesis
		*/
		for (int i = 0 ; i < instructionList.size(); ++i) {
			if (instructionList.get(i) == '[') {
				stack.addLast(i);
			} else if (instructionList.get(i) == ']') {
				try {
					int leftIndex = stack.pollLast();
					leftParenthesisIndex.put(leftIndex, i);
					rightParenthesisIndex.put(i, leftIndex);
				} catch (NullPointerException e) {
					System.err.println("Error:" + i);
					System.exit(-1);
				}
			}
		}
		/*
			Check if there's an open parenthesis without a matching closed one.
		*/
		if (!stack.isEmpty()) {
			System.err.println("Error:" + instructionList.size());
			System.exit(-1);
		}

		for (int i = 0 ; i < instructionList.size();) {
			i = instructionExecutor.executeInstruction(instructionList.get(i), i);
		}

		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}
}

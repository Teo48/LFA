import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * @author Teo
 * */

public class InstructionExecutor {
	public BufferedReader br;
	public GlyphoStack glyphoStack;
	private static InstructionExecutor instance;

	private InstructionExecutor(BufferedReader br, GlyphoStack glyphoStack) {
		this.br = br;
		this.glyphoStack = glyphoStack;
	}

	public static InstructionExecutor getInstance(BufferedReader br, GlyphoStack glyphoStack) {
		if (instance == null) {
			instance = new InstructionExecutor(br, glyphoStack);
		}
		return instance;
	}

	/**
	 * Execute the instruction at the given line.
	 * @param c The current instruction
	 * @param instructionLine Index of the given instruction
	 * @return Index of the next instruction
	 * */
	public int executeInstruction(final Character c, int instructionLine) {
		switch (c) {
			case 'n': break;
			case 'i': {
				try {
					try {
						BigInteger element = new BigInteger(br.readLine(), Interpreter.base);
						glyphoStack.glyStack.addLast(element);
					} catch (NumberFormatException e) {
						System.err.println("Exception:" + instructionLine);
						System.exit(-2);
					}
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}

			case '>': {
				if (glyphoStack.glyStack.isEmpty()) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}

				var element = glyphoStack.glyStack.pollLast();
				glyphoStack.glyStack.addFirst(element);
				break;
			}

			case '\\': {
				if (glyphoStack.glyStack.size() < 2) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}

				var firstElement = glyphoStack.glyStack.pollLast();
				var secondElement = glyphoStack.glyStack.pollLast();
				glyphoStack.glyStack.addLast(firstElement);
				glyphoStack.glyStack.addLast(secondElement);
				break;
			}

			case '1': {
				glyphoStack.glyStack.addLast(new BigInteger("1", Interpreter.base));
				break;
			}

			case '<': {
				if (glyphoStack.glyStack.isEmpty()) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}

				var element = glyphoStack.glyStack.pollFirst();
				glyphoStack.glyStack.addLast(element);
				break;
			}

			case 'd': {
				try {
					glyphoStack.glyStack.addLast(glyphoStack.glyStack.getLast());
				} catch (Exception e) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}
				break;
			}

			case '+': {
				if (glyphoStack.glyStack.size() < 2) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}
				var firstElement = glyphoStack.glyStack.pollLast();
				var secondElement = glyphoStack.glyStack.pollLast();
				glyphoStack.glyStack.addLast(firstElement.add(secondElement));
				break;
			}

			case '[': {
				if (glyphoStack.glyStack.size() < 1) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}

				if (glyphoStack.glyStack.getLast().equals(BigInteger.ZERO)) {
					instructionLine = Interpreter.leftParenthesisIndex.get(instructionLine) - 1;
				}
				break;
			}

			case 'o': {
				if (glyphoStack.glyStack.isEmpty()) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}

				System.out.println(glyphoStack.glyStack.pollLast().toString(Interpreter.base).toUpperCase());
				break;
			}

			case '*': {
				if (glyphoStack.glyStack.size() < 2) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}

				var firstElement = glyphoStack.glyStack.pollLast();
				var secondElement = glyphoStack.glyStack.pollLast();
				glyphoStack.glyStack.addLast(secondElement.multiply(firstElement));
				break;
			}

			case 'e': {
				try {
					if (glyphoStack.glyStack.size() < 4) {
						throw new Exception();
					}
					var firstElement = glyphoStack.glyStack.pollLast();
					var secondElement = glyphoStack.glyStack.pollLast();
					var thirdElement = glyphoStack.glyStack.pollLast();
					var fourthElement = glyphoStack.glyStack.pollLast();
					StringBuilder sb = new StringBuilder();
					HashMap<BigInteger, Integer> hashMap = new HashMap<>();
					int instructionCounter = 0;
					sb.append(instructionCounter);
					hashMap.put(firstElement, instructionCounter++);

					if (hashMap.containsKey(secondElement)) {
						sb.append(hashMap.get(secondElement));
					} else {
						sb.append(instructionCounter);
						hashMap.put(secondElement, instructionCounter++);
					}

					if (hashMap.containsKey(thirdElement)) {
						sb.append(hashMap.get(thirdElement));
					} else {
						sb.append(instructionCounter);
						hashMap.put(thirdElement, instructionCounter++);
					}

					if (hashMap.containsKey(fourthElement)) {
						sb.append(hashMap.get(fourthElement));
					} else {
						sb.append(instructionCounter);
					}

					executeInstruction(Encoder.instructionSet.get(sb.toString()), instructionLine);
				} catch (Exception e) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}
				break;
			}

			case '-': {
				if (glyphoStack.glyStack.isEmpty()) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}
				var element = glyphoStack.glyStack.pollLast();
				glyphoStack.glyStack.addLast(element.negate());
				break;
			}

			case '!': {
				if (glyphoStack.glyStack.isEmpty()) {
					System.err.println("Exception:" + instructionLine);
					System.exit(-2);
				}
				glyphoStack.glyStack.pollLast();
				break;
			}

			case ']' : {
				try {
					if (!glyphoStack.glyStack.getLast().equals(BigInteger.ZERO)) {
						instructionLine = Interpreter.rightParenthesisIndex.get(instructionLine) - 1;
					}
				} catch (NoSuchElementException e) {
					instructionLine = Interpreter.rightParenthesisIndex.get(instructionLine) - 1;
				}
				break;
			}
			default: break;
		}

		++instructionLine;
		return instructionLine;
	}
}

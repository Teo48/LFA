import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;

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

	public int executeInstruction(final Character c, int instructionLine) {
		switch (c) {
			case 'n': break;
			case 'i': {
				try {
					BigInteger element = new BigInteger(br.readLine());
					glyphoStack.glyStack.addLast(element);
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			case '>': {
				var element = glyphoStack.glyStack.pollLast();
				glyphoStack.glyStack.addFirst(element);
				break;
			}
			case '\\': {
				var firstElement = glyphoStack.glyStack.pollLast();
				var secondElement = glyphoStack.glyStack.pollLast();
				glyphoStack.glyStack.addLast(firstElement);
				glyphoStack.glyStack.addLast(secondElement);
				break;
			}
			case '1': {
				glyphoStack.glyStack.addLast(new BigInteger("1"));
				break;
			}
			case '<': {
				var element = glyphoStack.glyStack.pollFirst();
				glyphoStack.glyStack.addLast(element);
				break;
			}
			case 'd': {
				glyphoStack.glyStack.addLast(glyphoStack.glyStack.getLast());
				break;
			}
			case '+': {
				var firstElement = glyphoStack.glyStack.pollLast();
				var secondElement = glyphoStack.glyStack.pollLast();
				glyphoStack.glyStack.addLast(firstElement.add(secondElement));
				break;
			}
			// De fixat
			// Nu verific ca am 0 in varful stivei si nu creez bine vectorii cu pozitii
			case '[': {
				if (glyphoStack.glyStack.getLast().equals(BigInteger.ZERO)) {
					instructionLine = Interpreter.leftParenthesisIndex.get(instructionLine) - 1;
				}
				break;
			}
			case 'o': {
				System.out.println(glyphoStack.glyStack.pollLast());
				break;
			}
			case '*': {
				var firstElement = glyphoStack.glyStack.pollLast();
				var secondElement = glyphoStack.glyStack.pollLast();
				glyphoStack.glyStack.addLast(secondElement.multiply(firstElement));
				break;
			}
			case 'e': {
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
				break;
			}
			case '-': {
				var element = glyphoStack.glyStack.pollLast();
				glyphoStack.glyStack.addLast(element.negate());
				break;
			}
			case '!': {
				glyphoStack.glyStack.pollLast();
				break;
			}
			case ']' : {
				if (!glyphoStack.glyStack.getLast().equals(BigInteger.ZERO)) {
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

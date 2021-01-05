import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;

public class Interpreter {
	public static void main(String [] args) {
		final String fileName = args[0];
		Reader reader = Reader.getInstance(fileName);
		Encoder encoder = Encoder.getInstance();
		GlyphoStack glyphoStack = GlyphoStack.getInstance();
		final String code = reader.readLine();
		ArrayList<Character> instructionList = encoder.encode(code);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (var i : instructionList) {
			switch (i) {
				case 'n': break;
				case 'i': {
					try {
						BigInteger element = new BigInteger(br.readLine());
						glyphoStack.glyStack.addLast(element);
						break;
					} catch (IOException e) {
						e.printStackTrace();
					}
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
					glyphoStack.glyStack.addLast(secondElement.add(firstElement));
				}
				case '[': {
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
					break;
				}
				default: break;
			}
		}
	}
}

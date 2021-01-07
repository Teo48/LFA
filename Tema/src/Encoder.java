import java.util.ArrayList;
import java.util.HashMap;

public class Encoder {
	private static Encoder instance;
	public static final HashMap<String, Character> instructionSet;

	static {
		instructionSet = new HashMap<>();
		instructionSet.put("0000", 'n');
		instructionSet.put("0001", 'i');
		instructionSet.put("0010", '>');
		instructionSet.put("0011", '\\');
		instructionSet.put("0012", '1');
		instructionSet.put("0100", '<');
		instructionSet.put("0101", 'd');
		instructionSet.put("0102", '+');
		instructionSet.put("0110", '[');
		instructionSet.put("0111", 'o');
		instructionSet.put("0112", '*');
		instructionSet.put("0120", 'e');
		instructionSet.put("0121", '-');
		instructionSet.put("0122", '!');
		instructionSet.put("0123", ']');
	}

	private Encoder() {}

	public ArrayList<Character> encode(final String instructions) {
		ArrayList<Character> instructionList = new ArrayList<>();
		char [] glyphoCharacters = instructions.toCharArray();
		int size = glyphoCharacters.length;

		for (int i = 0 ; i < size ; i += 4) {
			StringBuilder sb = new StringBuilder();
			HashMap<Character, Integer> hashMap = new HashMap<>();
			int instructionCounter = 0;
			sb.append(instructionCounter);
			hashMap.put(glyphoCharacters[i], instructionCounter++);

			if (hashMap.containsKey(glyphoCharacters[i + 1])) {
				sb.append(hashMap.get(glyphoCharacters[i + 1]));
			} else {
				sb.append(instructionCounter);
				hashMap.put(glyphoCharacters[i + 1], instructionCounter++);
			}

			if (hashMap.containsKey(glyphoCharacters[i + 2])) {
				sb.append(hashMap.get(glyphoCharacters[i + 2]));
			} else {
				sb.append(instructionCounter);
				hashMap.put(glyphoCharacters[i + 2], instructionCounter++);
			}

			if (hashMap.containsKey(glyphoCharacters[i + 3])) {
				sb.append(hashMap.get(glyphoCharacters[i + 3]));
			} else {
				sb.append(instructionCounter);
			}

			instructionList.add(instructionSet.get(sb.toString()));
		}
		return instructionList;
	}

	public static Encoder getInstance() {
		if (instance == null) {
			instance = new Encoder();
		}
		return instance;
	}
}

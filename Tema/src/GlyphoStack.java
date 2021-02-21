import java.math.BigInteger;
import java.util.ArrayDeque;

/**
 * @author Teo
 * */

public class GlyphoStack {
	public ArrayDeque<BigInteger> glyStack;
	private static GlyphoStack instance;

	private GlyphoStack() {
		glyStack = new ArrayDeque<>();
	}

	public static GlyphoStack getInstance() {
		if (instance == null) {
			instance = new GlyphoStack();
		}

		return instance;
	}
}

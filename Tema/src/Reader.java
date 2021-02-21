import java.io.*;

/**
 * @author Teo
 * */

public final class Reader {
	private final int bufferSize = 1 << 17;
	private DataInputStream dataInputStream;
	private byte[] buffer;
	private int bufferPointer, bytesRead;
	private static Reader instance;

	/**
	 * Constructor that initializes the stream for the parsing process.
	 *
	 * @param inputPath Path for the input stream.
	 */
	private Reader(final String inputPath) {
		try {
			dataInputStream = new DataInputStream(new FileInputStream(inputPath));
			buffer = new byte[bufferSize];
			bufferPointer = 0;
			bytesRead = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method used for parsing a line.
	 *
	 * @return String which represents the line read from the file specified in constructor.
	 */
	public String readLine() {
		byte[] buf = new byte[1 << 21];
		int counter = 0;
		int chr;

		while ((chr = read()) != -1) {
			if (chr == '\n') {
				break;
			}
			buf[counter++] = (byte) chr;
		}

		return new String(buf, 0, counter);
	}

	/**
	 * Method that fills that buffer with data.
	 */
	private void fillBuffer() {
		bufferPointer = 0;
		try {
			bytesRead = dataInputStream.read(buffer, bufferPointer, bufferSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bytesRead == -1) {
			buffer[0] = (byte) -1;
		}
	}

	/**
	 * Method used for buffer traversal.
	 *
	 * @return the current character in the buffer.
	 */
	private byte read() {
		if (bufferPointer == bytesRead) {
			fillBuffer();
		}
		return buffer[bufferPointer++];
	}

	public static Reader getInstance(final String fileName) {
		if (instance == null) {
			instance = new Reader(fileName);
		}
		return instance;
	}
}

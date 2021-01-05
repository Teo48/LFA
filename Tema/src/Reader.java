import java.io.*;

public final class Reader {
	private final int shifter = 17;
	private final int zero = 0;
	private final int minus1 = -1;
	private final int bufferSize = 1 << shifter;
	private DataInputStream dataInputStream;
	private byte[] buffer;
	private int bufferPointer, bytesRead;
	private static Reader instance;

	/**
	 * Constructor that initialize the stream for the parsing process.
	 *
	 * @param inputPath which represents that path for the input stream
	 */
	private Reader(final String inputPath) {
		try {
			dataInputStream = new DataInputStream(new FileInputStream(inputPath));
			buffer = new byte[bufferSize];
			bufferPointer = zero;
			bytesRead = zero;
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
		int eight = 8;
		byte[] buf = new byte[1 << shifter];
		int counter = zero;
		int chr;

		while ((chr = read()) != minus1) {
			if (chr == '\n') {
				break;
			}
			buf[counter++] = (byte) chr;
		}


		return new String(buf, zero, counter);
	}

	/**
	 * Method that fills that buffer with data.
	 */
	private void fillBuffer() {
		bufferPointer = zero;
		try {
			bytesRead = dataInputStream.read(buffer, bufferPointer, bufferSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bytesRead == minus1) {
			buffer[0] = (byte) minus1;
		}
	}

	/**
	 * Method used for buffer traversal.
	 *
	 * @return byte which represents the current character in the buffer.
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

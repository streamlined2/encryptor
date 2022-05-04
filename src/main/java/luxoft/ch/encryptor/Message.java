package luxoft.ch.encryptor;

class Message {

	private static final char PAD_CHAR = '#';

	private final StringBuilder text;

	public Message(int boardSize, String... words) {
		text = new StringBuilder();
		for (String word : words) {
			text.append(word);
		}
		pad(boardSize);
		text.reverse();
	}

	private void pad(int boardSize) {
		for (int rest = getNewSize(boardSize) - text.length(); rest > 0; rest--) {
			text.append(PAD_CHAR);
		}
	}

	private int getNewSize(int boardSize) {
		if (text.length() % boardSize == 0) {
			return text.length();
		}
		return (text.length() / boardSize + 1) * boardSize;
	}

	public int getSize() {
		return text.length();
	}

	public char getChar(int index) {
		return text.charAt(index);
	}

}

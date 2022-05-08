package luxoft.ch.encryptor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Encryptor {

	private static final String DEMO_MESSAGE = """
			Hello, my darling! Glad to see you again! How is it going? Is it warm or cold outside? Is it raining or snowing? Let's have a cup of tea together at the cafe nearby.""";
	private static final int BOARD_SIDE = 6;
	private static final Grill SAMPLE_GRILL = new Grill(new Position(0, 1), new Position(0, 3), new Position(0, 5),
			new Position(1, 4), new Position(2, 2), new Position(3, 1), new Position(3, 4), new Position(4, 5),
			new Position(5, 3));

	private final Board board;
	private final Grill grill;

	public Encryptor(Board board, Grill grill) {
		this.board = board;
		this.grill = grill;
	}

	public String encrypt(String... text) {
		final Message message = new Message(board.getSize(), text);
		putDownMessage(message);
		return board.readMessage();
	}

	private void putDownMessage(Message message) {
		final int numberOfParts = getNumberOfParts(message);
		for (int partNo = 0; partNo < numberOfParts; partNo++) {
			putDownMessagePart(message, partNo);
		}
	}

	private int getNumberOfParts(Message message) {
		return message.getSize() / grill.size();
	}

	private void putDownMessagePart(Message message, int partNo) {
		int index = partNo * grill.size();
		for (Position position : grill) {
			board.putDownChar(message.getChar(index++), position, partNo);
		}
	}

	public String decrypt(String encryptedMessage) {
		board.writeMessage(encryptedMessage);
		return retrieveMessage().toString();
	}

	private Message retrieveMessage() {
		final Message message = new Message();
		final int numberOfParts = board.getSize() / grill.size();
		for (int partNo = 0; partNo < numberOfParts; partNo++) {
			message.append(retrieveMessagePart(partNo));
		}
		return message;
	}

	private Message retrieveMessagePart(int partNo) {
		final Message message = new Message();
		for (Position position : grill) {
			message.append(board.retrieveChar(position, partNo));
		}
		return message;
	}

	public static void main(String... args) {
		String text = DEMO_MESSAGE;
		if (args.length >= 1) {
			text = Stream.of(args).collect(Collectors.joining(" "));
		}
		Board board = new Board(BOARD_SIDE, text);
		Encryptor encryptor = new Encryptor(board, SAMPLE_GRILL);
		String encryptedMessage = encryptor.encrypt(text);
		System.out.printf("Original message: %s%nEncrypted message: %s%n", text, encryptedMessage);
		String decryptedMessage = encryptor.decrypt(encryptedMessage);
		System.out.printf("Decrypted message: %s%n", decryptedMessage);
		if (text.equals(decryptedMessage)) {
			System.out.println("Success! Original and decrypted messages coincide.");
		} else {
			System.out.println("Program failed, as original and decrypted messages are different.");
		}
	}

}

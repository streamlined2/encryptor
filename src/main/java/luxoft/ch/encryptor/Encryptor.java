package luxoft.ch.encryptor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Encryptor {

	private static final int BOARD_SIDE = 6;

	private final Board board;
	private final Grill grill;

	public Encryptor(Board board, Grill grill) {
		this.board = board;
		this.grill = grill;
	}

	public String encrypt(String... text) {
		Message message = new Message(getBoardSize(), text);
		putDownMessage(message);
		return board.readMessage();
	}

	private void putDownMessage(Message message) {
		int numberOfParts = getNumberOfParts(message);
		for (int partNo = 0; partNo < numberOfParts; partNo++) {
			putDownMessagePart(message, partNo);
		}
	}

	private int getNumberOfParts(Message message) {
		return message.getSize() / grill.size();
	}

	private void putDownMessagePart(Message message, int partNo) {
		int index = partNo * grill.size();
		TurnCount turnCount = TurnCount.getTurnCount(partNo);
		for (Position position : grill) {
			board.putDownChar(message.getChar(index++), position, turnCount);
		}
	}

	public String decrypt(String encryptedMessage) {
		board.writeMessage(encryptedMessage);
		return retrieveMessage().toString();
	}

	private Message retrieveMessage() {
		Message message = new Message();
		int numberOfParts = getBoardSize() / grill.size();
		for (int partNo = 0; partNo < numberOfParts; partNo++) {
			message.append(retrieveMessagePart(partNo));
		}
		return message;
	}

	private int getBoardSize() {
		return BOARD_SIDE * BOARD_SIDE;
	}

	private Message retrieveMessagePart(int partNo) {
		TurnCount turnCount = TurnCount.getTurnCount(partNo);
		Message message = new Message();
		for (Position position : grill) {
			message.append(board.retrieveChar(position, turnCount));
		}
		return message;
	}

	public static void main(String... args) {
		if (args.length < 1)
			throw new IllegalArgumentException("please pass at least one word as message");
		final String text = Stream.of(args).collect(Collectors.joining(" "));
		Grill grill = new Grill(new Position(0, 1), new Position(0, 3), new Position(0, 5), new Position(1, 4),
				new Position(2, 2), new Position(3, 1), new Position(3, 4), new Position(4, 5), new Position(5, 3));
		Board board = new Board(BOARD_SIDE);
		Encryptor encryptor = new Encryptor(board, grill);
		String encryptedMessage = encryptor.encrypt(text);
		System.out.printf("Source message: %s%nEncrypted message: %s%n", text, encryptedMessage);
		String decryptedMessage = encryptor.decrypt(encryptedMessage);
		System.out.printf("Decrypted message: %s%n", decryptedMessage);
	}

}

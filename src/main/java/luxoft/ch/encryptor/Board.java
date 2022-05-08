package luxoft.ch.encryptor;

import java.util.Objects;

class Board {

	private final int side;
	private final int depth;
	private final char[][][] cells;

	public Board(int side, String text) {
		Objects.checkIndex(side, Integer.MAX_VALUE);
		this.side = side;
		this.depth = getDepth(text);
		cells = new char[side][side][depth];
	}

	private int getDepth(String text) {
		final int quotient = text.length() / getSquare();
		if (text.length() % getSquare() == 0) {
			return quotient;
		}
		return quotient + 1;
	}

	public int getSize() {
		return getSquare() * depth;
	}

	private int getSquare() {
		return side * side;
	}

	public void putDownChar(char ch, Position position, int partNo) {
		final int layer = getLayer(partNo);
		switch (TurnCount.getTurnCount(partNo)) {
		case NO_TURNS:
			cells[position.y()][position.x()][layer] = ch;
			break;
		case ONE_TURN:
			cells[position.x()][side - position.y() - 1][layer] = ch;
			break;
		case TWO_TURNS:
			cells[side - position.y() - 1][side - position.x() - 1][layer] = ch;
			break;
		case THREE_TURNS:
			cells[side - position.x() - 1][position.y()][layer] = ch;
			break;
		}
	}

	private int getLayer(int partNo) {
		return partNo / TurnCount.getCount();
	}

	public char retrieveChar(Position position, int partNo) {
		final int layer = getLayer(partNo);
		return switch (TurnCount.getTurnCount(partNo)) {
		case NO_TURNS -> cells[position.y()][position.x()][layer];
		case ONE_TURN -> cells[position.x()][side - position.y() - 1][layer];
		case TWO_TURNS -> cells[side - position.y() - 1][side - position.x() - 1][layer];
		case THREE_TURNS -> cells[side - position.x() - 1][position.y()][layer];
		};
	}

	public String readMessage() {
		final StringBuilder b = new StringBuilder();
		for (int layer = 0; layer < depth; layer++) {
			for (int row = 0; row < side; row++) {
				for (int column = 0; column < side; column++) {
					b.append(cells[row][column][layer]);
				}
			}
		}
		return b.toString();
	}

	public void writeMessage(String message) {
		int layer = 0;
		int row = 0;
		int column = 0;
		for (int index = 0; index < message.length(); index++) {
			cells[row][column][layer] = message.charAt(index);
			column++;
			if (column >= side) {
				row++;
				column = 0;
			}
			if (row >= side) {
				layer++;
				row = column = 0;
			}
		}
	}

}

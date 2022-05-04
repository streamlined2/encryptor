package luxoft.ch.encryptor;

import java.util.Objects;

class Board {

	private final int side;
	private final char[][] cells;

	public Board(int side) {
		Objects.checkIndex(side, Integer.MAX_VALUE);
		this.side = side;
		cells = new char[side][side];
	}

	public void putDownChar(char ch, Position position, TurnCount turnCount) {
		switch (turnCount) {
		case NO_TURNS:
			cells[position.y()][position.x()] = ch;
			break;
		case ONE_TURN:
			cells[position.x()][side - position.y() - 1] = ch;
			break;
		case TWO_TURNS:
			cells[side - position.y() - 1][side - position.x() - 1] = ch;
			break;
		case THREE_TURNS:
			cells[side - position.x() - 1][position.y()] = ch;
			break;
		}
	}

	public String readMessage() {
		StringBuilder b = new StringBuilder();
		for (int row = 0; row < side; row++) {
			for (int column = 0; column < side; column++) {
				b.append(cells[row][column]);
			}
		}
		return b.toString();
	}

}

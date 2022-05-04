package luxoft.ch.encryptor;

enum TurnCount {
	NO_TURNS, ONE_TURN, TWO_TURNS, THREE_TURNS;

	public static TurnCount getTurnCount(int count) {
		return values()[count % values().length];
	}
}

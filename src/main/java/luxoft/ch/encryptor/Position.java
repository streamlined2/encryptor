package luxoft.ch.encryptor;

record Position(int y, int x) {

	@Override
	public String toString() {
		return new StringBuilder().append("{ y=").append(y).append(", x=").append(x).append(" }").toString();
	}

}

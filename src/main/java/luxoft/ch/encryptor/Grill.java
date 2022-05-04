package luxoft.ch.encryptor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class Grill implements Iterable<Position> {

	private final List<Position> positions;

	public Grill(Position... list) {
		positions = Arrays.asList(list);
	}

	public int size() {
		return positions.size();
	}

	@Override
	public Iterator<Position> iterator() {
		return positions.iterator();
	}

}

package lynn.voice.send;

import java.util.Set;

import lynn.models.Cell;
import lynn.models.Host;

public class CellCulture {
	
	private Set<Cell> cells;
	private Host host;
	
	public CellCulture() {}

	public Set<Cell> getCells() {
		return cells;
	}

	public void setCells(Set<Cell> cells) {
		this.cells = cells;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

}

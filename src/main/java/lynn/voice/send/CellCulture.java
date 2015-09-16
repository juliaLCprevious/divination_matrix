package lynn.voice.send;

import java.util.Map;

import lynn.models.Host;

public class CellCulture {
	
	/**
	 * d3network contains: {
	 * 
	 * positionMap: a map of cell name -> index within the nodes array
	 * d3data: a map of...
	 * ---- nodes: [cell1, cell2, cell3...]
	 * ---- rels: [{source:n, target:m}...]
	 * 
	 * example: {positionMap={Julia=1, Emily=2, Noah=0}, d3data={nodes=[Node[3], Node[2], Node[1]], rels=[{source=0, target=1}, {source=1, target=2}]}}
	 * 
	 */
	private Map<String, Object> d3network;
	private Host host;
	
	public Map<String, Object> getD3network() {
		return d3network;
	}
	
	public void setD3network(Map<String, Object> d3network) {
		this.d3network = d3network;
	}
	
	public Host getHost() {
		return host;
	}
	
	public void setHost(Host host) {
		this.host = host;
	}
	
}

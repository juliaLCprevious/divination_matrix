package divination.matrix.models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Reading {
	
	@GraphId private Long id;
	private String pseudonym;
	private int hexagramNumber;
	
	public Reading() {}
	public Reading(String pseudonym, int hexagramNumber) {
		this.pseudonym = pseudonym;
		this.hexagramNumber = hexagramNumber;
	}
	
	/**
	 * Node properties
	 */
	@Fetch
	@RelatedTo(type="READING", direction=Direction.INCOMING)
	private Hexagram hexagram;
	
	/**
	 * Boring ole' getters, setters
	 */

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPseudonym() {
		return pseudonym;
	}
	
	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}
	
	public int getHexagramNumber() {
		return hexagramNumber;
	}
	
	public void setHexagramNumber(int hexagramNumber) {
		this.hexagramNumber = hexagramNumber;
	}
	
	public Hexagram getHexagram() {
		return hexagram;
	}
	
	public void setHexagram(Hexagram hexagram) {
		this.hexagram = hexagram;
		this.hexagramNumber = hexagram.getNumber();
	}
	
	public String toString() {
		return "Reading [pseudonym: " + pseudonym
				+ ", hexagram: " + hexagram + "]";
	}
}

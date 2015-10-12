package divination.matrix.models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Reading {
	
	@GraphId private Long id;
	private String pseudonym;
	
	
	public Reading() {}
	public Reading(String pseudonym) {
		this.pseudonym = pseudonym;
	}
	
	/**
	 * Node properties
	 */
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
	
	public Hexagram getHexagram() {
		return hexagram;
	}
	
	public void setHexagram(Hexagram hexagram) {
		this.hexagram = hexagram;
	}
	
	public String toString() {
		int hexagramNumber = hexagram != null ? hexagram.getNumber() : null;
		return "Reading [pseudonym: " + pseudonym
				+ ", hexagram: " + hexagramNumber + "]";
	}
}

package lynn.models;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@NodeEntity
public class Cell {
	
	@GraphId private Long id;
	private String name;
	private String about;
	
	public Cell() {}
	public Cell(String name, String about) {
		this.name = name;
		this.about = about;
	}
	
	/**
	 * Node relationships
	 */
	@JsonIgnore // We don't want to serialize all the cytoplasms, it'd be stack overflow hell I tells ya!
	@RelatedTo(type="CYTOPLASM", direction=Direction.BOTH)
	private @Fetch Set<Cell> cytoplasm;
	
	public void addToCytoplasm(Cell cell) {
		if (cytoplasm == null) {
			cytoplasm = new HashSet<Cell>();
		}
		cytoplasm.add(cell);
	}
	
	@RelatedTo(type="CREATED", direction=Direction.INCOMING)
	private Host host;
	
	/**
	 * Boring ole' getters n' setters
	 */
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAbout() {
		return about;
	}
	
	public void setAbout(String about) {
		this.about = about;
	}
	
	@JsonIgnore
	public Set<Cell> getCytoplasm() {
		return cytoplasm;
	}
	
	@JsonProperty
	public void setCytoplasm(Set<Cell> cytoplasm) {
		this.cytoplasm = cytoplasm;
	}
	
	@JsonValue
	public List<String> getCytoplasmNames() {
		List<String> names = new LinkedList<String>();
		for (Cell cell: cytoplasm) {
			names.add(cell.getName());
		}
		return names;
	}
	
	public Host getHost() {
		return host;
	}
	
	public void setHost(Host host) {
		this.host = host;
	}
	
	public String toString() {
		String cellString = "Cell [name:" + name;
		if (id != null) {
			cellString += ", id: " + id;
		}
		if (cytoplasm != null) {
			cellString += ", cytoplasm:(~";
			for (Cell cell: cytoplasm) {
				cellString += cell.getName() + "~";
			}
			cellString += ")";
		}
		cellString += "]";
		return cellString;
	}

}

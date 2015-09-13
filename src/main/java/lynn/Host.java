package lynn;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Host {
	
	@GraphId private Long id;
	private String name;
	
	public Host() {}
	public Host(String name) {
		this.name = name;
	}
	
	/**
	 * Node relationships
	 */
	
	@RelatedTo(type="CREATED", direction = Direction.OUTGOING)
	private @Fetch Set<Cell> createdCells;
	
	public void addCreatedCell(Cell cell) {
		if (createdCells == null) {
			createdCells = new HashSet<Cell>();
		}
		createdCells.add(cell);
	}
	
	/**
	 * Boring ole' getters n' setters
	 */
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<Cell> getCreatedCells() {
		return createdCells;
	}

	public void setCreatedCells(Set<Cell> createdCells) {
		this.createdCells = createdCells;
	}
	
	public String toString() {
		String hostString = "Host [name:" + name + ", id:" + id;
		if (createdCells != null) {
			hostString += ", createdCells:(~";
			for (Cell cell: createdCells) {
				hostString += cell.getName() + "~";
			}
			hostString += ")";
		}
		hostString += "]";
		return hostString;
	}

}

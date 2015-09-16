package lynn.models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="CYTOPLASM")
public class Cytoplasm {
	
	@GraphId private Long id;
	@StartNode @Fetch private Cell cellA;
	@EndNode @Fetch private Cell cellB;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Cell getCellA() {
		return cellA;
	}
	
	public void setCellA(Cell cellA) {
		this.cellA = cellA;
	}
	
	public Cell getCellB() {
		return cellB;
	}
	
	public void setCellB(Cell cellB) {
		this.cellB = cellB;
	}
	
	public String toString() {
		return "Cytoplasm (~"+cellA.getName()+"~"+cellB.getName()+"~)";
	}
}

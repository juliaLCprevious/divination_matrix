package lynn;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

public interface CellRepository extends CrudRepository<Cell, Long> {
	
	Cell findByName(String name);
	
	@Query("MATCH (cell:Cell { name:{0} })-[:CYTOPLASM]-(cytoplasmCell) RETURN cytoplasmCell")
	Iterable<Cell> findCytoplasmByCellName(String name);
	
	@Query("MATCH (host:Host { name:{0} })-[:CREATED]->(cells) RETURN cells")
	Iterable<Cell> findCellsByHostName(String name);

}

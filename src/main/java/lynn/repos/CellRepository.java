package lynn.repos;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import lynn.models.Cell;

public interface CellRepository extends CrudRepository<Cell, Long> {
	
	Cell findByName(String name);
	
	@Query("MATCH (cell:Cell { name:{0} })-[:CYTOPLASM]-(cytoplasmCell) RETURN cytoplasmCell")
	Iterable<Cell> findCytoplasmByCellName(String name);
	
	@Query("MATCH (host:Host { name:{0} })-[:CREATED]->(cells) RETURN cells")
	Iterable<Cell> findCellsByHostName(String name);
	
	@Query("MATCH (n:Cell) return count(n)")
	int getTotalCellCount();
	
	@Query("MATCH (n:Cell)-[:CYTOPLASM]-(m:Cell) "
			+ "WITH DISTINCT n, count(m) AS cytoCount "
			+ "WHERE cytoCount < {0} AND NOT n.name = \"{1}\""
			+ "WITH n, cytoCount, rand() AS number "
			+ "RETURN n "
			+ "ORDER BY number "
			+ "LIMIT {0}")
	Iterable<Cell> chooseRandomCellsForCytoplasm(int count, String starterCellName);

}

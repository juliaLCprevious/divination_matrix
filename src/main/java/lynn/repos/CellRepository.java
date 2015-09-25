package lynn.repos;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import lynn.models.Cell;

public interface CellRepository extends GraphRepository<Cell> {
	
	Cell findByName(String name);
	
	@Query("MATCH (cell:Cell { name:{0} })-[:CYTOPLASM]-(cytoplasmCell) RETURN cytoplasmCell")
	Iterable<Cell> findCytoplasmByCellName(String name);
	
	@Query("MATCH (host:Host { name:{0} })-[:CREATED]->(cells) RETURN cells")
	Iterable<Cell> findCellsByHostName(String name);
	
	@Query("MATCH (n:Cell) return count(n)")
	int getTotalCellCount();
	
	@Query("MATCH (n:Cell)-[:CYTOPLASM]-(m:Cell) "
			+ "WITH DISTINCT n, count(m) AS cytoCount "
			+ "WHERE cytoCount < {0} AND NOT n.name = \"{2}\""
			+ "WITH n, cytoCount, rand() AS number "
			+ "RETURN n "
			+ "ORDER BY number "
			+ "LIMIT {1}")
	Iterable<Cell> chooseRandomCellsForCytoplasm(int existingThreshold, int maxCells, String starterCellName);
	
	@Query("MATCH (c:Cell)-[:CYTOPLASM]-(d:Cell) RETURN DISTINCT c.name as name, "
			+ "c.about as about, "
			+ "collect(d.name) as cytoplasm")
    List<Map<String,Object>> graph();

}

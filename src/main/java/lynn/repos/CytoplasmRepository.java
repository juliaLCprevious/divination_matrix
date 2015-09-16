package lynn.repos;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import lynn.models.Cytoplasm;

public interface CytoplasmRepository extends CrudRepository<Cytoplasm, Long> {
	
	@Query("MATCH (:Cell)-[r:CYTOPLASM]-(:Cell) RETURN DISTINCT r")
	Iterable<Cytoplasm> getAllCytoplasmPairs();

}

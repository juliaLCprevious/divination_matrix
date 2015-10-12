package divination.matrix.repos;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import divination.matrix.models.Reading;

public interface ReadingRepository extends GraphRepository<Reading> {
	
	Reading findByPseudonym(String pseudonym);
	
	@Query("MATCH (n:Reading) return n")
	List<Reading> findAllReadings();

}

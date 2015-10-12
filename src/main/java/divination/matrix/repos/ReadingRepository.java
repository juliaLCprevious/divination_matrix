package divination.matrix.repos;

import org.springframework.data.neo4j.repository.GraphRepository;

import divination.matrix.models.Reading;

public interface ReadingRepository extends GraphRepository<Reading> {
	
	Reading findByPseudonym(String pseudonym);

}

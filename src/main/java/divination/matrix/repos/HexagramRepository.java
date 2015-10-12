package divination.matrix.repos;

import org.springframework.data.neo4j.repository.GraphRepository;

import divination.matrix.models.Hexagram;

public interface HexagramRepository extends GraphRepository<Hexagram> {
	
	Hexagram findByName(String name);
	
	Hexagram findByNumber(int number);

}

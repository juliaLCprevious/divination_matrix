package divination.matrix.repos;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import divination.matrix.models.Hexagram;

public interface HexagramRepository extends GraphRepository<Hexagram> {
	
	Hexagram findByNumber(int number);
	
	@Query("MATCH (n:Hexagram) return n")
	List<Hexagram> findAllHexagrams();

}

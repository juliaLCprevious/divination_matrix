package lynn.repos;

import org.springframework.data.neo4j.repository.GraphRepository;

import lynn.models.Host;

public interface HostRepository extends GraphRepository<Host> {
	
	Host findByName(String name);

}

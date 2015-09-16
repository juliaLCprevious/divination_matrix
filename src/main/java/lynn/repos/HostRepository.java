package lynn.repos;

import org.springframework.data.repository.CrudRepository;

import lynn.models.Host;

public interface HostRepository extends CrudRepository<Host, Long> {
	
	Host findByName(String name);

}

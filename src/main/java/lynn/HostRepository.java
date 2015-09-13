package lynn;

import org.springframework.data.repository.CrudRepository;

public interface HostRepository extends CrudRepository<Host, Long> {
	
	Host findByName(String name);

}

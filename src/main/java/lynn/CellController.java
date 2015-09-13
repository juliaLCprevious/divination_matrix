package lynn;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class CellController {
	
	@Autowired CellRepository cellRepository;
	@Autowired HostRepository hostRepository;
	@Autowired GraphDatabase graphDatabase;
	
	@Transactional
	@MessageMapping("/welcome")
    @SendTo("/topic/cellCulture")
    public CellCulture welcome(WelcomeMessage message) throws Exception {
		System.out.println("Welcome to: " + message.getId());
        CellCulture response = new CellCulture();
           
        // Load all the cells for our lovely biologists <3
		Set<Cell> allCells = new HashSet<Cell>();
		for (Cell cell : cellRepository.findAll()) {
			allCells.add(cell);
		}
		
		if (allCells != null) {
			response.setCells(allCells);
		}
		
		// If the user isn't a host, send 'em to LynnAI!
		Host host = hostRepository.findOne(message.getId());
		if (host == null) {
			hostRepository.findByName("LynnAI");
		}
		response.setHost(host); 
    		
        return response;
    }
}

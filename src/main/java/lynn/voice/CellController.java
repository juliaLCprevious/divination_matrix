package lynn.voice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import lynn.models.Cell;
import lynn.models.Host;
import lynn.repos.HostRepository;
import lynn.services.CellService;
import lynn.voice.recieve.CellCreationSignal;
import lynn.voice.recieve.WelcomeMessage;
import lynn.voice.send.CellCulture;
import lynn.voice.send.NewCell;

@Controller
public class CellController {
	
	@Autowired CellService cellService;
	@Autowired HostRepository hostRepository;
	
	@Transactional
	@MessageMapping("/welcome")
    @SendTo("/topic/cellCulture")
    public CellCulture welcome(WelcomeMessage message) throws Exception {
		System.out.println("Welcome to: " + message.getId());
        CellCulture response = new CellCulture();
           
        // Load all the cells and cytoplasms for our lovely biologists <3
		response.setD3network(cellService.graph());
		
		// If the user isn't a host, send 'em to LynnAI!
		Host host = hostRepository.findOne(message.getId());
		if (host == null) {
			hostRepository.findByName("LynnAI");
		}
		response.setHost(host); 
    		
        return response;
    }
	
	@Transactional
	@MessageMapping("/createCell")
    @SendTo("/topic/newCell")
	public NewCell createCell(CellCreationSignal signal) throws Exception {
		System.out.println("Recieved cell creation signal: " + signal);			
		Cell cell = cellService.createCell(signal);	
		if (cell == null) {
			NewCell newCell = new NewCell();
			newCell.setSuccess(false);
			newCell.setError("NAME_TAKEN");
			return newCell;
		}
		NewCell newCell = new NewCell();
		newCell.setSuccess(true);
		newCell.setName(cell.getName());
		newCell.setAbout(cell.getAbout());
		newCell.setCytoplasm(cell.getCytoplasmNames());
		return newCell;
	
	}
}

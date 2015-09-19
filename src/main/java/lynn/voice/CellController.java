package lynn.voice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lynn.models.Cell;
import lynn.models.Host;
import lynn.repos.HostRepository;
import lynn.services.CellService;
import lynn.voice.recieve.CellCreationSignal;
import lynn.voice.send.CellCulture;
import lynn.voice.send.NewCell;

@Controller
public class CellController {
	
	@Autowired CellService cellService;
	@Autowired HostRepository hostRepository;
	
	@RequestMapping("/cellCulture")
	public @ResponseBody CellCulture getcellCulture() throws Exception {
        CellCulture response = new CellCulture();
           
        // Load all the cells and cytoplasms for our lovely biologists <3
		response.setD3network(cellService.graph());
		
		// Someday, accept a host param. Till then, send 'em to LynnAI!
		Host host = hostRepository.findByName("LynnAI");
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

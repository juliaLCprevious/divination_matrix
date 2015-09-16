package lynn.voice;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.graphdb.Transaction;
import org.parboiled.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import lynn.models.Cell;
import lynn.models.Host;
import lynn.repos.CellRepository;
import lynn.repos.HostRepository;
import lynn.services.CellService;
import lynn.voice.recieve.CellCreationSignal;
import lynn.voice.recieve.WelcomeMessage;
import lynn.voice.send.CellCulture;
import lynn.voice.send.NewCell;

@Controller
public class CellController {
	
	@Autowired CellService cellService;
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
		for (Cell cell : cellService.findAll()) {
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
	
	@Transactional
	@MessageMapping("/createCell")
    @SendTo("/topic/newCell")
	public NewCell createCell(CellCreationSignal signal) throws Exception {
		System.out.println("Recieved cell creation signal: " + signal);			
		Cell cell = new Cell(signal.getName(), signal.getAbout());
		
		Transaction tx = graphDatabase.beginTx();
		try {
			Cell existingCell = cellRepository.findByName(cell.getName());
			if (existingCell != null) {
				NewCell newCell = new NewCell();
				newCell.setSuccess(false);
				newCell.setError("NAME_TAKEN");
				return newCell;
			}
			cell = cellRepository.save(cell);
			System.out.println("New cell created: " + cell);
			
			if (StringUtils.isNotEmpty(signal.getHostName())) {
				Host host = hostRepository.findByName(signal.getHostName());
				if (host != null) {
					host.addCreatedCell(cell);
					hostRepository.save(host);
				}
				System.out.println("Host for new cell: " + host);
			}
					
			List<Cell> cytoplasmCells = cellService.chooseRandomCellsForCytoplasm(3, cell.getName());
			for (Cell cytoplasmCell : cytoplasmCells) {
				System.out.println("Adding cell to cytoplasm: " + cytoplasmCell);
				cell.addToCytoplasm(cytoplasmCell);
			}
			cell = cellRepository.save(cell);
			tx.success();
		} finally {
			tx.close();
		}
			
		
		NewCell newCell = new NewCell();
		newCell.setNewCell(cell);
		return newCell;
	
	}
}

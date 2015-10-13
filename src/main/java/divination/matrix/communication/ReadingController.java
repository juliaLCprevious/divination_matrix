package divination.matrix.communication;

import java.util.List;

import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import divination.matrix.models.Hexagram;
import divination.matrix.models.Reading;
import divination.matrix.repos.HexagramRepository;
import divination.matrix.repos.ReadingRepository;

@Controller
public class ReadingController {
	
	@Autowired HexagramRepository hexagramRepository;
	@Autowired ReadingRepository readingRepository;
	@Autowired GraphDatabase graphDatabase;
	
	private static Hexagram[] hexagrams;
	
	@Transactional
	@MessageMapping("/addReading")
    @SendTo("/topic/newReading")
	public Reading addReading(Reading reading) {
		System.out.println("Recieved new reading: " + reading);
		if (hexagrams == null) {
			hexagrams = new Hexagram[64];
			for (Hexagram hexagram : hexagramRepository.findAll()) {
				hexagrams[hexagram.getNumber() - 1] = hexagram;
			}
		}
		Transaction tx = graphDatabase.beginTx();
		try {
			reading = readingRepository.save(reading);
			reading.setHexagram(hexagrams[reading.getHexagramNumber() - 1]);
			reading = readingRepository.save(reading);
			tx.success();
			System.out.println("Reading successful!" + reading);
		} finally {
			tx.close();
		}
		return reading;
	}
	
	@RequestMapping("/allReadings")
	public @ResponseBody List<Reading> getAllReadings() throws Exception {
		return readingRepository.findAllReadings();
    }
	
	@RequestMapping("/allHexagrams")
	public @ResponseBody List<Hexagram> getAllHexagrams() throws Exception {
		return hexagramRepository.findAllHexagrams();
    }
}

package divination.matrix.communication;

import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class VisualizationController {
	
	@Transactional
	@MessageMapping("/addReading")
    @SendTo("/topic/newReading")
	public Map<String, Object> addReading( Map<String, Object> json) {
		// TO DO!
		return null;
	}
}

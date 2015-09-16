package lynn.services;

import java.util.List;
import java.util.Map;

import lynn.models.Cell;

public interface CellService {
	
	Cell findByName(String name);
	
	List<Cell> findAll();
	
	Iterable<Cell> findCytoplasmByCellName(String name);
	
	Iterable<Cell> findCellsByHostName(String name);
	
	List<Cell> chooseRandomCellsForCytoplasm(int max, String starterCellName);
	
	Map<String, Object> graph();


}

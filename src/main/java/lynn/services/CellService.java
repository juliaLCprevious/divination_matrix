package lynn.services;

import java.util.List;
import java.util.Map;

import lynn.models.Cell;
import lynn.voice.recieve.CellCreationSignal;

public interface CellService {
	
	Cell createCell(CellCreationSignal signal);
	
	Cell findByName(String name);
	
	List<Cell> findAll();
	
	Iterable<Cell> findCytoplasmByCellName(String name);
	
	Iterable<Cell> findCellsByHostName(String name);
	
	List<Cell> chooseRandomCellsForCytoplasm(int existingThreshold, int maxCells, String starterCellName);
	
	Map<String, Object> graph();

}

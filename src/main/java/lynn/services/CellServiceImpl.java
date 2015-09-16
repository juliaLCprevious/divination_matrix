package lynn.services;


import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lynn.models.Cell;
import lynn.repos.CellRepository;

@Service
public class CellServiceImpl implements CellService {
	
	@Autowired CellRepository cellRepository;

	@Override
	public Cell findByName(String name) {
		return cellRepository.findByName(name);
	}
	
	public List<Cell> findAll() {
		List<Cell> allCells = new LinkedList<Cell>();
		for (Cell cell : cellRepository.findAll()) {
			allCells.add(cell);
		}
		return allCells;
	}

	@Override
	public Iterable<Cell> findCytoplasmByCellName(String name) {
		return cellRepository.findCytoplasmByCellName(name);
	}

	@Override
	public Iterable<Cell> findCellsByHostName(String name) {
		return cellRepository.findCellsByHostName(name);
	}
	
	@Override
	public List<Cell> chooseRandomCellsForCytoplasm(int max, String starterCellName) {
		List<Cell> cells = new LinkedList<Cell>();
		for (Cell cytoplasmCell: cellRepository.chooseRandomCellsForCytoplasm(max, starterCellName)) {
			cells.add(cytoplasmCell);
		}
		if (cells.isEmpty()) {
			System.out.println("No cells found for cytoplasm.");
		}
		return cells;
	}
		
		

}

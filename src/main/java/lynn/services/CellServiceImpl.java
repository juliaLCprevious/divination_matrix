package lynn.services;


import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.neo4j.graphdb.Transaction;
import org.parboiled.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Service;

import lynn.models.Cell;
import lynn.models.Host;
import lynn.repos.CellRepository;
import lynn.repos.HostRepository;
import lynn.voice.recieve.CellCreationSignal;

@Service
public class CellServiceImpl implements CellService {
	
	@Autowired CellRepository cellRepository;
	@Autowired HostRepository hostRepository;
	@Autowired GraphDatabase graphDatabase;
	
	@Override
	public Cell createCell(CellCreationSignal signal) {
		Cell cell = new Cell(signal.getName(), signal.getAbout());
		
		Transaction tx = graphDatabase.beginTx();
		try {
			Cell existingCell = cellRepository.findByName(cell.getName());
			if (existingCell != null) {
				return null;
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
			
			RandomDataGenerator r = new RandomDataGenerator();
			int existingThreshold = (int) Math.round(r.nextGaussian(3, 1));
			int maxCells = (int) Math.round(r.nextGaussian(3, 1));
			existingThreshold = existingThreshold != 0 ? existingThreshold : 5;
			maxCells = maxCells != 0 ? maxCells : 3;
					
			List<Cell> cytoplasmCells = chooseRandomCellsForCytoplasm(existingThreshold, maxCells, cell.getName());
			for (Cell cytoplasmCell : cytoplasmCells) {
				System.out.println("Adding cell to cytoplasm: " + cytoplasmCell);
				cell.addToCytoplasm(cytoplasmCell);
			}
			cell = cellRepository.save(cell);
			tx.success();
		} finally {
			tx.close();
		}
		
		return cell;
	}

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
	public List<Cell> chooseRandomCellsForCytoplasm(int existingThreshold, int maxCells, String starterCellName) {
		List<Cell> cells = new LinkedList<Cell>();
		for (Cell cytoplasmCell: cellRepository.chooseRandomCellsForCytoplasm(existingThreshold, maxCells, starterCellName)) {
			cells.add(cytoplasmCell);
		}
		if (cells.isEmpty()) {
			System.out.println("No cells found for cytoplasm.");
		}
		return cells;
	}
		
	public Map<String, Object> graph() {
        List<Map<String, Object>> result = cellRepository.graph();
        return toD3Format(result);
    }
	
	
	/**
	 * Let's hope I never need you again, toD3Format()/past loves/etc.
	 * 
	 * 
	private Map<String, Object> toD3Format(Iterator<Map<String, Object>> result) {
        List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> rels= new ArrayList<Map<String,Object>>();
        int i=0;
        while (result.hasNext()) {
            Map<String, Object> row = result.next();
            nodes.add(map("name",row.get("nexusCell"),"label","cell"));
            System.out.println("Yay for #" + i + ": " + row);
            int target=i;
            i++;
            for (Object name : (Collection) row.get("cytoplasm")) {
                Map<String, Object> cell = map("name", name,"label","cell");
                int source = nodes.indexOf(cell);
                if (source == -1) {
                    nodes.add(cell);
                    source = i++;
                }
                rels.add(map("source",source,"target",target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }
    **/
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> toD3Format(List<Map<String, Object>> result) {
		System.out.println("Creating D3 graph. gird yr loins");
		
		Object[] nodes = new Object[result.size()];
        List<Map<String,Object>> rels= new LinkedList<Map<String,Object>>();
		Map<String, Integer> positions = new HashMap<String, Integer>();
		
		int nodesPosition = 0; // position in the node list
		for (Map<String, Object> row: result) { 		
			// Every row returns attributes + cytoplasm:[1,2,...,n]
			Map<String, Object> cell = new HashMap<String, Object>();
			String cellName = (String) row.get("name"); 
			String cellAbout= (String) row.get("about");
			cell.put("name", cellName);
			cell.put("about", cellAbout);
			
			// Add cell to nodes
			int cellPosition;
			if (positions.get(cellName) == null) {
				cellPosition = nodesPosition++;
				positions.put(cellName, cellPosition);			
			} else {
				cellPosition = positions.get(cellName);
			}
			System.out.println("Adding " + cellName + " to position " + cellPosition + ". Body: " + cell);
			nodes[cellPosition] = cell;
			cell.put("id", cellPosition);
			
			// Add cytoplasm relationships
			Collection<String> cytoplasm = (Collection<String>) row.get("cytoplasm");
			System.out.println("Cytoplasm for " + cellName + ": " + cytoplasm);
			for (String cytoCellName : cytoplasm) {
				int cytoCellPosition;
				
				// Only create a relationship if the cytoplasmCell has not yet been parsed.
				// This relationship would have already been added!
				if (positions.get(cytoCellName) == null) {		
					cytoCellPosition = nodesPosition++;
					positions.put(cytoCellName, cytoCellPosition);
				} else {
					cytoCellPosition = positions.get(cytoCellName);
				}
				rels.add(map("source", cellPosition, "target", cytoCellPosition));
				System.out.println("Adding relationship b/w " + cellName +
						" at position " + cellPosition + 
						" and " + cytoCellName +
						" at " + cytoCellPosition
				);
			}
		}
		return map("positionMap", positions, "d3data", map("nodes", nodes, "rels", rels));
	}
	
	private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String,Object>(2);
        result.put(key1,value1);
        result.put(key2,value2);
        return result;
    }


}

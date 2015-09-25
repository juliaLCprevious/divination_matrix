package lynn.models;

import java.util.Calendar;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Comment {
	
	@GraphId private Long id;
	private String text;
	private long time;
	
	public Comment() {}
	public Comment(String text, Cell cell) {
		this.text = text;
		this.cell = cell;
		this.time = Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * Node relationships
	 */
	
	@RelatedTo(type="MESSAGES", direction=Direction.INCOMING)
	private Cell cell;
	
	/**
	 * Boring ole' getters n' setters
	 */

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public Cell getCell() {
		return cell;
	}
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	public String toString() {
		return "Comment [text:" + text + 
				", time:" + id +
				", cell:" + cell.getName() + 
				"]";
	}
	

}

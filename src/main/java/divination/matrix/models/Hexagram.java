package divination.matrix.models;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Hexagram {
	
	@GraphId private Long id;
	@Fetch private int number;
	@Fetch private String name;
	@Fetch private String chinese;
	@Fetch private String character;
	
	
	public Hexagram() {}
	public Hexagram(int number, String name, String chinese, String character) {
		this.number = number;
		this.name = name;
		this.chinese = chinese;
		this.character = character;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getChinese() {
		return chinese;
	}
	
	public void setChinese(String chinese) {
		this.chinese = chinese;
	}
	
	public String getCharacter() {
		return character;
	}
	
	public void setCharacter(String character) {
		this.character = character;
	}
	
	public String toString() {
		return "IChing [" + number
				+ ", character: " + character 
				+ ", chinese: " + chinese
				+ ", name: " + name + "]";
	}

}

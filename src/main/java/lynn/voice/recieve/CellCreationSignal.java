package lynn.voice.recieve;

public class CellCreationSignal {
	
	private String name;
	private String about;
	private String hostName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbout() {
		return about;
	}
	
	public void setAbout(String about) {
		this.about = about;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public String toString() {
		return "New Cell: [name: " + name + " , about: " + about + ", hostName: " + hostName + "]";
	}

}

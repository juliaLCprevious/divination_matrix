package lynn.voice.send;

import java.util.List;

public class NewCell {
	
	private boolean success;
	private String error;
	
	private String name;
	private String about;
	private List<String> cytoplasm;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

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

	public List<String> getCytoplasm() {
		return cytoplasm;
	}

	public void setCytoplasm(List<String> cytoplasm) {
		this.cytoplasm = cytoplasm;
	}

}

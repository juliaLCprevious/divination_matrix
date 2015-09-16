package lynn.voice.send;

import lynn.models.Cell;

public class NewCell {
	
	private boolean success;
	private String error;
	
	private Cell newCell;

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

	public Cell getNewCell() {
		return newCell;
	}

	public void setNewCell(Cell newCell) {
		this.newCell = newCell;
	}

}

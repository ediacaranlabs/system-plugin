package br.com.uoutec.community.ediacaran.system.actions;

import java.time.LocalDateTime;
import java.util.Objects;

public class ActionExecutorRequestEntry implements ActionExecutorRequest{

	private String id;
	
	private ActionExecutorRequest request;
	
	private String status;
	
	private LocalDateTime dateSchedule;
	
	private int attempts;
	
	public ActionExecutorRequestEntry(String id, ActionExecutorRequest request, String status,
			LocalDateTime dateSchedule, int attempts) {
		this.id = id;
		this.request = request;
		this.status = status;
		this.dateSchedule = dateSchedule;
		this.attempts = attempts;
	}

	public String getId() {
		return id;
	}
	
	public ActionExecutorRequest getRequest() {
		return request;
	}

	public LocalDateTime getDateSchedule() {
		return dateSchedule;
	}

	@Override
	public String getParameter(String name) {
		return request.getParameter(name);
	}

	public String getStatus() {
		return status;
	}

	public int getAttempts() {
		return attempts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionExecutorRequestEntry other = (ActionExecutorRequestEntry) obj;
		return Objects.equals(id, other.id);
	}
	
}

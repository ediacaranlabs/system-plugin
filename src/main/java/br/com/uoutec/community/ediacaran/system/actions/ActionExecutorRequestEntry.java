package br.com.uoutec.community.ediacaran.system.actions;

import java.time.LocalDateTime;
import java.util.Objects;

public class ActionExecutorRequestEntry implements ActionExecutorRequest{

	private String id;
	
	private ActionExecutorRequest request;
	
	private ActionExecutorRequestStatus status;
	
	private String nexAction;
	
	private LocalDateTime dateSchedule;
	
	private int attempts;
	
	public ActionExecutorRequestEntry(String id, ActionExecutorRequest request, ActionExecutorRequestStatus status, String nexAction,
			LocalDateTime dateSchedule, int attempts) {
		this.id = id;
		this.request = request;
		this.status = status;
		this.dateSchedule = dateSchedule;
		this.attempts = attempts;
		this.nexAction = nexAction;
	}

	@Override
	public String getParameter(String name) {
		return request.getParameter(name);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ActionExecutorRequest getRequest() {
		return request;
	}

	public void setRequest(ActionExecutorRequest request) {
		this.request = request;
	}

	public ActionExecutorRequestStatus getStatus() {
		return status;
	}

	public void setStatus(ActionExecutorRequestStatus status) {
		this.status = status;
	}

	public String getNexAction() {
		return nexAction;
	}

	public void setNexAction(String nexAction) {
		this.nexAction = nexAction;
	}

	public LocalDateTime getDateSchedule() {
		return dateSchedule;
	}

	public void setDateSchedule(LocalDateTime dateSchedule) {
		this.dateSchedule = dateSchedule;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
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

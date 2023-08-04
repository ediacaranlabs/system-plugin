package br.com.uoutec.community.ediacaran.system.event;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

public class SystemEvent implements Serializable {

	private static final long serialVersionUID = 8212092539416656113L;

	private String id;

	private SystemEventType type;

	private String group;
	
	private String subgroup;
	
	private Date date;
	
	private String message;

	@Generated("SparkTools")
	private SystemEvent(Builder builder) {
		this.id = builder.id;
		this.type = builder.type;
		this.group = builder.group;
		this.subgroup = builder.subgroup;
		this.date = builder.date;
		this.message = builder.message;
	}

	public SystemEvent() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SystemEventType getType() {
		return type;
	}

	public void setType(SystemEventType type) {
		this.type = type;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSubgroup() {
		return subgroup;
	}

	public void setSubgroup(String subgroup) {
		this.subgroup = subgroup;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private String id;
		private SystemEventType type;
		private String group;
		private String subgroup;
		private Date date;
		private String message;

		private Builder() {
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withType(SystemEventType type) {
			this.type = type;
			return this;
		}

		public Builder withGroup(String group) {
			this.group = group;
			return this;
		}

		public Builder withSubgroup(String subgroup) {
			this.subgroup = subgroup;
			return this;
		}

		public Builder withDate(Date date) {
			this.date = date;
			return this;
		}

		public Builder withMessage(String message) {
			this.message = message;
			return this;
		}

		public SystemEvent build() {
			return new SystemEvent(this);
		}
	}
	
}

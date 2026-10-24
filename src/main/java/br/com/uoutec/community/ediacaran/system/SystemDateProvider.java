package br.com.uoutec.community.ediacaran.system;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.inject.Singleton;

import br.com.uoutec.ediacaran.core.plugins.PublicBean;

@Singleton
public class SystemDateProvider implements PublicBean {

	private volatile Clock clock;
	
	public void setClock(Clock clock) {
		this.clock = clock;
	}
	
	public LocalDateTime getSystemDateTime() {
		return LocalDateTime.now(clock == null? Clock.systemDefaultZone() : clock);
	}

	public LocalDate getSystemDate() {
		return LocalDate.now(clock == null? Clock.systemDefaultZone() : clock);
	}
	
}

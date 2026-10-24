package br.com.uoutec.community.ediacaran.system;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

public class SystemDate {

	public static LocalDateTime getSystemDateTime() {
		SystemDateProvider sdp = EntityContextPlugin.getEntity(SystemDateProvider.class);
		return sdp.getSystemDateTime();
	}

	public static LocalDate getSystemDate() {
		SystemDateProvider sdp = EntityContextPlugin.getEntity(SystemDateProvider.class);
		return sdp.getSystemDate();
	}
	
}

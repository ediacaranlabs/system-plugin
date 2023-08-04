package br.com.uoutec.community.ediacaran.system.event;

import java.io.Serializable;
import java.util.List;

import br.com.uoutec.persistence.EntityAccessException;


public interface SystemEventEntityAccess {

	void save(SystemEvent e) throws EntityAccessException;

	void update(SystemEvent e) throws EntityAccessException;

	void delete(SystemEvent e) throws EntityAccessException;
	
	SystemEvent findById(Serializable id) throws EntityAccessException;

	List<SystemEvent> getByType(SystemEventType type, Integer first, 
			Integer max) throws EntityAccessException;
	
	void flush();

}

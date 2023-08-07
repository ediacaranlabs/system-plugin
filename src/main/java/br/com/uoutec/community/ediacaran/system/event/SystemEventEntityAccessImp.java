package br.com.uoutec.community.ediacaran.system.event;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import br.com.uoutec.persistence.EntityAccessException;

@RequestScoped
public class SystemEventEntityAccessImp implements SystemEventEntityAccess{

	@Override
	public void save(SystemEvent e) throws EntityAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SystemEvent e) throws EntityAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(SystemEvent e) throws EntityAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SystemEvent findById(Serializable id) throws EntityAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SystemEvent> getByType(SystemEventType type, Integer first, Integer max) throws EntityAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}


}

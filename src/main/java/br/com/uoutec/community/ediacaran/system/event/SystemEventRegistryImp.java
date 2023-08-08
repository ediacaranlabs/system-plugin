package br.com.uoutec.community.ediacaran.system.event;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.plugins.SecurityUtil;


@Singleton
public class SystemEventRegistryImp 
	implements SystemEventRegistry {

	@Inject
	private SystemEventEntityAccess entityAccess;

	@Override
	public void registerSystemEvent(SystemEvent entity)
			throws SystemEventRegistryException {
		
		SecurityUtil.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "register"));

		try{
			if(entity.getId() == null){
				this.entityAccess.save(entity);
			}
			else{
				this.entityAccess.update(entity);
			}
		}
		catch(Throwable e){
			throw new SystemEventRegistryException(e);
		}
	}

	@Override
	public void removeSystemEvent(SystemEvent entity)
			throws SystemEventRegistryException {
		
		SecurityUtil.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "unregister"));
		
		try{
			this.entityAccess.delete(entity);
		}
		catch(Throwable e){
			throw new SystemEventRegistryException(e);
		}
	}

	@Override
	public List<SystemEvent> getSystemEventByType(SystemEventType type,
			Integer first, Integer max) throws SystemEventRegistryException {
		
		SecurityUtil.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "access.type"));
		
		try{
			return this.entityAccess.getByType(type, first, max);
		}
		catch(Throwable e){
			throw new SystemEventRegistryException(e);
		}
	}

	@Override
	public void flush() {
		entityAccess.flush();
	}
	
}
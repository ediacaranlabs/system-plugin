package br.com.uoutec.community.ediacaran.system.event;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.community.ediacaran.system.cdi.Parallel;
import br.com.uoutec.application.SystemProperties;
import br.com.uoutec.community.ediacaran.system.cdi.ActiveRequestContext;

@Singleton
public class EventRegistryImp implements EventRegistry{

	private static final String LINE_SEPARATOR = SystemProperties.getProperty("line.separator");
	
	private static final Logger logger = LoggerFactory.getLogger(EventRegistryImp.class);
	@Inject
	private SystemEventRegistry systemEventRegistry;

	@Transactional(rollbackOn = Throwable.class)
	@Parallel
	@ActiveRequestContext
	public void info(String group, String subgroup, String message) {
		this.registry(SystemEventType.INFO, group, subgroup, message, null);
	}
	
	@Transactional(rollbackOn = Throwable.class)
	@Parallel
	@ActiveRequestContext
	public void warn(String group, String subgroup, String message, Throwable ex) {
		this.registry(SystemEventType.WARNING, group, subgroup, message, ex);
	}

	@Transactional(rollbackOn = Throwable.class)
	@Parallel
	@ActiveRequestContext
	public void warn(String group, String subgroup, String message) {
		this.registry(SystemEventType.WARNING, group, subgroup, message, null);
	}
	
	@Transactional(rollbackOn = Throwable.class)
	@Parallel
	@ActiveRequestContext
	public void error(String group, String subgroup, String message) {
		this.registry(SystemEventType.ERROR, group, subgroup, message, null);
	}
	
	@Transactional(rollbackOn = Throwable.class)
	@Parallel
	@ActiveRequestContext
	public void error(String group, String subgroup, String message, Throwable ex) {
		this.registry(SystemEventType.ERROR, group, subgroup, message, ex);
	}

	private void registry(SystemEventType type, String group, String subgroup, 
			String message, Throwable ex) {
		try{
			SystemEvent e = new SystemEvent();
			e.setDate(new Date());
			e.setGroup(group);
			e.setSubgroup(subgroup);
			e.setType(type);
			if(ex != null){
				StringBuilder builder = new StringBuilder(message);
				builder.append(LINE_SEPARATOR);
				builder.append(this.toString(ex));
				e.setMessage(builder.toString());
			}
			else{
				e.setMessage(message);
			}
			
			
			this.systemEventRegistry.registerSystemEvent(e);
		}
		catch(Throwable e){
			logger.error("Falha ao tentar registrar o erro", e);
		}
	}
	
	private String toString(Throwable e){
		if(e == null){
			return "null";
		}
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
		PrintStream ps = new PrintStream(bout);
		try{
			e.printStackTrace(ps);
			ps.flush();
			byte[] data = bout.toByteArray();
			return new String(data);
		}
		finally{
			try{
				ps.close();
			}
			catch(Throwable ex){
			}
		}
		
	}
	
}

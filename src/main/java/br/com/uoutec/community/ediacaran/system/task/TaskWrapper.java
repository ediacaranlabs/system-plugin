package br.com.uoutec.community.ediacaran.system.task;

import java.util.concurrent.TimeUnit;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPluginProvider;

public class TaskWrapper implements Runnable{

	private Class<? extends Runnable> taskClass;
	
	private TimeUnit timeUnit;
	
	private long time;

	private boolean run;
	
	private EntityContextPluginProvider ecp;
	
	public TaskWrapper(Class<? extends Runnable> taskClass, EntityContextPluginProvider ecp, 
			TimeUnit timeUnit, long time) {
		this.taskClass = taskClass;
		this.timeUnit  = timeUnit;
		this.time      = time;
		this.run       = false;
		this.ecp       = ecp;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	@Override
	public void run() {

		this.run = true;
		
		while(this.run){
			try{
				Thread.sleep(this.timeUnit.toMillis(this.time));
				Runnable task = ecp.getEntity(this.taskClass);
				task.run();
			}
			catch(InterruptedException e){
				e.printStackTrace();
				break;
			}
			catch(Throwable e){
				e.printStackTrace();
			}
		}
		
	}
	
}

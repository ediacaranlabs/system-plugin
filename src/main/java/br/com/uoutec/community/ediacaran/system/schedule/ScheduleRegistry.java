package br.com.uoutec.community.ediacaran.system.schedule;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public interface ScheduleRegistry {

    void schedule(String code, Runnable command, long delay, TimeUnit unit);
    
    <V> void schedule(String code, Callable<V> callable, long delay, TimeUnit unit);
	
    void scheduleAtFixedRate(String code, Runnable command, long initialDelay, long period, TimeUnit unit);

    void scheduleWithFixedDelay(String code, Runnable command, long initialDelay, long delay, TimeUnit unit);
	
    ScheduledStatus<?> getScheduledStatus(String code);
    
	boolean cancelScheduled(String code);
    
}

package br.com.uoutec.community.ediacaran.system.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.brandao.concurrent.NamedLock;

import br.com.uoutec.community.ediacaran.system.lock.NamedLockThreadContext.ThreadGroupLock;

public final class Lock {

	static ConcurrentMap<String, NamedLock> locks = new ConcurrentHashMap<String, NamedLock>();

	public static void lock(String groupName, String resource){
		NamedLock namedLock = getNamedLock(groupName);
		GroupLock group = getGroup(groupName);
		group.lock(namedLock, resource);
	}

	public static void unlock(String groupName, String resource){
		NamedLock namedLock = getNamedLock(groupName);
		GroupLock group = getGroup(groupName);
		
		group.unlock(namedLock, resource);
	}
	
	private static GroupLock getGroup(String groupName){
		ConcurrentMap<String, GroupLock> groups = getGroupMap(groupName).group;
		
		GroupLock group = groups.get(groupName);
		
		if(group == null){
			GroupLock newGroup = new GroupLock();
			GroupLock current = groups.putIfAbsent(groupName, newGroup);
			group = current == null? newGroup : current;
		}
		
		return group;
	}
	
	private static ThreadGroupLock getGroupMap(String group){
		ThreadGroupLock groups = NamedLockThreadContext.threadGroupLock.get();
		
		if(groups == null){
			throw new IllegalStateException("o contexto não foi encontrado");
		}
		
		return groups;
	}
	
	private static NamedLock getNamedLock(String group){
		NamedLock lock = locks.get(group);
		
		if(lock == null){
			NamedLock newNamedLock = new NamedLock();
			NamedLock current = locks.putIfAbsent(group, newNamedLock);
			return current == null? newNamedLock : current;
		}
		
		return lock;
	}
	
}

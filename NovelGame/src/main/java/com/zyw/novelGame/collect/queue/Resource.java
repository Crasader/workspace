package com.zyw.novelGame.collect.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zyw.utils.Common;
@Component
public class Resource {
	
	public static final  Logger logger=LoggerFactory.getLogger(Resource.class);
	
	private static volatile BlockingQueue<QueueInfo> resourceQueue ;
		
	private static volatile Resource instance;
	
	@Autowired
	private  Common common;
	
	private static Common staticCommon;
	
	@PostConstruct
	private void init() {
		staticCommon=common;
	}
	private Resource() {}
		
	public static Resource getInstance(){
		if(instance==null) {
			synchronized(Resource.class) {
				if(instance==null) {
					instance=new Resource();
					resourceQueue= new LinkedBlockingQueue<QueueInfo>(staticCommon.getBlocking_queue_nums());
				}
			}
		}
		return instance;
		
	}
	
	public int size() {
		return resourceQueue.size();
	}
	/**
	  * 向资源池中添加资源
	  */
	public void add(QueueInfo queueInfo){
	         try {
	             resourceQueue.put(queueInfo);
	             System.out.println("生产者" + Thread.currentThread().getName()
	                     + "生产一件资源," + "当前资源池有" + resourceQueue.size() + 
	                     "个资源");
	         } catch (InterruptedException e) {
	            e.printStackTrace();
	         }
	     }
	
	/**
	  * 向资源池中移除资源
	  */
	public synchronized QueueInfo remove(){
		QueueInfo queueInfo=null;
	         try {
	        	 queueInfo=resourceQueue.take();
	             System.out.println("消费者" + Thread.currentThread().getName() + 
	                     "消耗一件资源," + "当前资源池有" + resourceQueue.size() 
                     + "个资源");
	         } catch (InterruptedException e) {
	             e.printStackTrace();
	         }
	         
	         return queueInfo;
	    }
	
/*	public static void main(String[] args){
		Producer producer=new Producer();
		Consumer  consumer=new Consumer();
		producer.start();
		producer.add(new QueueInfo());
		consumer.start();
		
	}*/

}

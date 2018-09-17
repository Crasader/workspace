package com.zyw.novelGame.collect.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyw.utils.Common;

public class Resource {
	
	public static final  Logger logger=LoggerFactory.getLogger(Resource.class);
	
	private BlockingQueue<QueueInfo> resourceQueue ;
	
	private int capacity=0;
		
	public Resource(int capacity){
		this.capacity=capacity;
		this.resourceQueue= new LinkedBlockingQueue<QueueInfo>(capacity);
		
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
	public QueueInfo remove(){
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
	
	public static void main(String[] args){
		Resource resource=new Resource(Common.BLOCKING_QUEUE_NUMS);
		Producer producer=new Producer(resource);
		Consumer  consumer=new Consumer(resource);
		producer.start();
		consumer.start();
		
	}

}

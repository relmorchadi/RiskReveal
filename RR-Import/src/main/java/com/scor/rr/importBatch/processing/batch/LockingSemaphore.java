package com.scor.rr.importBatch.processing.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.support.MetricType;

import java.util.concurrent.Semaphore;

/**
 * Created by U002629 on 26/06/2015.
 */
@ManagedResource()
public class LockingSemaphore implements InitializingBean {
    Logger logger = LoggerFactory.getLogger(LockingSemaphore.class);

    private Semaphore semaphore;
    private int maxConcurrent = 1;

    public void setMaxConcurrent(int maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }

    @Override
    public void afterPropertiesSet() {
    	logger.debug("LockingSemaphore - initialized with {} max slot",maxConcurrent);
        semaphore = new Semaphore(maxConcurrent, true);
    }
    
    public boolean acquire(){
        try {
            logger.debug("LockingSemaphore - acquire");
            this.semaphore.acquire();
            logger.debug("LockingSemaphore - acquired");
            return true;
        } catch (InterruptedException e) {
            logger.debug("LockingSemaphore - exception {}", e);
            this.semaphore.release();
            return false;
        }
    }

    public void release() {
        logger.debug("LockingSemaphore - release");
        this.semaphore.release();
        logger.debug("LockingSemaphore - released");
    }

    @ManagedMetric(metricType= MetricType.GAUGE,description="total number slot",unit="slot(s)")
    public int getTotalNumberOfSlot()
    {
    	return this.maxConcurrent;
    }
    
    @ManagedMetric(metricType= MetricType.GAUGE,description="number of available slot",unit="slot(s)")
    public int getAvailableSlot()
    {
    	return this.semaphore.availablePermits();
    }
    @ManagedMetric(metricType= MetricType.GAUGE,description="number of waiting thread to aquire a slot",unit="slot(s)")
    public int getQueuedAquire()
    {
    	return this.semaphore.getQueueLength();
    }
    
    @ManagedOperation(description="remove a number of slot from the available slot, return true if it succeed, if it fails the total number of slot remain unchanged")
    public boolean tryRemoveSlot(int slots)
    {
    	boolean result=this.semaphore.tryAcquire(slots);
    	if (result)
    	{
    		maxConcurrent-=slots;
    	}
    	return result;
    }
    
    @ManagedOperation(description="add a number of slot to the available slot")
    public void tryAddSlot(int slots)
    {
    	this.semaphore.release(slots);
    	this.maxConcurrent+=slots;
    }

}

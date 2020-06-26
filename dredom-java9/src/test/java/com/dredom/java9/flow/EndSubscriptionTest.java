package com.dredom.java9.flow;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import org.junit.jupiter.api.Test;

/**
 * JUnit 5 sample, test Publish/Subscribe.
 */
public class EndSubscriptionTest {

    class EndSubscriberNtfy<T> extends EndSubscriber<T> {
        @Override
        public void onComplete() {
            super.onComplete();
            synchronized(this) {
                this.notifyAll();
            }
        }
    }
    
    @Test
    public void whenSubscribeToIt_thenShouldConsumeAll() throws Exception {
        // given
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriber<>();
        publisher.subscribe(subscriber);
        List<String> list = List.of("1", "x", "2", "x");
        
        // when
        assertEquals(1, publisher.getNumberOfSubscribers());
        list.forEach(publisher::submit);
        publisher.close();
        
        // then
        Thread.sleep(100);
        assertEquals(list.size(), subscriber.consumedElements.size());
        
    }

    /**
     * Use wait/notify for asynchronous process. 
     * But still possible for notify() to execute before wait().
     * But wait(100) is a failsafe.
     */
    @Test
    public void whenSubscribeToIt_thenShouldConsumeAll_notify() throws Exception {
        // given
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriberNtfy<>();
        publisher.subscribe(subscriber);
        List<String> list = List.of("1", "x", "2", "x");
        
        // when
        assertEquals(1, publisher.getNumberOfSubscribers());
        list.forEach(publisher::submit);
        publisher.close();
        
        // then
        synchronized(subscriber) {
            subscriber.wait(100);
        }
        assertEquals(list.size(), subscriber.consumedElements.size());
        
    }

}   
    
    


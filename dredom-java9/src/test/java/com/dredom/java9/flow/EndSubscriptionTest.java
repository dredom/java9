package com.dredom.java9.flow;

//import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import org.junit.Assert;
import org.junit.Test;

//import org.junit.jupiter.api.Test;


public class EndSubscriptionTest {

    @Test
    public void whenSubscribeToIt_thenShouldConsumeAll() throws Exception {
        // given
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriber<>();
        publisher.subscribe(subscriber);
        List<String> list = List.of("1", "x", "2", "x");
        
        // when
        assertThat(publisher.getNumberOfSubscribers(), is(1));
        list.forEach(publisher::submit);
        publisher.close();
        
        // then
        Thread.sleep(100);
        Assert.assertEquals(list.size(), subscriber.consumedElements.size());
        
    }
}   
    
    


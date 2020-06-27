/**
 * 
 */
package com.dredom.java9.flow;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

/**
 * Plagiarized from example code.
 * Added in optional post processing hook to enable wait/notify.
 * @author andre, 2020
 *
 */
public class EndSubscriber<T> implements Subscriber<T> {
    private Subscription subscription;
    public List<T> consumedElements = new LinkedList<>();
    private Consumer<EndSubscriber<T>> onCompleteConsumer;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        System.out.println("Got: " + item);
        consumedElements.add(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Done");
        if (onCompleteConsumer != null) {
            onCompleteConsumer.accept(this);
        }
    }

    /**
     * Used to inject a notify into onComplete processing for testing.
     * @param onCompleteConsumer
     */
    public void setOnCompleteConsumer(Consumer<EndSubscriber<T>> onCompleteConsumer) {
        this.onCompleteConsumer = onCompleteConsumer;
    }

}

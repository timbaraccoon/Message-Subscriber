package com.message.subscriber.service;

import com.message.subscriber.dao.PurchaseMessageRepository;
import com.message.subscriber.dao.SubscriptionMessageRepository;
import com.message.subscriber.jsonpojo.Message;
import com.message.subscriber.entity.PurchaseMessage;
import com.message.subscriber.entity.SubscriptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@EnableScheduling
public class SubscriberServiceImpl implements SubscriberService {

    private final PurchaseMessageRepository purchaseRepository;
    private final SubscriptionMessageRepository subscriptionRepository;

    private final ConcurrentLinkedQueue<Message> queue;
    private final Object lock1;
    private final Object lock2;

    @Autowired
    public SubscriberServiceImpl(PurchaseMessageRepository purchaseRepository,
                                 SubscriptionMessageRepository subscriptionRepository) {
        this.purchaseRepository = purchaseRepository;
        this.subscriptionRepository = subscriptionRepository;

        queue = new ConcurrentLinkedQueue<>();
        lock1 = new Object();
        lock2 = new Object();
    }


    @Override
    public void receiveMessage(Message message) {
        queue.add(message);

        if (queue.size() > 25) {
            sortMessagesToTargetDB();
        }
    }

    @Override
    @Scheduled(fixedRate = 60_000)
    @Async
    public void sortMessagesToTargetDB() {
        if (!queue.isEmpty()) {
            List<PurchaseMessage> purchaseMessages = new ArrayList<>();
            List<SubscriptionMessage> subscriptionMessages  = new ArrayList<>();

            while (!queue.isEmpty()) {
                sortMessages(purchaseMessages, subscriptionMessages);
            }

            synchronized (lock1) {
                saveAllPurchases(purchaseMessages);
            }
            synchronized (lock2) {
                saveAllSubscriptions(subscriptionMessages);
            }
        }
    }

    private void sortMessages(List<PurchaseMessage> purchase,
                              List<SubscriptionMessage> subscription) {
        Message message = queue.poll();

        if (message != null && message.getAction().equalsIgnoreCase("PURCHASE")) {
            purchase.add(new PurchaseMessage(message));
        }
        if (message != null && message.getAction().equalsIgnoreCase("SUBSCRIPTION")) {
            subscription.add(new SubscriptionMessage(message));
        }
    }

    @Transactional
    public void saveAllPurchases(List<PurchaseMessage> purchaseMessages) {
        purchaseRepository.saveAll(purchaseMessages);
        purchaseMessages.clear();
    }

    @Transactional
    public void saveAllSubscriptions(List<SubscriptionMessage> subscriptionMessages) {
        subscriptionRepository.saveAll(subscriptionMessages);
        subscriptionMessages.clear();
    }

}

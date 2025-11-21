package com.example.demo.Observer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationManager {
    @Autowired
    private BookNotifier bookNotifier;

    @Autowired
    private EmailNotificationService emailService;

    @Autowired
    private InAppNotificationService inAppService;

    @PostConstruct
    public void init() {
        bookNotifier.attach(emailService);
        bookNotifier.attach(inAppService);
    }

    public void addBook(String title) {
        bookNotifier.bookAdded(title);
    }
    public void bookBought(String title) {
        bookNotifier.bookBought(title);
    }
}

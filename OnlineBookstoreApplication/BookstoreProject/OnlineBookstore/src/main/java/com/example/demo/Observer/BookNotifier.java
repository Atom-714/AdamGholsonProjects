package com.example.demo.Observer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Flow;

@Component
public class BookNotifier extends Subject{

    public void bookAdded(String title) {
        notifyObservers("book added: " + title);
    }
    public void bookBought(String title) {
        notifyObservers("book bought: " + title);
    }
}

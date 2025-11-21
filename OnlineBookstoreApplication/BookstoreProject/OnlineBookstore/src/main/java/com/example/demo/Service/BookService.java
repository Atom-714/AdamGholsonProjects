package com.example.demo.Service;

import com.example.demo.Domain.Book;
import com.example.demo.Repository.BookRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public Book createBook(Book book)
    {
        if (userService.getCurrentUserID() != 0 && userRepository.findById(userService.getCurrentUserID()).get().getRole().equals("Admin")) {
            bookRepository.save(book);
            return bookRepository.save(book);
        }
        throw new IllegalArgumentException("Admin not logged in");
    }

    public Book updateBook(Book oldBook, Book newBook)
    {
        if (userService.getCurrentUserID() != 0 && userRepository.findById(userService.getCurrentUserID()).get().getRole().equals("Admin")) {
            oldBook.setTitle(newBook.getTitle());
            oldBook.setAuthor(newBook.getAuthor());
            oldBook.setPrice(newBook.getPrice());
            oldBook.setAvailable(newBook.isAvailable());

            return bookRepository.save(oldBook);
        }
        throw new IllegalArgumentException("Admin not logged in");
    }
}

package com.example.demo.Controllers;

import com.example.demo.Domain.Book;
import com.example.demo.Domain.Cart;
import com.example.demo.Factory.User;
import com.example.demo.Observer.BookNotifier;
import com.example.demo.Observer.NotificationManager;
import com.example.demo.Repository.BookRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.BookService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private NotificationManager notificationManager;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getBook(@PathVariable Integer id) {
        return ResponseEntity.ok(bookRepository.findById(id));
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Book>> getBooksByCartId() {
        return ResponseEntity.ok(bookRepository.findByCartId(userService.getUserCartID()));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Book>> getBooksByUserId() {
        return ResponseEntity.ok(bookRepository.findByUserId((long) userService.getCurrentUserID()));
    }

    @PostMapping
    public ResponseEntity createBook(@RequestBody Book book) {
        bookService.createBook(book);
        notificationManager.addBook(book.getTitle());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@PathVariable Integer id, @RequestBody Book book) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book existingBook = optionalBook.get();

        return ResponseEntity.ok(bookService.updateBook(existingBook, book));
    }

    @PutMapping("/{bookId}/addToCart")
    public ResponseEntity<String> addBookToCart(@PathVariable Long bookId) {
        Integer currentUserId = userService.getCurrentUserID();
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("No logged in user");
        }

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(Optional.ofNullable(user));

        Book book = bookRepository.findById(Math.toIntExact(bookId))
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setAvailable(false);
        book.setCartId(Math.toIntExact(cart.getId()));

        bookRepository.save(book);

        return ResponseEntity.ok("Book added to cart successfully");
    }

    @PutMapping("/{bookId}/removeFromCart")
    public ResponseEntity<String> removeFromCart(@PathVariable Long bookId) {
        Book book = bookRepository.findById(Math.toIntExact(bookId))
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setAvailable(true);
        book.setCartId(0);

        bookRepository.save(book);

        return ResponseEntity.ok("Book added to cart successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable Integer id) {
        Book book = bookRepository.findById(id).get();
        bookRepository.delete(book);
        return ResponseEntity.ok().build();
    }
}

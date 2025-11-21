package com.example.demo.Service;

import com.example.demo.Controllers.UserController;
import com.example.demo.Domain.*;
import com.example.demo.Factory.User;
import com.example.demo.Observer.NotificationManager;
import com.example.demo.Repository.*;
import com.example.demo.State.CheckoutContext;
import com.example.demo.Strategy.CreditCardPayment;
import com.example.demo.Strategy.GiftCardPayment;
import com.example.demo.Strategy.PayPalPayment;
import com.example.demo.Strategy.PaymentContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private OrderBookRepository orderBookRepository;

    @Autowired
    private NotificationManager notificationManager;

    private CheckoutContext checkoutContext;

    private CreditCardPayment creditCardPayment = new CreditCardPayment();
    private PayPalPayment payPalPayment = new PayPalPayment();
    private GiftCardPayment giftCardPayment = new GiftCardPayment();
    private PaymentContext paymentContext = new PaymentContext(creditCardPayment);

    public Cart getOrCreateCartForUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }
        return cart;
    }

    public double getCartCost(long id) {
        List<Book> books = bookRepository.findByCartId(id);

        double amount = 0;

        for (int i = 0; i < books.size(); i++) {
            amount += books.get(i).getPrice();
        }

        return amount;
    }

    public void startPurchase() {
        checkoutContext = new CheckoutContext();
    }
    public void changeState() {
        checkoutContext.next();
    }

    public void changeStrategy(int index) {
        System.out.println(index);
        switch (index) {
            case 1:
                paymentContext.setPaymentStategy(payPalPayment);
                break;
            case 2:
                paymentContext.setPaymentStategy(giftCardPayment);
                break;
            default:
                paymentContext.setPaymentStategy(creditCardPayment);
                break;
        }
        makePayment(getCartCost(userService.getUserCartID()));
    }

    public void makePayment(double amount) {
        paymentContext.pay(amount);
        completePurchase();
    }

    public void completePurchase() {
        int userId = userService.getCurrentUserID();
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new RuntimeException("Cart not found for user: " + userId);
        }

        List<Book> cartBooks = bookRepository.findByCartId(cart.getId());

        CustomerOrders order = new CustomerOrders();
        order.setUserId(userId);
        order.setTotalPrice(getCartCost(cart.getId()));

        order = customerOrderRepository.save(order);

        for (Book book : cartBooks) {

            OrderBook ob = new OrderBook();
            ob.setOrderId(order.getId());
            ob.setBookId(book.getId());

            orderBookRepository.save(ob);

            book.setCartId(0);
            book.setUserId(userId);
            bookRepository.save(book);

            notificationManager.bookBought(book.getTitle());
        }

        bookRepository.saveAll(cartBooks);
    }

    public List<OrderResponse> getUserOrders() {
        int userId = userService.getCurrentUserID();

        List<CustomerOrders> orders = customerOrderRepository.findByUserId(userId);

        List<OrderResponse> responseList = new ArrayList<>();

        for (CustomerOrders order : orders) {
            List<OrderBook> mappings = orderBookRepository.findByOrderId(order.getId());

            List<Book> books = mappings.stream()
                    .map(ob -> bookRepository.findById(ob.getBookId()).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();

            OrderResponse dto = new OrderResponse();
            dto.setOrderId(Long.valueOf(order.getId()));
            dto.setTotalPrice(order.getTotalPrice());
            dto.setBooks(books);

            responseList.add(dto);
        }

        return responseList;
    }
}

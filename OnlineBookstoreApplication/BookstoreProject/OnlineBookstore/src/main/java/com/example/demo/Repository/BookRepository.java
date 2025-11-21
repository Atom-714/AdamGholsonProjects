package com.example.demo.Repository;

import com.example.demo.Domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByCartId(Long cartId);
    List<Book> findByUserId(Long userId);
}

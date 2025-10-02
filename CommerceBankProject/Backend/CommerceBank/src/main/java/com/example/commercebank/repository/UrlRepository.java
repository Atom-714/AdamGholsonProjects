package com.example.commercebank.repository;

import com.example.commercebank.domain.Url;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Url getUrlById(Long id);
    // public Url updateUrlById(Long id, Url url);
}

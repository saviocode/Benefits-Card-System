package com.qintess.benefits.card.system.repository;

import com.qintess.benefits.card.system.domain.Card;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Card c SET c.active = false WHERE c.id = :id")
    int disabled(@Param("id") Long id);
}

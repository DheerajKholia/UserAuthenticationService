package com.ecommerce.authentication.repositories;

import com.ecommerce.authentication.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepo extends JpaRepository<Session, Long> {
}

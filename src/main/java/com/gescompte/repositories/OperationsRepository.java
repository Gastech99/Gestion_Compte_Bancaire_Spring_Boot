package com.gescompte.repositories;

import com.gescompte.entities.Operations;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OperationsRepository extends JpaRepository<Operations, Long> {
}

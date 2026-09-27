package com.portafolio.bankingtransactions.infrastructure.repository;

import com.portafolio.bankingtransactions.infrastructure.persistence.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, String> {
}

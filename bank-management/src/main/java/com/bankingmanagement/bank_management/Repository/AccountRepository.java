package com.bankingmanagement.bank_management.Repository;

import com.bankingmanagement.bank_management.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}

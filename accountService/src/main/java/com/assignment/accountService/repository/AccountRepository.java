package com.assignment.accountService.repository;
import com.assignment.accountService.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sliyanag
 * @created 10/10/2022 - 11:20 AM
 * @project EurekaServer
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}

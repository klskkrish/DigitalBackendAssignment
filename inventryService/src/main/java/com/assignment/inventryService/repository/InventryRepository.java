package com.assignment.inventryService.repository;
import com.assignment.inventryService.entity.Inventry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sliyanag
 * @created 10/10/2022 - 11:20 AM
 * @project EurekaServer
 */
public interface InventryRepository extends JpaRepository<Inventry, Long> {
}

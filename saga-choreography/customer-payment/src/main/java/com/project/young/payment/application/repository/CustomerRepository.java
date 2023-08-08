package com.project.young.payment.application.repository;

import com.project.young.payment.application.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
}

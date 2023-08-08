package com.project.young.payment.application.service;

import com.project.young.common.events.payment.PaymentStatus;
import com.project.young.common.util.DuplicateEventValidator;
import com.project.young.payment.application.entity.Customer;
import com.project.young.payment.application.entity.CustomerPayment;
import com.project.young.payment.application.mapper.EntityDtoMapper;
import com.project.young.payment.application.repository.CustomerRepository;
import com.project.young.payment.application.repository.PaymentRepository;
import com.project.young.payment.common.dto.PaymentDto;
import com.project.young.payment.common.dto.PaymentProcessRequest;
import com.project.young.payment.common.exception.CustomerNotFoundException;
import com.project.young.payment.common.exception.InsufficientBalanceException;
import com.project.young.payment.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private static final Mono<Customer> CUSTOMER_NOT_FOUND = Mono.error(new CustomerNotFoundException());
    private static final Mono<Customer> INSUFFICIENT_BALANCE = Mono.error(new InsufficientBalanceException());
    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public Mono<PaymentDto> process(PaymentProcessRequest request) {
        return DuplicateEventValidator.validate(
                this.paymentRepository.existsByOrderId(request.orderId()),
                this.customerRepository.findById(request.customerId())
        )
                .switchIfEmpty(CUSTOMER_NOT_FOUND)
                .filter(c -> c.getBalance() >= request.amount())
                .switchIfEmpty(INSUFFICIENT_BALANCE)
                .flatMap(c -> this.deductPayment(c, request))
                .doOnNext(dto -> log.info("payment processed for {}", dto.orderId()));
    }

    private Mono<PaymentDto> deductPayment(Customer customer, PaymentProcessRequest request) {
        CustomerPayment customerPayment = EntityDtoMapper.toCustomerPayment(request);
        customer.setBalance(customer.getBalance() - request.amount());
        customerPayment.setStatus(PaymentStatus.DEDUCTED);
        return this.customerRepository.save(customer)
                .then(this.paymentRepository.save(customerPayment))
                .map(EntityDtoMapper::toDto);
    }

    @Override
    public Mono<PaymentDto> refund(UUID orderId) {
        return null;
    }
}

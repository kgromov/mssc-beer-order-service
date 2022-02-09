package guru.sfg.beer.order.service.web.controllers;

import guru.sfg.beer.order.service.repositories.CustomerRepository;
import guru.sfg.beer.order.service.web.mappers.CustomerMapper;
import guru.sfg.brewery.model.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomersController {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @GetMapping
    @Transactional(readOnly = true)
    public List<CustomerDto> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToDto)
                .collect(Collectors.toList());
    }
}

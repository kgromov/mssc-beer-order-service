package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.Customer;
import guru.sfg.brewery.model.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class, BeerOrderMapper.class})
public interface CustomerMapper {
    @Mapping(source = "customerName", target = "name")
    CustomerDto customerToDto(Customer customer);

    @Mapping(source = "name", target = "customerName")
    Customer dtoToCustomer(CustomerDto dto);
}

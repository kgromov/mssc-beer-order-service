package guru.sfg.beer.order.service.services;

import guru.sfg.brewery.model.ValidateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static guru.sfg.beer.order.service.config.JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValidateOrderListener {
    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = VALIDATE_ORDER_RESPONSE_QUEUE)
    public void onOrderValidated(ValidateOrderResponse orderResponse) {
        beerOrderManager.validateOrder(orderResponse);
    }
}

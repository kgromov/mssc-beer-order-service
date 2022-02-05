package guru.sfg.beer.order.service.services.testcomponets;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.brewery.model.events.AllocateOrderRequest;
import guru.sfg.brewery.model.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static guru.sfg.beer.order.service.config.JmsConfig.ALLOCATE_ORDER_QUEUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class AllocationOrderListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = ALLOCATE_ORDER_QUEUE)
    public void onAllocationRequest(AllocateOrderRequest orderRequest) {
        orderRequest.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
            beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
        });

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
                AllocateOrderResult.builder()
                        .beerOrderDto(orderRequest.getBeerOrderDto())
                        .build());
    }
}

package guru.sfg.beer.order.service.sm;

import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.domain.BeerOrderEventEnum;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import guru.sfg.beer.order.service.web.mappers.BeerOrderMapper;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.ValidateBeerOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.UUID;

import static guru.sfg.beer.order.service.config.JmsConfig.VALIDATE_ORDER_QUEUE;
import static guru.sfg.beer.order.service.services.BeerOrderManagerImpl.ORDER_ID_HEADER;

@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    @Transactional
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
//        BeerOrder beerOrder = beerOrderRepository.getOne(stateContext.getMessageHeaders().get(ORDER_ID_HEADER, UUID.class));
        BeerOrder beerOrder = beerOrderRepository.getOne(stateContext.getStateMachine().getUuid());
        BeerOrderDto newBeerOrderDto = BeerOrderDto.builder()
                .orderStatus(stateContext.getStateMachine().getId())
                .build();
        BeerOrderDto beerOrderDto = Optional.ofNullable(beerOrder).map(beerOrderMapper::beerOrderToDto).orElse(newBeerOrderDto);
        // can be request itself
        Message message = MessageBuilder.withPayload(new ValidateBeerOrderRequest(beerOrderDto)).build();
        jmsTemplate.convertAndSend(VALIDATE_ORDER_QUEUE, message);
    }
}

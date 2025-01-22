package guru.sfg.beer.inventory.service.services;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.brewery.model.events.AllocateOrderRequest;
import guru.sfg.brewery.model.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationListener {
	
	private final JmsTemplate jmsTemplate;
	private final AllocationService allocationService;

	@JmsListener(destination = JmsConfig.ALLOCATE_ORDER_REQUEST_QUEUE)
	public void listen(AllocateOrderRequest allocateOrderRequest) {

		AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();

		builder.beerOrderDto(allocateOrderRequest.getBeerOrderDto());

		Boolean allocationResult = allocationService.allocateOrder(allocateOrderRequest.getBeerOrderDto());

		try {
			if (allocationResult) {
				builder.pendingInventory(false);
			} else {
				builder.pendingInventory(true);
			}
			builder.allocationError(false);
		} catch (Exception ex) {
			log.error("Error occurred during allocating order for beerOrderId "
					+ allocateOrderRequest.getBeerOrderDto().getId());
			builder.allocationError(true);
		}

		jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESULT_QUEUE, builder.build());
	}
}

package guru.sfg.beer.inventory.service.services;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.brewery.model.events.DeallocationOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocationListener {

	private final AllocationService allocationService;

	@JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
	public void listen(DeallocationOrderRequest request) {
		allocationService.deallocateOrder(request.getBeerOrderDto());
	}
}

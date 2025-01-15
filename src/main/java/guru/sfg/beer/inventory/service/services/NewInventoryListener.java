package guru.sfg.beer.inventory.service.services;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.brewery.model.events.BeerDto;
import guru.sfg.brewery.model.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewInventoryListener {

	private final BeerInventoryRepository beerInventoryRepository;
	private final JmsTemplate jmsTemplate;

	@Transactional
	@JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
	public void listen(NewInventoryEvent event) throws NotFoundException {
		log.debug("Getting inventory for " + event.getBeerDto().getBeerName());

		beerInventoryRepository.save(BeerInventory
				.builder()
				.beerId(event.getBeerDto().getId())
				.upc(event.getBeerDto().getUpc())
				.quantityOnHand(event.getBeerDto().getQuantityOnHand())
				.build());

	}
}

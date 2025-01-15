package guru.sfg.brewery.model.events;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {
	
	private static final long serialVersionUID = -1767703357372754708L;

	public NewInventoryEvent(BeerDto beerDto) {
		super(beerDto);
	}

}

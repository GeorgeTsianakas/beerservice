package com.brewery.model.events;

import com.brewery.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent {

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }

}

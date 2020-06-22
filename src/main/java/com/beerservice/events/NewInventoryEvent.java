package com.beerservice.events;

import com.beerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent {

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }

}

package com.beerservice.services;

import com.beerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {

    BeerDto getById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

}

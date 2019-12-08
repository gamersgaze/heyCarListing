package com.heycar.challenge.repository;

import com.heycar.challenge.controller.request.FetchListingRequest;
import com.heycar.challenge.entities.ListingEntity;

import java.util.List;

public interface ListingRepositoryCustom {

    List<ListingEntity> searchListingByDealerId(FetchListingRequest request);
}

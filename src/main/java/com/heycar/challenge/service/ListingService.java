package com.heycar.challenge.service;

import com.heycar.challenge.controller.request.FetchListingRequest;
import com.heycar.challenge.entities.ListingEntity;
import com.heycar.challenge.models.ListingDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ListingService {

    void addListing(List<ListingDTO> listings, Long dealerId);

    void addListingFromCSV(MultipartFile file, Long dealerId);

    List<ListingEntity> fetchListings(FetchListingRequest request);
}

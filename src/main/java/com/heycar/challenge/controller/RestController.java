package com.heycar.challenge.controller;

import com.heycar.challenge.controller.request.FetchListingRequest;
import com.heycar.challenge.models.ListingDTO;
import com.heycar.challenge.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.heycar.challenge.utils.HeyCarConstants.MAX_RESULTS;
import static com.heycar.challenge.utils.HeyCarConstants.START_AT;

@Controller
public class RestController {

    @Autowired
    private ListingService listingService;


    @PostMapping("/vehicle_listings/{dealerId}")
    public ResponseEntity<?> addListings(@RequestBody List<ListingDTO> listings, @PathVariable("dealerId") Long dealerId) {
        listingService.addListing(listings, dealerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/upload_csv/{dealerId}")
    public ResponseEntity<?> addListingsFromCSV(@RequestParam(value = "file") MultipartFile file, @PathVariable("dealerId") Long dealerId) {
        listingService.addListingFromCSV(file, dealerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
     * default Page size is set to 50 int startAt and maxResults variable
     */

    @GetMapping("/search")
    public ResponseEntity<?> fetchListings(@RequestParam(required = false) String make, @RequestParam(required = false) String model,
                                           @RequestParam(required = false) Long year, @RequestParam(required = false) String color,
                                           @RequestParam(required = false, defaultValue = START_AT) Integer startAt,
                                           @RequestParam(required = false, defaultValue = MAX_RESULTS) Integer maxResults) {

        FetchListingRequest request = new FetchListingRequest(make, model, year, color, startAt, maxResults);
        return ResponseEntity.ok().body(listingService.fetchListings(request));
    }

}

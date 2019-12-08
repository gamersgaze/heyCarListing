package com.heycar.challenge.task;

import com.heycar.challenge.entities.ListingEntity;
import com.heycar.challenge.models.ListingDTO;
import com.heycar.challenge.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class ListingTask extends BaseTask {

    @Autowired
    private ListingRepository listingRepository;

    /*  Assumptions->  Since you have not mentioned the listing limit for CSV, I am treating it as a Bulk listings case
     *
     *  Ideally this should be an Async process because if csv contains large number of records it could take very long
     *  I have implemented similar task in our own application, there are better ways to do this , will discuss it in interview.
     */


    /*
     * instead of checking every listing I am first fetching the listings already saved in database
     * and then deducting listing which are not found in the database and saving them.
     * This method is exponentially faster than checking each listing
     *      for example: suppose csv contains 1000 listing
     *                       you hit select query for each listing to check if the listing exist in db and if exist update it
     *                       so that is 2 queries for each listing . there for it will hit (1000*2=2000) queries. which will slow down db
     *           instead:
     *                    I am first hitting select query with in clause to find the already saved listing (1 query)
     *                    and then deducting it with listing in request and then you can insert it with 'insert ignore into... VALUES (),(),()' (1 query)
     *
     * we should do this process in batches if the number of listing is huge. but for this challenge, I am not using batches
     */

    //this method returns the total newly inserted listings (to make this method unit testCase friendly)
    public Integer saveOrUpdateListings(List<ListingDTO> listings, Long dealerId) {

        //here I have assumed that Although same code can be used by other dealers, a single dealer can have only unique listing code.
        Set<String> codeIds = listings.stream().filter(Objects::nonNull)
                .filter(listing -> isNotBlank(listing.getCode()))
                .map(listing -> listing.getCode())
                .collect(Collectors.toSet());

        //find saved listing
        List<ListingEntity> savedListings = listingRepository.findListingsByDealerId(dealerId, codeIds);

        //if no listing is saved then all are new listing save them all
        if (isEmpty(savedListings)) {

            //add all listings
            createNewListings(listings, dealerId);
            return  listings.size();
        } else {
            //extract list of codes
            List<String> savedListingCodes = savedListings.stream().map(listing -> listing.getCode()).collect(Collectors.toList());

            //deduct the savedListing with the listing found in request, remaining list is new listings (listingsToSave)
            List<ListingDTO> listingsToSave = listings.stream().filter(listing -> isNotBlank(listing.getCode()))
                    .filter(listing -> !savedListingCodes.contains(listing.getCode()))
                    .collect(Collectors.toList());

            //create new listings for remaining list
            createNewListings(listingsToSave, dealerId);

            //update the savedListings
            updateListing(savedListings, listings, dealerId);
            return listingsToSave.size();
        }
    }

    private void createNewListings(List<ListingDTO> listingsToSave, Long dealerId) {
        List<ListingEntity> entities = new ArrayList<>();
        listingsToSave.stream().forEach(listingDTO -> {
            ListingEntity entity = mapToEntity(listingDTO, ListingEntity.class);
            entity.setDealerId(dealerId);
            entities.add(entity);
        });

        saveListings(entities);
    }

    /*
     *  here I have used JPA saveAll(to save times) which is very slow because it fires separate queries (I never use it for bulk insert)
     *  the best approach is to use 'insert ignore into listings ..values(),(),(),(),()'. this insert all listings in single query
     *  'insert ignore into' query will take care of unique constraints violation which is required in this case with 'dealerId' and 'code' column
     *   Cautions: -> the database limitation needs to be considered , some db have placeholder limit and size of the query limits
     *                which can be increased from the db configurations
     *             -> we could fire these in batches in case we have to create thousands of listing at once (Async task)
     *             -> we are successfully using it in our current application with csv records sizes up to 10,000 (10K)
     */
    private List<ListingEntity> saveListings(List<ListingEntity> entities) {
        return listingRepository.saveAll(entities);
    }

    private void updateListing(List<ListingEntity> savedListings, List<ListingDTO> Listings, Long dealerId) {

        List<ListingEntity> updatedListings = new ArrayList<>();
        savedListings.stream().forEach(savedListing -> {
            Optional<ListingDTO> listing = Listings.stream().filter(ls -> ls.getCode().equals(savedListing.getCode())).findFirst();
            if (listing.isPresent()) {
                savedListing.setColor(listing.get().getColor());
                savedListing.setkW(listing.get().getkW());
                savedListing.setMake(listing.get().getMake());
                savedListing.setModel(listing.get().getModel());
                savedListing.setPrice(listing.get().getPrice());
                savedListing.setYear(listing.get().getYear());
                savedListing.setDealerId(dealerId);
                updatedListings.add(savedListing);
            }
        });
        listingRepository.saveAll(updatedListings);
    }
}

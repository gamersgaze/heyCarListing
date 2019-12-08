package com.heycar.challenge.task;

import com.heycar.challenge.entities.ListingEntity;
import com.heycar.challenge.models.ListingDTO;
import com.heycar.challenge.repository.ListingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ListingTaskTest {

    @MockBean
    private ListingRepository listingRepository;

    @Autowired
    private ListingTask listingTask;

    /*
     * -->  I have written unit testCases for only saveOrUpdateListings because that is the only class that contains critical business logic
     *      such as fetching and deducting listing for saving and updating.
     * -->  this project is db operation heavy tasks and since other classes and methods do not contain significant java logic , they are skipped to save time
     * -->  In db operation heavy task, integration testCases are more effective. I would have written them in real application
     */

    @Test
    void saveOrUpdateListings_when_noListingAlreadyExist_then_insertAllListing() {
        //mock findByDealerId (return empty ArrayList)
        when(listingRepository.findListingsByDealerId(any(), any())).thenReturn(new ArrayList<>());

        //total 3 listings are passed , none of them exist in database
        int listingInserted = listingTask.saveOrUpdateListings(listingsToSave(3), 1L);

        //if all 3 listings are added, the test is passed
        assertEquals(3,listingInserted);
    }

    @Test
    void saveOrUpdateListings_when_someListingAlreadyExist_then_insertRemainingListing() {
        //one listing with code'test0' already exist in database (mock)
        when(listingRepository.findListingsByDealerId(any(), any())).thenReturn(listingEntities(1));

        //total 3 listings are passed , one of them exist in database (with code 'test0')
        int listingInserted = listingTask.saveOrUpdateListings(listingsToSave(3), 1L);

        //since 1 listing is already exist in database, if remaining 2 listings are added then test is passed
        assertEquals(2,listingInserted);
    }


    private List<ListingEntity> listingEntities(int totalListing) {
        List<ListingEntity> listingEntities = new ArrayList<>();
        for (int i = 0; i < totalListing; i++) {
            ListingEntity entity = new ListingEntity();
            entity.setCode("test" + i);
            listingEntities.add(entity);
        }
        return listingEntities;
    }

    private List<ListingDTO> listingsToSave(int totalListings) {
        List<ListingDTO> listingDTOS = new ArrayList<>();
        for (int i = 0; i < totalListings; i++) {
            ListingDTO listingDTO = new ListingDTO();
            listingDTO.setCode("test" + i);
            listingDTOS.add(listingDTO);
        }
        return listingDTOS;
    }
}
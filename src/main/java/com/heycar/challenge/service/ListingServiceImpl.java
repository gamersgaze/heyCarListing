package com.heycar.challenge.service;

import com.heycar.challenge.controller.request.FetchListingRequest;
import com.heycar.challenge.entities.ListingEntity;
import com.heycar.challenge.exceptions.BadRequestException;
import com.heycar.challenge.exceptions.NoValidListingFoundException;
import com.heycar.challenge.models.ListingDTO;
import com.heycar.challenge.parser.ListingCSVParser;
import com.heycar.challenge.repository.ListingRepository;
import com.heycar.challenge.task.ListingTask;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.heycar.challenge.utils.HeyCarConstants.CSV_EXTENSION;
import static com.heycar.challenge.utils.HeyCarConstants.MAX_IMPORT_FILE_SIZE;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Service
public class ListingServiceImpl extends BaseService implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ListingTask listingTask;

    @Autowired
    private ListingCSVParser listingCSVParser;


    @Override
    public void addListing(List<ListingDTO> listings, Long dealerId) {
        validateListing(listings);
        listingTask.saveOrUpdateListings(listings, dealerId);
    }

    @Override
    public void addListingFromCSV(MultipartFile file, Long dealerId) {
        validateFile(file);
        List<ListingDTO>listings=listingCSVParser.parseListingFromCSV(file);
        validateListing(listings);
        listingTask.saveOrUpdateListings(listings, dealerId);
    }


    @Override
    public List<ListingEntity> fetchListings(FetchListingRequest request) {
        return  listingRepository.searchListingByDealerId(request);
    }


    private void validateFile(MultipartFile file) {

        //validate extension
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals(CSV_EXTENSION)) {
            throw new BadRequestException(i18nMessage("only-csv-file-accepted"));
        }

        //validate file size
        if (file.getSize() <= 0) {
            throw new BadRequestException(i18nMessage("file-cannot-be-empty"));
        }

        //validate max file size limit
        if (file.getSize() > MAX_IMPORT_FILE_SIZE) {
            throw new BadRequestException(i18nMessage("import.file-size-exceed"));
        }

    }

    private void validateListing(List<ListingDTO> listings) {
        //if listing is empty , return
        if (isEmpty(listings)) {
            throw new NoValidListingFoundException(i18nMessage("no.valid.listing.found"));
        }

        /*
         * other validations for different fields of listing could be added below
         * I have assumed here that rest of the fields are valid.
         */
    }

}
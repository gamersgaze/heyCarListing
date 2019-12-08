package com.heycar.challenge.parser;

import com.heycar.challenge.exceptions.FileReadingException;
import com.heycar.challenge.models.ListingDTO;
import com.heycar.challenge.utils.HeyCarUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static com.heycar.challenge.utils.CSVColumns.*;
import static com.heycar.challenge.utils.HeyCarConstants.MAKE_MODEL_SPLIT_PATTER;
import static com.heycar.challenge.utils.HeyCarUtil.parseLong;

@Component
public class ListingCSVParser extends BaseParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListingCSVParser.class);

    public List<ListingDTO> parseListingFromCSV(MultipartFile file) {

        List<ListingDTO> listingDTOs = new ArrayList<>();
        try (Reader reader = getReader(file);
             CSVParser csvParser = getParserWithHeaderSchema(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                listingDTOs.add(prepareDTOFromCSVRecord(csvRecord));
            }

        } catch (IOException e) {
            throw new FileReadingException(i18nMessage("error.reading.file"), e);
        }
        return listingDTOs;
    }

    /*
     * prepare the header schema for CSV file.
     * It allows us to use headerName instead of headIndex while reading the record which makes code easy to read and modify.
     */
    private CSVParser getParserWithHeaderSchema(Reader reader) throws IOException {
        return new CSVParser(reader, CSVFormat.DEFAULT
                .withHeader(CODE.getValue(),
                        MAKE_MODEL.getValue(),
                        POWER_IN_PS.getValue(),
                        YEAR.getValue(),
                        COLOR.getValue(),
                        PRICE.getValue())
                .withIgnoreHeaderCase()
                .withSkipHeaderRecord()
                .withTrim());
    }

    /*
     * prepare ListingDTP from a single CSVRecord
     */
    private ListingDTO prepareDTOFromCSVRecord(CSVRecord csvRecord) {
        ListingDTO listingDTO = new ListingDTO();

        listingDTO.setCode(readValue(csvRecord, CODE.getValue()));
        listingDTO.setkW(parseLong(readValue(csvRecord, POWER_IN_PS.getValue())));
        listingDTO.setYear(parseLong(readValue(csvRecord, YEAR.getValue())));
        listingDTO.setColor(readValue(csvRecord, COLOR.getValue()));
        listingDTO.setPrice(parseLong(readValue(csvRecord, PRICE.getValue())));

        //set make and model property
        String makeModel = csvRecord.get(MAKE_MODEL.getValue());
        String[] makeModelArray = makeModel.split(MAKE_MODEL_SPLIT_PATTER);
        listingDTO.setMake(makeModelArray[0]);
        if (makeModelArray.length > 1) {
            listingDTO.setModel(makeModelArray[1]);
        }

        return listingDTO;
    }


    //read csv cell value
    private String readValue(CSVRecord csvRecord, String header) {
        String value = null;
        try {
            value = csvRecord.get(header);
        } catch (Exception e) {
            LOGGER.error("error reading csv cell value ::", e.getMessage());
        }
        return value;
    }

}

package com.heycar.challenge.parser;

import com.heycar.challenge.utils.BaseUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public abstract class BaseParser extends BaseUtils {


    /**
     * converts the MultipartFile to a bufferReader
     * its will be used in all parser , therefore I have put it in BaseParser
     */
    protected Reader getReader(MultipartFile file) throws IOException {
        return new BufferedReader(new InputStreamReader(file.getInputStream()));
    }
}

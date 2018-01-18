package com.shipping.generator;

import com.shipping.exception.GenerateIdException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RegisterIdGenerator implements Generator {

    private static final String ID_PATTERN = "YYYYMMDDHHMMSSS";

    @Override
    public Long generate() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ID_PATTERN);
        final String formattedValue = simpleDateFormat.format(new Date());
        try {
            return Long.parseLong(formattedValue);
        } catch (final NumberFormatException e) {
            throw new GenerateIdException(e);
        }

    }
}

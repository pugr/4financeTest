package com.forfinance.interview.test.service.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Custom converter to convert {@link LocalDateTime} to {@link Timestamp} on the ORM level.
 * 
 * @author Jan Koren
 *
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {

        if (attribute != null) {
            return Timestamp.valueOf(attribute);
        }

        return null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {

        if (dbData != null) {
            return dbData.toLocalDateTime();
        }

        return null;
    }

}
package com.ibm.pmc.ticket.common.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Converter(autoApply = true)
public class OffsetDateTimePersistenceConverter implements AttributeConverter<OffsetDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(OffsetDateTime attribute) {
        return attribute != null ? Timestamp.valueOf(attribute.toLocalDateTime()) : null;
    }

    @Override
    public OffsetDateTime convertToEntityAttribute(Timestamp dbData) {
        ZoneOffset zoneOffset = dbData.toInstant().atZone(ZoneId.systemDefault()).getOffset();
        return dbData != null ? dbData.toLocalDateTime().atOffset(zoneOffset) : null;
    }
}

package lapr4.jobs4u.jobApplication.domain;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;


public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ";";
    private static final int MAX_LENGTH = 255; // Maximum length of the filePath column

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        if (stringList == null || stringList.isEmpty()) {
            return "";
        }
        // Join strings and truncate if necessary
        String joinedString = String.join(SPLIT_CHAR, stringList);
        if (joinedString.length() > MAX_LENGTH) {
            joinedString = joinedString.substring(0, MAX_LENGTH);
        }
        return joinedString;
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();
    }

}

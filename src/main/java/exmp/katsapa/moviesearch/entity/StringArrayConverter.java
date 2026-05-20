package exmp.katsapa.moviesearch.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringArrayConverter implements AttributeConverter<List<String>, String[]> {

    @Override
    public String[] convertToDatabaseColumn(List<String> list) {
        return list == null ? new String[0] : list.toArray(new String[0]);
    }

    @Override
    public List<String> convertToEntityAttribute(String[] array) {
        if (array == null || array.length == 0) return List.of();

        if (array.length == 1 && array[0].startsWith("{")) {
            String raw = array[0];
            raw = raw.substring(1, raw.length() - 1);
            return Arrays.stream(raw.split(","))
                    .map(s -> s.replaceAll("^\"|\"$", "").trim())
                    .collect(Collectors.toList());
        }

        return Arrays.asList(array);
    }
}
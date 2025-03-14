package pl.radek.flashcards.converter;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class FlashcardConverterFromText
{
    private String text;

    public Map<String, String> doParse(){
        List<String> lines = this.text.trim().lines().toList();
        if (text.isBlank())
            return Collections.emptyMap();

        if (ConverterFromTextStrategy.hasNumberHeader(lines))
            return ConverterFromTextStrategy.PARSE_WITH_NUMBER_HEADERS.parse(this.text);
        else
            return ConverterFromTextStrategy.PARSE_ALTERNATELY.parse(this.text);
    }
}

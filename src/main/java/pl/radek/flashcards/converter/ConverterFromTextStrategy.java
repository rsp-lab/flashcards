package pl.radek.flashcards.converter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum ConverterFromTextStrategy
{
    PARSE_ALTERNATELY() {
        @Override
        public Map<String, String> parse(String text) {
            Map<String, String> flashcardsMap = new LinkedHashMap<>();
            List<String> lines = text.lines().toList();
            String firstSide = null;

            for (int i = 0; i < lines.size(); i++) {
                if (i % 2 == 0)
                    firstSide = lines.get(i);
                else
                    flashcardsMap.put(firstSide, lines.get(i));
            }
            if (lines.size() % 2 == 1)
                flashcardsMap.put(firstSide, "");
            
            return flashcardsMap;
        }
    },
    
    PARSE_WITH_NUMBER_HEADERS() {
        @Override
        public Map<String, String> parse(String text) {
            Map<String, String> flashcardsMap = new LinkedHashMap<>();
            String[] chunks = text.split(" {2,}[0-9]+");
            
            for (String chunk : chunks) {
                if (isLineBlank(chunk))
                    continue;
                
                StringBuilder firstSideBuilder = new StringBuilder();
                StringBuilder secondSideBuilder = new StringBuilder();
                String trimmed = chunk.trim();
                
                List<String> lines = trimmed.lines().toList();
                for (int i = 0; i < lines.size(); i++) {
                    if (i < (lines.size() / 2F))
                        firstSideBuilder.append(lines.get(i)).append("\n");
                    else
                        secondSideBuilder.append(lines.get(i)).append("\n");
                }
                
                String firstSide = firstSideBuilder.toString();
                String secondSide = secondSideBuilder.toString();
                flashcardsMap.put(firstSide, secondSide);
            }
            return flashcardsMap;
        }
    };

    public abstract Map<String, String> parse(String text);
    
    public static boolean isLineBlank(String line) {
        return line.isBlank();
    }
    
    public static boolean hasNumberHeader(List<String> lines) {
        String line = lines.get(0);
        
        if (line.isBlank())
            return false;
        
        char header = line.trim().charAt(0);
        return header >= '0' && header <= '9';
    }
}

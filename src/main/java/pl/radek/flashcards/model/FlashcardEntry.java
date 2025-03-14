package pl.radek.flashcards.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FlashcardEntry
{
    public static final String ENTRY_TEXT =
            """
            [optional number]
            first side
            second side
            [optional number]
            first side
            second side
            ...
            """;
    
    private String text;
    private int elementsPerPage;

    public FlashcardEntry() {
        this.text = ENTRY_TEXT;
        this.elementsPerPage = 1;
    }
    
    public FlashcardEntry(int elementsPerPage) {
        this.text = ENTRY_TEXT;
        this.elementsPerPage = elementsPerPage;
    }

    @Override
    public String toString() {
        return String.format("EntryText (%s, %d)", this.text, this.elementsPerPage);
    }
}

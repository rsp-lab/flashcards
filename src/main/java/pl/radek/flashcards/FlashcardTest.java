package pl.radek.flashcards;

import lombok.*;
import org.springframework.stereotype.Component;
import pl.radek.flashcards.model.Flashcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Getter
@Setter
public class FlashcardTest
{
    private List<Flashcard> flashcards = new ArrayList<>();
    
    public void shuffleRandomlyFlashcards() {
        Collections.shuffle(flashcards);
    }
}

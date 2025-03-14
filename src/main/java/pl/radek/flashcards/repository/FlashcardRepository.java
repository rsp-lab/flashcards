package pl.radek.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.radek.flashcards.model.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long>
{

}

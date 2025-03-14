package pl.radek.flashcards.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Flashcards")
@Getter
@Setter
public class Flashcard
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String first;
    private String second;

    public Flashcard() { }

    public Flashcard(String first, String second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("FlashcardEntry (%s, %s, %s)", this.id, this.first, this.second);
    }
}

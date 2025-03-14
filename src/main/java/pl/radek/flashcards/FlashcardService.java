package pl.radek.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.radek.flashcards.model.Flashcard;
import pl.radek.flashcards.repository.FlashcardRepository;
import pl.radek.flashcards.converter.FlashcardConverterFromText;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class FlashcardService
{
    @Autowired
    FlashcardRepository flashcardRepository;

    @Autowired
    FlashcardTest flashcardTest;

    public long getTotalEntries() {
        return flashcardRepository.count();
    }

    public void removeAllEntries() {
        flashcardRepository.deleteAll();
    }

    public void setupTest() {
        flashcardTest.setFlashcards(flashcardRepository.findAll());
        flashcardTest.shuffleRandomlyFlashcards();
    }

    public Page<Flashcard> findFlashcardByPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Flashcard> allFlashcards = flashcardTest.getFlashcards();
        List<Flashcard> flashcards;
        
        if (allFlashcards.size() < startItem)
            flashcards = Collections.emptyList();
        else {
            int toIndex = Math.min(startItem + pageSize, allFlashcards.size());
            flashcards = allFlashcards.subList(startItem, toIndex);
        }

        return new PageImpl<>(flashcards, PageRequest.of(currentPage, pageSize), allFlashcards.size());
    }

    public void addEntriesFromText(String text) {
        Map<String, String> flashcards = new FlashcardConverterFromText(text).doParse();
        for (Map.Entry<String, String> entry : flashcards.entrySet()) {
            String firstSide = entry.getKey();
            String secondSide = entry.getValue();
            flashcardRepository.save(new Flashcard(firstSide, !secondSide.isBlank() ? secondSide : null));
        }
    }
}

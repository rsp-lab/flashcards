package pl.radek.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.radek.flashcards.model.FlashcardEntry;
import pl.radek.flashcards.model.Flashcard;

import java.util.List;
import java.util.Optional;

@Controller
public class FlashcardController
{
    public int elementsPerPage = 1;
    
    @Autowired
    private FlashcardService flashcardService;

    @RequestMapping("/")
    public String start(@RequestParam(required = false) boolean noEntries, Model model) {
        long entriesNumber = flashcardService.getTotalEntries();
        model.addAttribute("message", "Flashcards");
        model.addAttribute("entriesNumber", entriesNumber);
        model.addAttribute("entry", new FlashcardEntry(elementsPerPage));
        model.addAttribute("noEntries", noEntries);
        return "start";
    }
    
    @RequestMapping(value = "/changeElements", method = RequestMethod.POST)
    public String changeNrOfElementsPerPage(@ModelAttribute FlashcardEntry entry, Model model) {
        elementsPerPage = Math.max(entry.getElementsPerPage(), 1);
        return "redirect:/";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addEntries(@ModelAttribute FlashcardEntry entry, Model model) {
        flashcardService.addEntriesFromText(entry.getText());
        return "redirect:/";
    }

    @RequestMapping("/remove")
    public String removeEntries(Model model) {
        flashcardService.removeAllEntries();
        return "redirect:/";
    }

    @RequestMapping("/prepareTest")
    public String testEntries() {
        flashcardService.setupTest();
        return "redirect:/test?page=0";
    }

    @RequestMapping("/test")
    public String testEntries(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(0);
        int nextPage = currentPage + 1;
        int prevPage = currentPage - 1;
        int pageSize = size.orElse(elementsPerPage);

        Page<Flashcard> flashcardPage = flashcardService.findFlashcardByPage(PageRequest.of(currentPage, pageSize));
        if (flashcardPage.getTotalPages() == 0)
            return "redirect:/?noEntries=true";

        List<Flashcard> flashcards = flashcardPage.getContent();
        int totalPages = flashcardPage.getTotalPages();
        // if (nextPage == totalPages)
        //     nextPage = 0;
        
        String progress = String.format("Current progress: %d%%", 100 * (currentPage + 1) / totalPages);
        
        model.addAttribute("progress", progress);
        model.addAttribute("flashcards", flashcards);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("totalPages", totalPages);
        return "test";
    }
}

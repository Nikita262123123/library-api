package by.modsen.libraryservice.controller;

import by.modsen.libraryservice.entity.Record;
import by.modsen.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;

    @PostMapping
    public void registerBook(@RequestParam Long bookId) {
        libraryService.registerNewBook(bookId);
    }

    @DeleteMapping("/{bookId}")
    public void deleteRecord(@PathVariable Long bookId) {
        libraryService.deleteRecordByBookId(bookId);
    }

    @PostMapping("/{recordId}/borrow")
    public void borrowBook(@PathVariable Long recordId) {
        libraryService.borrowBook(recordId);
    }

    @PostMapping("/{recordId}/return")
    public void returnBook(@PathVariable Long recordId) {
        libraryService.returnBook(recordId);
    }

    @GetMapping
    public List<Record> getAllRecords() {
        return libraryService.getAllRecords();
    }

    @GetMapping("/available")
    public List<Record> getAvailableBooks() {
        return libraryService.getAvailableBooks();
    }

    @GetMapping("/{bookId}")
    public Record getRecordByBookId(@PathVariable Long bookId) {
        return libraryService.getRecordByBookId(bookId);
    }


}


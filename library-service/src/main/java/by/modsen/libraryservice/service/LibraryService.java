package by.modsen.libraryservice.service;

import by.modsen.libraryservice.entity.Record;
import by.modsen.libraryservice.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;

    @Transactional
    public void registerNewBook(Long bookId) {
        Record record = new Record();
        record.setBookId(bookId);
        libraryRepository.save(record);
    }

    @Transactional
    public void deleteRecordByBookId(Long bookId) {
        Record record = libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Book with id " + bookId + " not found"));

        if (record.getIsAvailable() != null && !record.getIsAvailable()) {
            throw new RuntimeException("Cannot delete record: book is currently not available");
        }

        libraryRepository.delete(record);
    }


    @Transactional
    public void borrowBook(Long bookId) {
        Record record = libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Record with id " + bookId + " not found"));

        if (record.getIsAvailable() != null && !record.getIsAvailable()) {
            throw new RuntimeException("Book is not available");
        }

        LocalDateTime now = LocalDateTime.now();
        record.setBorrowedAt(now);
        record.setIsAvailable(false);
        libraryRepository.save(record);
    }

    @Transactional
    public void returnBook(Long bookId) {
        Record record = libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Record with id " + bookId + " not found"));

        if (record.getBorrowedAt() == null) {
            throw new RuntimeException("Book is not currently borrowed");
        }

        record.setBorrowedAt(null);
        record.setIsAvailable(true);
        libraryRepository.save(record);
    }

    @Transactional(readOnly = true)
    public List<Record> getAllRecords() {
        return libraryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Record> getAvailableBooks() {
        return libraryRepository.findAllByIsAvailableTrueOrIsAvailableIsNull();
    }

    public Record getRecordByBookId(Long bookId) {
        return libraryRepository
                .findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Book with id " + bookId + " not found"));
    }









}

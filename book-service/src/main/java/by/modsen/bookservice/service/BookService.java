package by.modsen.bookservice.service;

import by.modsen.bookservice.dto.request.BookRequest;
import by.modsen.bookservice.dto.response.BookResponse;
import by.modsen.bookservice.entity.Book;
import by.modsen.bookservice.exception.BookNotFoundException;
import by.modsen.bookservice.exception.BookWithIsbnExistException;
import by.modsen.bookservice.mapper.BookMapper;
import by.modsen.bookservice.repository.BookRepository;
import by.modsen.bookservice.util.ExceptionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format(ExceptionConstants.BOOK_NOT_FOUND, id)));
        return bookMapper.toBookResponse(book);
    }

    public List<BookResponse> getAllBooks() {
        List<Book> bookEntities = bookRepository.findAll();
        return bookMapper.toBookResponseList(bookEntities);
    }

    public BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(String.format(ExceptionConstants.BOOK_WITH_ISBN_NOT_FOUND, isbn)));
        return bookMapper.toBookResponse(book);
    }

    @Transactional
    public BookResponse addBook(BookRequest bookRequest) {
        if (bookRepository.findByIsbn(bookRequest.isbn()).isPresent()) {
            throw new BookWithIsbnExistException(ExceptionConstants.BOOK_WITH_ISBN_EXIST);
        }
        Book book = bookMapper.toBookEntity(bookRequest);
        bookRepository.save(book);
        return bookMapper.toBookResponse(book);
    }

    @Transactional
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format(ExceptionConstants.BOOK_NOT_FOUND, id)));
        if (!existingBook.getIsbn().equals(bookRequest.isbn()) && bookRepository.existsByIsbn(bookRequest.isbn())) {
            throw new BookWithIsbnExistException(ExceptionConstants.BOOK_WITH_ISBN_EXIST);
        }
        bookMapper.updateBookFromDTO(bookRequest, existingBook);
        bookRepository.save(existingBook);
        return bookMapper.toBookResponse(existingBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(String.format(ExceptionConstants.BOOK_NOT_FOUND, id));
        }
        bookRepository.deleteById(id);
    }

}


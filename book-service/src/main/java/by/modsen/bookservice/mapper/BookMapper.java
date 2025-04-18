package by.modsen.bookservice.mapper;

import by.modsen.bookservice.dto.request.BookRequest;
import by.modsen.bookservice.dto.response.BookResponse;
import by.modsen.bookservice.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponse toBookResponse(Book book);

    Book toBookEntity(BookRequest bookRequest);

    List<BookResponse> toBookResponseList(List<Book> bookEntities);

    void updateBookFromDTO(BookRequest bookRequest, @MappingTarget Book book);
}

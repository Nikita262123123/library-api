package by.modsen.bookservice.dto.response;

public record BookResponse(
        Long id,

        String isbn,

        String title,

        String genre,

        String description,

        String author
) {}

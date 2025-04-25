package by.modsen.bookservice.dto.request;

public record BookRequest(

        String isbn,

        String title,

        String genre,

        String description,

        String author
) {}
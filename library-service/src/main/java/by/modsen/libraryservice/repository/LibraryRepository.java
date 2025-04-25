package by.modsen.libraryservice.repository;

import by.modsen.libraryservice.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Record, Long> {
    Optional<Record> findByBookId(Long bookId);

    List<Record> findAllByIsAvailableTrueOrIsAvailableIsNull();

}

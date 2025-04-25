package by.modsen.bookservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "library-service", url = "http://localhost:8081")
public interface LibraryServiceClient {
    @PostMapping("/library")
    void registerBook(@RequestParam Long bookId);

    @DeleteMapping("/library/{bookId}")
    void deleteRecord(@PathVariable Long bookId);
}

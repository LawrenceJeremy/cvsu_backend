package com.example.backend.Controller;

import com.example.backend.Dto.BookDTO;
import com.example.backend.Service.BookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // POST MAPPING
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BookDTO create(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("yearPublished") String yearPublished,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile file
    ) throws IOException {

        BookDTO dto = new BookDTO();
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setYearPublished(yearPublished);
        dto.setDescription(description);

        return bookService.create(dto, file);
    }

    // GET ALL MAPPING
    @GetMapping
    public List<BookDTO> getAll() {
        return bookService.getAll();
    }

    // GET BY ID MAPPING
    @GetMapping("/{id}")
    public BookDTO getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    // PUT MAPPING ID
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BookDTO update(
            @PathVariable Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "yearPublished", required = false) String yearPublished,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "image", required = false) MultipartFile file
    ) throws IOException {

        BookDTO dto = new BookDTO();
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setYearPublished(yearPublished);
        dto.setDescription(description);

        return bookService.update(id, dto, file);
    }

    // DELETE ID MAPPING
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws IOException {
        bookService.delete(id);
    }
}

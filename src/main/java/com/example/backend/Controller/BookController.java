package com.example.backend.Controller;

import com.example.backend.Entity.Book;
import com.example.backend.Repository.BookRepository;
import com.example.backend.Service.BookService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    private final String UPLOAD_DIR = "uploads/"; // make sure this folder exists

    // Get all books
    @GetMapping
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    // Get a book by ID (needed for pre-fill on Update page)
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // POST with file upload
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Book uploadBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("yearPublished") String yearPublished,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile file
    ) throws IOException {

        String originalFileName = file.getOriginalFilename();
        String safeFileName = originalFileName.replace(" ", "-");

        Path path = Paths.get(UPLOAD_DIR + safeFileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setYearPublished(yearPublished);
        book.setDescription(description);
        book.setImageUrl("/uploads/" + safeFileName);

        return bookRepository.save(book);
    }

    // PUT update existing book
    @PutMapping("/update/{id}")
    public Book updateBook(
            @PathVariable Long id,
            @RequestParam(value="title", required=false) String title,
            @RequestParam(value="author", required=false) String author,
            @RequestParam(value="yearPublished", required=false) String yearPublished,
            @RequestParam(value="description", required=false) String description,
            @RequestParam(value="image", required=false) MultipartFile file
    ) throws IOException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (title != null) book.setTitle(title);
        if (author != null) book.setAuthor(author);
        if (yearPublished != null) book.setYearPublished(yearPublished);
        if (description != null) book.setDescription(description);

        if (file != null) {
            String safeFileName = file.getOriginalFilename().replace(" ", "-");
            Path path = Paths.get(UPLOAD_DIR + safeFileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            book.setImageUrl("/uploads/" + safeFileName);
        }

        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Optionally delete the cover image file
        if (book.getImageUrl() != null) {
            Path path = Paths.get("uploads/" + Paths.get(book.getImageUrl()).getFileName());
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bookRepository.deleteById(id);
    }


}

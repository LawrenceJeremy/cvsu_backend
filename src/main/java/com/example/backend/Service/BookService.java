package com.example.backend.Service;

import com.example.backend.Dto.BookDTO;
import com.example.backend.Entity.Book;
import com.example.backend.Repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final String UPLOAD_DIR = "uploads/";

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file == null) return null;

        String safeName = file.getOriginalFilename().replace(" ", "-");
        Path path = Paths.get(UPLOAD_DIR + safeName);

        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        return "/uploads/" + safeName;
    }

    private BookDTO toDTO(Book b) {
        return new BookDTO(
                b.getId(),
                b.getTitle(),
                b.getAuthor(),
                b.getYearPublished(),
                b.getDescription(),
                b.getImageUrl()
        );
    }

    // CREATE
    public BookDTO create(BookDTO dto, MultipartFile file) throws IOException {
        Book book = new Book();

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setYearPublished(dto.getYearPublished());
        book.setDescription(dto.getDescription());
        book.setImageUrl(saveFile(file));

        return toDTO(bookRepository.save(book));
    }

    // GET ALL
    public List<BookDTO> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public BookDTO getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return toDTO(book);
    }

    // UPDATE BY ID
    public BookDTO update(Long id, BookDTO dto, MultipartFile file) throws IOException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (dto.getTitle() != null) book.setTitle(dto.getTitle());
        if (dto.getAuthor() != null) book.setAuthor(dto.getAuthor());
        if (dto.getYearPublished() != null) book.setYearPublished(dto.getYearPublished());
        if (dto.getDescription() != null) book.setDescription(dto.getDescription());

        if (file != null) {
            book.setImageUrl(saveFile(file));
        }

        return toDTO(bookRepository.save(book));
    }

    // DELETE BY ID
    public void delete(Long id) throws IOException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // delete image file
        if (book.getImageUrl() != null) {
            Path path = Paths.get("uploads/" + Paths.get(book.getImageUrl()).getFileName());
            Files.deleteIfExists(path);
        }

        bookRepository.delete(book);
    }
}

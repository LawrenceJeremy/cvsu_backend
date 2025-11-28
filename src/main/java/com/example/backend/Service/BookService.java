package com.example.backend.Service;
import com.example.backend.Dto.BookRequest;
import com.example.backend.Entity.Book;
import com.example.backend.Repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setYearPublished(request.getYearPublished());
        book.setImageUrl(request.getImageUrl());
        book.setDescription(request.getDescription());
        return bookRepository.save(book);
    }
}

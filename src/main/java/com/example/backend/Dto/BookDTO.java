package com.example.backend.Dto;

public class BookDTO {
    private String title;
    private String author;
    private String yearPublished;
    private String description;

    // getters/setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getYearPublished() { return yearPublished; }
    public void setYearPublished(String yearPublished) { this.yearPublished = yearPublished; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

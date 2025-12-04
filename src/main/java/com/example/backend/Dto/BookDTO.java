package com.example.backend.Dto;

public class BookDTO {
    private Long id; // for response (optional sa request)
    private String title;
    private String author;
    private String yearPublished;
    private String description;
    private String imageUrl; // filled after upload

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(String yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BookDTO() {}

    public BookDTO(Long id, String title, String author, String yearPublished, String description, String imageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}

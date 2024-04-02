package com.projects.MovieRecomondationSystem;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private int yearOfRelease;
    private boolean isActive; // Changed from "active" to "isActive"

    // Constructors
    public Movie() {
        super();
    }

    // Constructor for adding a new movie
    public Movie(String title, String genre, int yearOfRelease) {
        this.title = title;
        this.genre = genre;
        this.yearOfRelease = yearOfRelease;
        this.isActive = true;  // Set the default value to true
    }

    // Constructor for updating an existing movie
    public Movie(int id, String title, String genre, int yearOfRelease, boolean isActive) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.yearOfRelease = yearOfRelease;
        this.isActive = isActive;
    }

    // Getters and setters...
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    // toString method
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", isActive=" + isActive +
                '}';
    }
}

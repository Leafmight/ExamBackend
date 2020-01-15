/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Actor;
import entities.Director;
import entities.Genre;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class MovieDTO {
    private Integer id;
    private String title;
    private int year;
    private List<DirectorDTO> directors = new ArrayList();
    private List<ActorDTO> actors = new ArrayList();
    private List<GenreDTO> genres = new ArrayList();

    public MovieDTO() {
    }

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        
        for (Director director : movie.getDirectors()) {
            this.directors.add(new DirectorDTO(director));
        }
        for (Actor actor : movie.getActors()) {
            this.actors.add(new ActorDTO(actor));
        }
        for (Genre genre : movie.getGenres()) {
            this.genres.add(new GenreDTO(genre));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<DirectorDTO> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorDTO> directors) {
        this.directors = directors;
    }

    public List<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(List<ActorDTO> actors) {
        this.actors = actors;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "MovieDTO{" + "id=" + id + ", title=" + title + ", year=" + year + ", directors=" + directors + ", actors=" + actors + ", genres=" + genres + '}';
    }
    
    

    
}

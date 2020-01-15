/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

/**
 *
 * @author jacobfolkehildebrandt
 */
@Entity
@NamedQuery(name = "Movie.deleteAllRows", query = "DELETE from Movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private int year;
    
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Director> directors = new ArrayList();
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Actor> actors = new ArrayList();
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Genre> genres = new ArrayList();

    public Movie() {
    }

    public Movie(String title, int year) {
        this.title = title;
        this.year = year;
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

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirector(Director director) {
        this.directors.add(director);
        director.setMovie(this);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(Actor actor) {
        this.actors.add(actor);
        actor.setMovie(this);
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenre(Genre genre) {
        this.genres.add(genre);
        genre.setMovie(this);
    }

  
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        if (this.year != other.year) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.directors, other.directors)) {
            return false;
        }
        if (!Objects.equals(this.actors, other.actors)) {
            return false;
        }
        if (!Objects.equals(this.genres, other.genres)) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", title=" + title + ", year=" + year + ", directors=" + directors + ", actors=" + actors + ", genres=" + genres + '}';
    }
    
    
    
    
 
    
}

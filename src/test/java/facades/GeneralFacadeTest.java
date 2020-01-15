/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.MovieDTO;
import entities.Actor;
import entities.Director;
import entities.Genre;
import entities.Movie;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author jacobfolkehildebrandt
 */

public class GeneralFacadeTest {
    
    private static EntityManagerFactory emf;
    private static GeneralFacade facade;

    private static Movie movie;
    private static Director director;
    private static Director director1;
    private static Genre genre;
    private static Actor actor;
    private static Actor actor1;
    private static Actor actor2;
    private static Actor actor3;
    
    public GeneralFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/JanExam_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = GeneralFacade.getGeneralFacade(emf);
    }
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        movie = new Movie("The Lego Movie", 2014);
        director = new Director("Dan Lin","Born 8. April 1973");
        director1 = new Director("Roy Lee","Born 23. March 1969");
        genre = new Genre("Animation");
        actor = new Actor("Chris Pratt", "Voice as Emmit Brickowski (Everyman)");
        actor1 = new Actor("Will Ferrell", "Voice as Lord Business");
        actor2 = new Actor("Elizabeth Banks", "Voice as Lucy / Wildstyle");
        actor3 = new Actor("Will Arnett", "Voice as Bruce Wayne / Batman");
        
        movie.setDirector(director);
        movie.setDirector(director1);
        movie.setGenre(genre);
        movie.setActors(actor);
        movie.setActors(actor1);
        movie.setActors(actor2);
        movie.setActors(actor3);
        
        
        try {
            em.getTransaction().begin();
            em.createQuery("delete from Genre").executeUpdate();
            em.createQuery("delete from Actor").executeUpdate();
            em.createQuery("delete from Director").executeUpdate();
            em.createQuery("delete from Movie").executeUpdate();
            em.persist(movie);
            em.getTransaction().commit();
            
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetAllMovies() {
        System.out.println("-------TEST-------"+ facade.getAllMovies().size());
        assertEquals(1, facade.getAllMovies().size(), "Expects one row(s) in the database");
    }
    @Test
    public void testGetAllDirectors() {
        assertEquals(2, facade.getAllDirectors().size(), "Expects two row(s) in the database");
    }
    @Test
    public void testGetAllGenres() {
        assertEquals(1, facade.getAllGenres().size(), "Expects one row(s) in the database");
    }
    
    @Test
    public void testGetAllActors() {
        assertEquals(4, facade.getAllActors().size(), "Expects four row(s) in the database");
    }
    @Test
    public void testGetMovieByActor() {
        assertEquals(1, facade.getMovieByActor(actor.getName()).size(), "Expects one row(s) in the database");
    }
    
    @Test
    public void testGetMovieByGenre() {
        assertEquals(1, facade.getMovieByGenre(genre.getName()).size(), "Expects one row(s) in the database");
    }
    
    @Test
    public void testGetMovieByDirector() {
        assertEquals(movie.getTitle(), facade.getMovieById(movie.getId()).getTitle(), "Expects same name in the database");
    }
    @Test
    public void testGetMovieByTitle() {
        assertEquals(1, facade.getMovieByPartTitle(movie.getTitle()).size(), "Expects one row(s) in the database");
    }
    
    @Test
    public void testAddMovie() {
        Movie addmovie = new Movie("Spiderman",2000);
        Director adddirector = new Director("Jacob","Koded since he was 3");
        Actor addactor = new Actor("Mads Mikkelsen","b actor");
        Genre addgenre = new Genre("Sci-fi");
        addmovie.setActors(addactor);
        addmovie.setDirector(adddirector);
        addmovie.setGenre(addgenre);
        assertEquals(1, facade.addMovie(new MovieDTO(addmovie)).getDirectors().size(), "Expects one row(s) in the database");
    }
    
    @Test
    public void testEditMovie() {
        movie.setTitle("The Lego Movie 2");
        assertEquals(movie.getTitle(), facade.editMovie(new MovieDTO(movie), movie.getId()).getTitle(), "Expects one row(s) in the database");
    }
    
    
    
}

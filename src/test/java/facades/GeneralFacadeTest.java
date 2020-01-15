/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Actor;
import entities.Director;
import entities.Genre;
import entities.Movie;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author jacobfolkehildebrandt
 */
@Disabled
public class GeneralFacadeTest {
    
    private static EntityManagerFactory emf;
    private static FacadeExample facade;

    public GeneralFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/JanExam_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = FacadeExample.getFacadeExample(emf);
    }
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Movie movie7 = new Movie("The Lego Movie", 2014);
        Director director2 = new Director("Dan Lin","Born 8. April 1973");
        Director director3 = new Director("Roy Lee","Born 23. March 1969");
        Genre genre3 = new Genre("Animation");
        Actor actor2 = new Actor("Chris Pratt", "Voice as Emmit Brickowski (Everyman)");
        Actor actor3 = new Actor("Will Ferrell", "Voice as Lord Business");
        Actor actor4 = new Actor("Elizabeth Banks", "Voice as Lucy / Wildstyle");
        Actor actor5 = new Actor("Will Arnett", "Voice as Bruce Wayne / Batman");
        
        movie7.setDirector(director2);
        movie7.setDirector(director3);
        movie7.setGenre(genre3);
        movie7.setActors(actor2);
        movie7.setActors(actor3);
        movie7.setActors(actor4);
        movie7.setActors(actor5);
        
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(movie7);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {
        assertEquals(2, facade.getRenameMeCount(), "Expects two rows in the database");
    }

}

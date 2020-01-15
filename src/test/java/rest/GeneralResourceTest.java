/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTO.MovieDTO;
import entities.Actor;
import entities.Director;
import entities.Genre;
import entities.Movie;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author jacobfolkehildebrandt
 */
//@Disabled
public class GeneralResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Movie movie, movie1;
    private static Director director, director1, director2, director3;
   
    private static Genre genre, genre1;
    private static Actor actor, actor1, actor2, actor3, actor4, actor5, actor6, actor7;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        movie = new Movie("The Lego Movie", 2014);
        director = new Director("Dan Lin", "Born 8. April 1973");
        director1 = new Director("Roy Lee", "Born 23. March 1969");
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
    
    @Test
    public void testGetAllPersons() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/allMovies").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(1));
    }
    
    @Test
    public void testGetAllDirectors() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/allDirectors").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(2));
    }
    @Test
    public void testGetAllActors() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/allActors").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(4));
    }
    @Test
    public void testGetAllGenres() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/allGenres").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(1));
    }
    @Test
    public void testGetMovieById() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/movieById/" +movie.getId() ).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(6));
    }
    
    @Test
    public void testGetMovieByTitle() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/movieByPartTitle/" +movie.getTitle() ).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(1));
    }
    
    @Test
    public void testGetMovieByGenre() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/movieByGenre/" +genre.getName() ).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(1));
    }
    
    @Test
    public void testGetMovieByActor() throws Exception {
        String first = actor1.getName().split(" ")[0];
        String second = actor1.getName().split(" ")[1];
        System.out.println("++++++++++" + first + "++++" + second);
        given()
                .contentType("application/json")
                .get("/movie/movieByGenre/" +first + "%20" +second  ).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(0));
    }
    
//     @Test
//    public void testAddMovie() throws Exception {
//        movie1 = new Movie("Spiderman",2000);
//        director2 = new Director("Jacob","Koded since he was 3");
//        actor4 = new Actor("Mads Mikkelsen","b actor");
//        genre1 = new Genre("Sci-fi");
//        movie1.setActors(actor4);
//        movie1.setDirector(director2);
//        movie1.setGenre(genre1);
//        MovieDTO m = new MovieDTO(movie1);
//        MovieDTO mdto = given()
//                .contentType("application/json")
//                .body(m)
//                .when()
//                .post("/movie/addMovie")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .extract()
//                .as(MovieDTO.class);
//                assertThat(mdto.getTitle(), equalTo(m.getTitle()));
//    }
//    @Disabled
//    @Test
//    public void testEditMovie() throws Exception {
//        movie.setTitle("Spiderman");
//        MovieDTO m = new MovieDTO(movie);
//         System.out.println(movie.getId() + m.getId());
//        MovieDTO mdto = given()
//                .contentType("application/json")
//                .body(m)
//                .when()
//                .put("/movie/editMovie"+m.getId())
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .extract()
//                .as(MovieDTO.class);
//                assertThat(mdto.getTitle(), equalTo(m.getTitle()));
//    }
    
    
}

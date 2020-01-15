/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTO.ActorDTO;
import DTO.DirectorDTO;
import DTO.GenreDTO;
import DTO.MovieDTO;
import facades.GeneralFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author jacobfolkehildebrandt
 */
@Path("movie")
public class GeneralResource {
        private static GeneralFacade pFacade = new GeneralFacade();
    
     private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/JanExam",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final GeneralFacade FACADE = GeneralFacade.getGeneralFacade(EMF);

    
     @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setup")
    public String setUp() {
        
        FACADE.fill();
    return "Setup Complete!";
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allMovies")
    public List<MovieDTO> getAllMovies() {
        
        return FACADE.getAllMovies();
 
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movieById/{id}")
    public MovieDTO getMovieById(@PathParam("id") int id) {
        
        return FACADE.getMovieById(id);
 
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movieByPartTitle/{title}")
    public List<MovieDTO> getMovieByTitle(@PathParam("title") String title) {
        
        return FACADE.getMovieByPartTitle(title);
 
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movieByGenre/{genre}")
    public List<MovieDTO> getMovieByGenre(@PathParam("genre") String genre) {
        
        return FACADE.getMovieByGenre(genre);
 
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movieByActor/{name}")
    public List<MovieDTO> getMovieByActor(@PathParam("name") String name ) {
        
        return FACADE.getMovieByActor(name);
 
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allDirectors")
    public List<DirectorDTO> getAllDirectors() {
        
        return FACADE.getAllDirectors();
 
    }
     @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allActors")
    public List<ActorDTO> getAllActors() {
        
        return FACADE.getAllActors();
 
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allGenres")
    public List<GenreDTO> getAllGenre() {
        
        return FACADE.getAllGenres();
 
    }
    
    @POST
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("addMovie")
    public MovieDTO addMovie(MovieDTO movieDTO) {
        
        return FACADE.addMovie(movieDTO);
 
    }
    
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @Path("editMovie/{id}")
    public MovieDTO editMovie(@PathParam("id") int id, MovieDTO movieDTO) {
        
        return FACADE.editMovie(movieDTO, id);
 
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTO.ActorDTO;
import DTO.MovieDTO;
import facades.GeneralFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
    public List<MovieDTO> getMovieById(@PathParam("title") String title) {
        
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("movieByActor")
    public List<MovieDTO> getMovieByGenre(ActorDTO actorDTO) {
        System.out.println("ACTOR---------"+ actorDTO);
        return FACADE.getMovieByActor(actorDTO);
 
    }
    
}

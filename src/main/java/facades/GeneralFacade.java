/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.ActorDTO;
import DTO.DirectorDTO;
import DTO.GenreDTO;
import DTO.MovieDTO;
import entities.Actor;
import entities.Director;
import entities.Genre;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class GeneralFacade {

    private static GeneralFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    public GeneralFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static GeneralFacade getGeneralFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GeneralFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<MovieDTO> getAllMovies() {
        EntityManager em = emf.createEntityManager();
        List<MovieDTO> listDTO = new ArrayList<>();
        try {
            List<Movie> list = em.createQuery("SELECT m FROM Movie m").getResultList();
            for (Movie movie : list) {
                listDTO.add(new MovieDTO(movie));
            }
            return listDTO;
        } finally {
            em.close();
        }
    }

    public MovieDTO getMovieById(int id) {
        EntityManager em = emf.createEntityManager();

        try {
            Movie movie = em.find(Movie.class, id);

            return new MovieDTO(movie);
        } finally {
            em.close();
        }
    }

    public List<MovieDTO> getMovieByPartTitle(String movieTitle) {
        EntityManager em = emf.createEntityManager();
        List<MovieDTO> listDTO = new ArrayList();
        try {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m WHERE m.title LIKE :movieTitle", Movie.class);

            List<Movie> movies = query.setParameter("movieTitle", "%" + movieTitle + "%").getResultList();
            for (Movie movie : movies) {
                listDTO.add(new MovieDTO(movie));
            }

            return listDTO;
        } finally {
            em.close();
        }
    }

    public List<MovieDTO> getMovieByGenre(String movieGenre) {
        EntityManager em = emf.createEntityManager();
        List<MovieDTO> listDTO = new ArrayList();
        try {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m JOIN m.genres g WHERE g.name = :movieGenre", Movie.class);

            List<Movie> movies = query.setParameter("movieGenre", movieGenre).getResultList();
            for (Movie movie : movies) {
                listDTO.add(new MovieDTO(movie));
            }

            return listDTO;
        } finally {
            em.close();
        }
    }

    public List<MovieDTO> getMovieByActor(String name) {
        EntityManager em = emf.createEntityManager();
        List<MovieDTO> listDTO = new ArrayList();
        try {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m JOIN m.actors a WHERE a.name = :movieActor", Movie.class);

            List<Movie> movies = query.setParameter("movieActor", name).getResultList();
            for (Movie movie : movies) {
                listDTO.add(new MovieDTO(movie));
            }

            return listDTO;
        } finally {
            em.close();
        }
    }

    public List<DirectorDTO> getAllDirectors() {
        EntityManager em = emf.createEntityManager();
        List<DirectorDTO> listDTO = new ArrayList<>();
        try {
            List<Director> list = em.createQuery("SELECT d FROM Director d").getResultList();
            for (Director d : list) {
                listDTO.add(new DirectorDTO(d));
            }
            return listDTO;
        } finally {
            em.close();
        }
    }

    public List<ActorDTO> getAllActors() {
        EntityManager em = emf.createEntityManager();
        List<ActorDTO> listDTO = new ArrayList<>();
        try {
            List<Actor> list = em.createQuery("SELECT a FROM Actor a").getResultList();
            for (Actor a : list) {
                listDTO.add(new ActorDTO(a));
            }
            return listDTO;
        } finally {
            em.close();
        }
    }

    public List<GenreDTO> getAllGenres() {
        EntityManager em = emf.createEntityManager();
        List<GenreDTO> listDTO = new ArrayList<>();
        try {
            List<Genre> list = em.createQuery("SELECT g FROM Genre g").getResultList();
            for (Genre g : list) {
                listDTO.add(new GenreDTO(g));
            }
            return listDTO;
        } finally {
            em.close();
        }
    }

    public MovieDTO addMovie(MovieDTO movieDTO) {
        EntityManager em = emf.createEntityManager();
        Movie m = new Movie(movieDTO.getTitle(), movieDTO.getYear());

        for (ActorDTO actor : movieDTO.getActors()) {
            Actor a = new Actor(actor.getName(), actor.getAbout());
            m.setActors(a);
        }

        for (DirectorDTO director : movieDTO.getDirectors()) {
            Director d = new Director(director.getName(), director.getAbout());
            m.setDirector(d);
        }

        for (GenreDTO genre : movieDTO.getGenres()) {
            Genre g = new Genre(genre.getName());
            m.setGenre(g);
        }

        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();

        return new MovieDTO(m);
    }

    public MovieDTO editMovie(MovieDTO movieDTO, int id) {
        EntityManager em = emf.createEntityManager();
        Movie movie = em.find(Movie.class, id);
        movie.setTitle(movieDTO.getTitle());
        movie.setYear(movieDTO.getYear());
        

        for (DirectorDTO directorDTO : movieDTO.getDirectors()) {

            for (int i = 0; i < movie.getDirectors().size() - 1; i++) {
                Director director = em.find(Director.class, movie.getDirectors().get(i).getId());
                if (!(director.equals(movie.getDirectors().get(i)))) {
                    director = new Director(directorDTO.getName(), directorDTO.getAbout());
                    movie.setDirector(director);

                }
            }
        }
        for (ActorDTO actorDTO : movieDTO.getActors()) {

            for (int i = 0; i < movie.getActors().size() - 1; i++) {
                Actor actor = em.find(Actor.class, movie.getActors().get(i).getId());
                if (!(actor.equals(movie.getActors().get(i)))) {
                    actor = new Actor(actorDTO.getName(), actorDTO.getAbout());
                    movie.setActors(actor);

                }
            }
        }
        for (GenreDTO genreDTO : movieDTO.getGenres()) {

            for (int i = 0; i < movie.getGenres().size() - 1; i++) {
                Genre genre = em.find(Genre.class, movie.getGenres().get(i).getId());
                if (!(genre.equals(movie.getGenres().get(i)))) {
                    genre = new Genre(genreDTO.getName());
                    movie.setGenre(genre);

                }
            }
        }
        try {
            em.getTransaction().begin();
            em.merge(movie);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new MovieDTO(movie);
    }
    
    
    

    public void fill() {
        EntityManager em = emf.createEntityManager();

        Movie movie1 = new Movie("Star Wars The Phantom Menace", 1999);
        Movie movie2 = new Movie("Star Wars Attack of the clones", 2002);
        Movie movie3 = new Movie("Star Wars Revenge of the Sith", 2005);
        Movie movie4 = new Movie("Star Wars A New Hope", 1977);
        Movie movie5 = new Movie("Star Wars The Empire Strikes Back", 1980);
        Movie movie6 = new Movie("Star Wars Return of the Jedi", 1983);
        Director director1 = new Director("George Lucas", "Born 14. May 1994");
        Genre genre1 = new Genre("Sci-fi");
        Genre genre2 = new Genre("Adventure");
        Actor actor1 = new Actor("Harroson Ford", "Plays Han Solo");

        movie1.setDirector(director1);
        movie1.setGenre(genre1);
        movie1.setGenre(genre2);
        movie1.setActors(actor1);
        movie2.setDirector(director1);
        movie2.setGenre(genre1);
        movie2.setGenre(genre2);
        movie2.setActors(actor1);
        movie3.setDirector(director1);
        movie3.setGenre(genre1);
        movie3.setGenre(genre2);
        movie3.setActors(actor1);
        movie4.setDirector(director1);
        movie4.setGenre(genre1);
        movie4.setGenre(genre2);
        movie4.setActors(actor1);
        movie5.setDirector(director1);
        movie5.setGenre(genre1);
        movie5.setGenre(genre2);
        movie5.setActors(actor1);
        movie6.setDirector(director1);
        movie6.setGenre(genre1);
        movie6.setGenre(genre2);
        movie6.setActors(actor1);

        Movie movie7 = new Movie("The Lego Movie", 2014);
        Director director2 = new Director("Dan Lin", "Born 8. April 1973");
        Director director3 = new Director("Roy Lee", "Born 23. March 1969");
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

        em.getTransaction().begin();
        em.persist(movie1);
        em.persist(movie2);
        em.persist(movie3);
        em.persist(movie4);
        em.persist(movie5);
        em.persist(movie6);
        em.persist(movie7);
        em.getTransaction().commit();

    }

}

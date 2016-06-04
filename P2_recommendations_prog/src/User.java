import java.util.HashSet;

/**
 * Created by Pooshan on 5/28/16.
 */
public class User {
    private int userID;

    private HashSet<Movie> movies = new HashSet<Movie>();

    public int getUserID(){
        return userID;
    }
    public User(int userID){
        this.userID = userID;
    }

    public void addMovie(Movie movie){
        movies.add(movie);
    }

    public HashSet<Movie> getMovies(){
        return movies;
    }

    @Override
    public boolean equals(Object userObject){
        if(null != userObject && userObject instanceof User){
            User inputUser = (User) userObject;

            if(inputUser.getUserID() == this.userID){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        return userID;
    }
}

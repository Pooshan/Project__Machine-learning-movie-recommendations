/**
 * Created by Pooshan on 5/28/16.
 */
public class Movie {
    int movieID;
    float rating;

    public Movie(int movieID, float rating){
        this.movieID = movieID;
        this.rating = rating;
    }

public int getMovieID(){
    return movieID;
}

public float getRating(){
    return rating;
}

    @Override
    public boolean equals(Object userObject){
        if(null != userObject && userObject instanceof Movie){
            Movie inputMovie = (Movie) userObject;

            if(inputMovie.getMovieID() == this.movieID){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        return movieID;
    }

}
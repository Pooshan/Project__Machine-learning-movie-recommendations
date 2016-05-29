import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Pooshan on 5/28/16.
 */
public class DataUtil {
    private static ArrayList<User> users = new ArrayList<User>();
    private static HashSet<Movie> movies = new HashSet<Movie>();
    private static int[][] coOccurrenceMatrix;
    private static int[] moviesList;
    private static int[] userList;
    private static float[][] weightMatrix;
    private static float c[][];

    public static User isUserAlredyPresent(User user) {
        Iterator<User> usersInterator = users.iterator();
        User userPresent = null;
        while (usersInterator.hasNext()) {
            User existingUser = usersInterator.next();
            if(existingUser.getUserID() == user.getUserID()) {
                // System.out.println("This user is already exist: " + usersInterator.next());
                userPresent = existingUser;
                break;
            }
        }
        return userPresent;
    }

    public static void updateUser(Movie movie, User user) {
        user.addMovie(movie);
    }

    public static void updateUser(HashSet<Movie> movies, User user) {
        user.getMovies().addAll(movies);
    }

    public static void addUser(User user){
        User existingUser = isUserAlredyPresent(user);
        if(null != existingUser ){
            updateUser(user.getMovies(), existingUser);
        }else {
            users.add(user);
        }
    }

    public static ArrayList<User> getUsers(){
        return users;
    }
    public static void addMovie(Movie movie){
        movies.add(movie);
    }

    public static HashSet<Movie> getMovies(){
        return movies;
    }

    public static int getTotalMovies(){
        return  movies.size();
    }

    private static void indexOfAllMovies(){
        moviesList = new int[movies.size()];
        int counter = 0;
        for(Movie movie : movies){
            moviesList[counter] = movie.getMovieID();
            counter++;
        }

    }

    private static void indexOfAllUsers(){
        userList = new int[users.size()];
        int counter = 0;
        for(User user : users){
            userList[counter] = user.getUserID();
            counter++;
        }
    }

    private static int findMoviePosition(int movieID){
        for(int i=0; i<moviesList.length;i++){
            if(moviesList[i] == movieID){
                return i;
            }
        }
        return -1;
    }

    private static int findUserPosition(int userID){
        for(int i=0; i<userList.length;i++){
            if(userList[i] == userID){
                return i;
            }
        }
        return -1;
    }

    public static void createCoOccuranceMatrix()
    {
        indexOfAllMovies();
        indexOfAllUsers();
        coOccurrenceMatrix = new int[getTotalMovies()][getTotalMovies()];
        for(User user: users){
            for(Movie movie: user.getMovies()){
                int position = findMoviePosition(movie.getMovieID());
                for (Movie moviein: user.getMovies()){
                int pos = findMoviePosition(moviein.getMovieID());
                coOccurrenceMatrix[position][pos] += 1;
                }
            }
        }
    }

    public static void printCooMatrix(){
        for (int i = 0; i < coOccurrenceMatrix[0].length; i++) {
            for (int j = 0; j < coOccurrenceMatrix[i].length;  j++) {
                System.out.print(coOccurrenceMatrix[i][j]+ ",  ");
            }
            System.out.println();
        }
    }

    public static void creatWeightMatrix(){
        indexOfAllUsers();
        indexOfAllMovies();
        weightMatrix = new float[getTotalMovies()][getUsers().size()];
        for (User user : users){
            int userPosition  = findUserPosition(user.getUserID());
            for(Movie movie: user.getMovies()){
                int moviePosition = findMoviePosition(movie.getMovieID());
                weightMatrix[moviePosition][userPosition] = movie.getRating();
            }
        }
    }

    public static void printWeightMatrix(){
        for (int i=0; i < movies.size(); i++){
            for(int j = 0; j < users.size(); j++){
                System.out.print(weightMatrix[i][j]+ ",  ");
            }
            System.out.println();
        }
    }


    public static void matrixMultiplication(){
         c = new float[getTotalMovies()][users.size()];
        for(int i = 0;i < getTotalMovies(); i++){
            for(int j = 0; j < users.size(); j++){
                c[i][j]=0;
                for(int k = 0; k < getTotalMovies(); k++){
                    c[i][j] += coOccurrenceMatrix[i][k] * weightMatrix[k][j];
                }
            }
        }
    }

    public static void printMatrixMultiplication(){
        for (int i=0; i < movies.size(); i++){
            for(int j = 0; j < users.size(); j++){
                System.out.print(c[i][j]+ ",  ");
            }
            System.out.println();
        }
    }

    public static void printFinalResult(){

        for(int i=0; i< users.size(); i++){
          System.out.print("User "+(i+1)+": ");
            float[] uArray = new float[getTotalMovies()];
            for(int j=0; j<getTotalMovies(); j++){
                System.out.print(c[j][i] + " , ");

                uArray[j] = c[j][i];
            }
            recommendMovie(uArray, users.get(i));
            System.out.println();
        }
    }

    private static void recommendMovie(float[] uArray, User user) {
        int recommendedMovie = -1;
        boolean isMovieFound = false;
        float[] newUArray = new float[uArray.length];
        for(int i=0; i<uArray.length; i++){
            newUArray[i] = uArray[i];
        }
        Arrays.sort(newUArray);
        int count = newUArray.length-1;
        float highestRatedMovie = newUArray[count];
        boolean hasAlreadySeen = false;
        while(!isMovieFound){
            int movieId = getMovieId(uArray, highestRatedMovie);
            hasAlreadySeen = false;
            for(Movie movie : user.getMovies()){
                if(movie.getMovieID() == movieId) {
                    if (count != 0) {
                        highestRatedMovie = newUArray[count - 1];
                        count --;
                        hasAlreadySeen = true;
                        break;
                    }
                }
            }
            if(!hasAlreadySeen) {
                recommendedMovie = movieId;
                isMovieFound = true;
            }
        }
        System.out.print(" Recommended => "+recommendedMovie);
    }

    private static int getMovieId(float[] uArray, float highestRatedMovie) {
        int movieId = -1;
        for(int i =0; i<uArray.length; i++){
            if(uArray[i] == highestRatedMovie){
                movieId = moviesList[i];
                break;
            }
        }
        return movieId;
    }


}
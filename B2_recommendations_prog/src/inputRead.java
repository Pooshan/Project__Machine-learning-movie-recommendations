/**
 * Created by Pooshan on 5/28/16.
 */

import java.io.File;
        import java.io.FileNotFoundException;
        import java.util.Scanner;

public class inputRead {

    public static void main(String[] args) {
        // -define .csv file in app
        String fileNameDefined = "inputdata.csv";
        // -File class needed to turn stringName to actual file
        File file = new File(fileNameDefined);

        try{
            // -read from filePooped with Scanner class
            Scanner inputStream = new Scanner(file);
            // hashNext() loops line-by-line
            while(inputStream.hasNext()){
                //read single line, put in string
                String data = inputStream.next();
                //System.out.println(data + "  ");
                User user = new User(Integer.valueOf(data.split(",")[0]));
                Movie movie = new Movie(Integer.valueOf(data.split(",")[1]), Float.valueOf(data.split(",")[2]));
                user.addMovie(movie);
                DataUtil.addMovie(movie);
                DataUtil.addUser(user);


            }

            for(User user : DataUtil.getUsers()){
                System.out.println("User ID : "+user.getUserID());
                for(Movie movie : user.getMovies()){
                    System.out.println("Movie ID : "+movie.getMovieID()+" Rating :"+movie.getRating());
                }
            }

            for(Movie movie:DataUtil.getMovies()){
                System.out.println("Movie ID : "+movie.getMovieID());
            }

            System.out.println(" \n Co-Occurance Matrix ");
            DataUtil.createCoOccuranceMatrix();
            DataUtil.printCooMatrix();

            System.out.println(" \n Weight Matrix ");
            DataUtil.creatWeightMatrix();
            DataUtil.printWeightMatrix();

            System.out.println(" \n Matrix Multiplication Result ");
            DataUtil.matrixMultiplication();
            DataUtil.printMatrixMultiplication();

            System.out.println(" \n Final Recommendation for User ");
            DataUtil.printFinalResult();
            // after loop, close scanner
            inputStream.close();


        }catch (FileNotFoundException e){

            e.printStackTrace();
        }

    }
}
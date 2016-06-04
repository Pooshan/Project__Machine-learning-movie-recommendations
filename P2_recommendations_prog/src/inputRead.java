/**
 * Created by Pooshan on 5/28/16.
 */

import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class inputRead {

    public static void main(String[] args) throws
            IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = in.readLine()) != null) {
            System.out.println(s);
            String[] inputs = s.split(",");
            if(!s.isEmpty() && inputs.length>=3) {
                    if(numberOrNot(inputs[0].trim()) && numberOrNot(inputs[1].trim()) && isValidRating(inputs[2].trim())) {
                        User user = new User(Integer.valueOf(inputs[0]));
                        Movie movie = new Movie(Integer.valueOf(inputs[1].trim()), Float.valueOf(inputs[2].trim()));
                        user.addMovie(movie);
                        DataUtil.addMovie(movie);
                        DataUtil.addUser(user);
                    }
            }
        }
        for (User user : DataUtil.getUsers()) {
            System.out.println("User ID : " + user.getUserID());
            for (Movie movie : user.getMovies()) {
                System.out.println("Movie ID : " + movie.getMovieID() + " Rating :" + movie.getRating());
            }
        }

        for (Movie movie : DataUtil.getMovies()) {
            System.out.println("Movie ID : " + movie.getMovieID());
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
        //inputStream.close();

    }

    static boolean numberOrNot(String s)
    {
        try
        {
            Integer.parseInt(s);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
        return true;
    }

    static boolean isValidRating(String s)
    {
        boolean isValid = false;
        try
        {
            float rating = Float.parseFloat(s);
            if(rating >= 0.0 && rating<=5.0){
                isValid = true;
            }
        }
        catch(NumberFormatException ex)
        {
            isValid = false;
        }
        return isValid;
    }
}
package com.projects.MovieRecomondationSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MovieMain {

    private static boolean authenticateAdmin(Connection conn, String enteredUsername, String enteredPassword) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM admin_credentials WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, enteredUsername);
            pstmt.setString(2, enteredPassword);
            rs = pstmt.executeQuery();

            return rs.next(); // Returns true if there is a match
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Connection conn = null;

        conn = MovieDBConnection.createC();

        // Admin Authentication
        boolean isAuthenticated = false;
        do {
            System.out.print("Enter admin username: ");
            String enteredUsername = br.readLine();

            System.out.print("Enter admin password: ");
            String enteredPassword = br.readLine();

            isAuthenticated = authenticateAdmin(conn, enteredUsername, enteredPassword);

            if (!isAuthenticated) {
                System.out.println("Invalid admin credentials. Please try again.");
            }

        } while (!isAuthenticated);

        System.out.println("****** Welcome to Movie World ****** ");

        while (true) {
            System.out.println("PRESS 1 to ADD a Movie");
            System.out.println("PRESS 2 to UPDATE a Movie details");
            System.out.println("PRESS 3 to DEACTIVATE a Movie");
            System.out.println("PRESS 4 to DISPLAY all Movie");
            System.out.println("PRESS 5 to DISPLAY Movie by Id");
            System.out.println("PRESS 6 to EXIT");

            int c = Integer.parseInt(br.readLine());

            if (c == 1) {
                // 1. Add Movie..
                System.out.println("Enter Title:");
                String title = br.readLine();

                System.out.println("Enter Genre:");
                String genre = br.readLine();

                System.out.println("Enter Year Of Release:");
                int yearOfRelease = Integer.parseInt(br.readLine());

                // Creating object of Movie class
                Movie obj = new Movie(title, genre, yearOfRelease);

                // Calling method to insert into table and passing the object of Movie class
                boolean result = MovieDAO.insert(obj);
                if (result) {
                    System.out.println(" The movie is successfully added.");
                    System.out.println("To continue follow the steps...");
                } else {
                    System.out.println("Something went wrong.");
                }
            } 
            else if (c == 2) {
                // 2. Update movie..
            	System.out.println("Enter Id to which update:");
                int sid = Integer.parseInt(br.readLine());
                
                System.out.println("Enter Title:");
                String title = br.readLine();

                System.out.println("Enter Genre:");
                String genre = br.readLine();

                System.out.println("Enter Year Of Release:");
                int yearOfRelease = Integer.parseInt(br.readLine());

                // Creating object of Movie class
                Movie obj = new Movie(title, genre, yearOfRelease);

                // Calling of method to update table and pass the obj of Movie class
                boolean result = MovieDAO.update(obj, sid);
                if (result) {
                    System.out.println("The movie is successfully updated.");
                } else {
                    System.out.println("Something went wrong.");
                }
            } 
            else if (c == 3) {
                // 3. Delete movie..
                System.out.println("Enter Movie id to deactivate:");
                int id = Integer.parseInt(br.readLine());

                // Calling method to delete a particular row
                boolean result = MovieDAO.deactivate(id);
                if (result) {
                    System.out.println("The movie is successfully deactivated.");
                } else {
                    System.out.println("Something went wrong.");
                }
            } 
            else if (c == 4) {
                // 4. Display movie..
                // Calling method getAllMovies to display the table values
                List<Movie> movies = MovieDAO.getAllMovies();
                for (Movie movie : movies) {
                    System.out.println(movie);
                }
            }

            else if (c == 5) {
                // 5. Display movie by Id..
                System.out.println("Enter Movie id to get data:");
                int id = Integer.parseInt(br.readLine());
                Movie movie = MovieDAO.getById(id);
                if (movie != null) {
                    System.out.println(movie);
                } else {
                    System.out.println("Movie with ID " + id + " not found.");
                }
            }
            else if (c == 6) {
                // 5. Exit
                break;
            } 
            else {
                System.out.println("Wrong input!");
            }
        }
        System.out.println("Goodbye!");
        // Close the connection when done
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

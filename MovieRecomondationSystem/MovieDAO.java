package com.projects.MovieRecomondationSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
	static PreparedStatement pstmt = null;
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	// Insert method handles auto-increment
	public static boolean insert(Movie obj) {
		boolean f = false;
		try {
			conn = MovieDBConnection.createC();

			String q = "INSERT INTO MOVIES(Title, Genre, YearOfRelease) VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, obj.getTitle());
			pstmt.setString(2, obj.getGenre());
			pstmt.setInt(3, obj.getYearOfRelease());

			pstmt.executeUpdate();

			// Retrieve the auto-generated key (ID)
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				obj.setId(rs.getInt(1));
			}

			f = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			closeResources();
		}
		return f;
	}

	// Update Movie
	public static boolean update(Movie obj, int sid) {
		boolean f = false;
		try {
			//this will create Connection
			 conn = MovieDBConnection.createC();
		
			//Use connection to fire queries
			String q = "UPDATE MOVIES SET Title = ? ,  Genre = ?,  YearOfRelease = ? WHERE ID = ?";
			
			//PreparedStatement
			pstmt = conn.prepareStatement(q);
			
			//Set the value for parameter
			pstmt.setString(1, obj.getTitle());
			pstmt.setString(2, obj.getGenre());
			pstmt.setInt(3, obj.getYearOfRelease());
			pstmt.setInt(4, sid);
			
			
			//Execute
			pstmt.executeUpdate();
			f = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			closeResources();
		}
		return f;
	}

	// Deactivate Method
	public static boolean deactivate(int id) {
	    boolean f = false;
	    try {
	        conn = MovieDBConnection.createC();

	        // Deactivate the selected movie
	        String qDeactivate = "UPDATE MOVIES SET is_active = false WHERE ID=?";
	        pstmt = conn.prepareStatement(qDeactivate);
	        pstmt.setInt(1, id);
	        int rows = pstmt.executeUpdate();

	        if (rows > 0) {
	            f = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeResources();
	    }
	    return f;
	}


	// Movie by Id
	public static Movie getById(int id) {
		Movie movie = null;
		try {
			conn = MovieDBConnection.createC();

			String q = "SELECT * FROM MOVIES WHERE ID=?";
			pstmt = conn.prepareStatement(q);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				movie = new Movie();
				movie.setId(rs.getInt("ID"));
				movie.setTitle(rs.getString("Title"));
				movie.setGenre(rs.getString("Genre"));
				movie.setYearOfRelease(rs.getInt("YearOfRelease"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return movie;
	}

	public static List<Movie> getAllMovies() {
		List<Movie> movies = new ArrayList<>();
		try {
			conn = MovieDBConnection.createC();

			String q = "SELECT * FROM MOVIES";
			stmt = conn.createStatement();

			rs = stmt.executeQuery(q);

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setId(rs.getInt("ID"));
				movie.setTitle(rs.getString("Title"));
				movie.setGenre(rs.getString("Genre"));
				movie.setYearOfRelease(rs.getInt("YearOfRelease"));
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return movies;
	}

	// Close resources method
	private static void closeResources() {
		try {
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

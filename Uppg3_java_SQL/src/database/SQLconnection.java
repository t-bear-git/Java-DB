package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mrBean.MovieBean;

public class SQLconnection {

	// set up the connection as static
	static Connection conn = null;
	static PreparedStatement stmt = null;
	static ResultSet rs = null;

	public static boolean connectSQL() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			System.out.println("Exception Driver: " + ex);
			return false;
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movies?serverTimezone=UTC",
					DatabaseLogin.getuName(), DatabaseLogin.getuPass());
			return true;

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}

	}

	// Method to find all actor in specific movie
	public static ArrayList<MovieBean> getActors(String movieTitle, ArrayList<MovieBean> movies) {

		try {
			String requestQuery = "SELECT " + "act_fname, " + "act_lname " + "FROM actors "
					+ "JOIN movie_cast ON actors.act_id = movie_cast.act_id "
					+ "JOIN movies ON movie_cast.mov_id = movies.mov_id AND movies.mov_title = ?";

			stmt = conn.prepareStatement(requestQuery);
			stmt.setString(1, movieTitle);
			rs = stmt.executeQuery();

			while (rs.next()) {
				// set up the bean
				MovieBean movie = new MovieBean();
				// add the actors first name
				movie.setActorFname(rs.getString("act_fname"));
				// add the actors last name
				movie.setActorLname(rs.getString("act_lname"));
				// add bean into arraylist
				movies.add(movie);
			}

			rs.close();
			conn.endRequest();
			// always make sure to close this stuff

			// return the results after the ResultSet is done and closed
			return movies;

		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());

		}
		return null;

	}

	// Method to find movie that has won specific award
	public static ArrayList<MovieBean> getAwardWinner(String award, ArrayList<MovieBean> beans) {

		try {
			String requestQuery = "SELECT " + "movies.mov_title " + "FROM movies "
					+ "JOIN movie_awards ON movies.mov_id = movie_awards.mov_id "
					+ "JOIN awards ON movie_awards.aw_id = awards.aw_id AND awards.aw_name = ?";

			stmt = conn.prepareStatement(requestQuery);
			stmt.setString(1, award);
			rs = stmt.executeQuery();

			while (rs.next()) {
				MovieBean movieBean = new MovieBean();
				movieBean.setMovieTitle(rs.getString("mov_title"));
				beans.add(movieBean);
			}

			rs.close();
			conn.endRequest();

			return beans;

		} catch (SQLException ex) {
			System.out.println("getAwardWinner()");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return null;
	}

	// Method to get movies cast by a specific actor
	public static ArrayList<MovieBean> getActorMovies(String actor, ArrayList<MovieBean> beans) {

		try {
			String requestQuery = "SELECT " + "movies.mov_title " + "FROM " + "movies "
					+ "JOIN movie_cast ON movies.mov_id = movie_cast.mov_id "
					+ "JOIN actors ON movie_cast.act_id = actors.act_id AND actors.act_fname = ?";

			stmt = conn.prepareStatement(requestQuery);
			stmt.setString(1, actor);
			rs = stmt.executeQuery();

			while (rs.next()) {
				MovieBean movieBean = new MovieBean();
				movieBean.setMovieTitle(rs.getString("mov_title"));
				beans.add(movieBean);
			}

			rs.close();
			conn.endRequest();

			return beans;

		} catch (SQLException ex) {
			System.out.println("Error in getActorMovies()");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return null;
	}

	// Method to get awards won by a specific actor
	public static ArrayList<MovieBean> getActorAwards(String actor, ArrayList<MovieBean> beans) {

		try {
			String requestQuery = "SELECT " + "movies.mov_title, " + "awards.aw_name " + "FROM " + "awards "
					+ "JOIN movie_awards ON awards.aw_id = movie_awards.aw_id "
					+ "JOIN movie_cast ON movie_awards.mov_id = movie_cast.mov_id "
					+ "JOIN movies ON movie_cast.mov_id = movies.mov_id "
					+ "JOIN actors ON movie_cast.act_id = actors.act_id AND actors.act_fname = ?";

			stmt = conn.prepareStatement(requestQuery);
			stmt.setString(1, actor);
			rs = stmt.executeQuery();

			while (rs.next()) {
				MovieBean movieBean = new MovieBean();
				movieBean.setMovieTitle(rs.getString("mov_title"));
				movieBean.setAward(rs.getString("aw_name"));
				beans.add(movieBean);
			}

			rs.close();
			conn.endRequest();
			conn.close();
			return beans;

		} catch (SQLException ex) {
			System.out.println("Error in getActorAwards()");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return null;
	}
}
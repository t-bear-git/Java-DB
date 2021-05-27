package main;

import java.util.ArrayList;

import database.SQLconnection;
import mrBean.MovieBean;

public class SQL_Start {

	public static void main(String[] args) {

		if (SQLconnection.connectSQL()) {

			// Print actors in the movie Seven
			String movieName = "Seven";
			ArrayList<MovieBean> movieBeans = new ArrayList<MovieBean>();
			SQLconnection.getActors(movieName, movieBeans);

			System.out.println("#1. Actors in the movie Seven: ");
			System.out.println("");

			for (int i = 0; i < movieBeans.size(); i++) {
				System.out.print(movieBeans.get(i).getActorFname());
				System.out.print(" ");
				System.out.print(movieBeans.get(i).getActorLname());
				System.out.println("");
			}

			// Print names of movies that has won a Golden Globe
			String award = "Golden Globe";
			ArrayList<MovieBean> awardBeans = new ArrayList<MovieBean>();
			SQLconnection.getAwardWinner(award, awardBeans);

			System.out.println("");
			System.out.println("#2. Movies that has won a Golden Globe award: ");
			System.out.println("");

			for (int i = 0; i < awardBeans.size(); i++) {
				System.out.print(awardBeans.get(i).getMovieTitle());
				System.out.println("");
			}

			// Print names of movies with the actor Brad Pitt
			String actor = "Brad";
			ArrayList<MovieBean> actorBeans = new ArrayList<MovieBean>();
			SQLconnection.getActorMovies(actor, actorBeans);

			System.out.println("");
			System.out.println("#3. Names of movies with Brad Pitt cast in them: ");
			System.out.println("");

			for (int i = 0; i < actorBeans.size(); i++) {
				System.out.print(actorBeans.get(i).getMovieTitle());
				System.out.println("");
			}

			// Print awards won by movies with Brad Pitt casted in them
			actor = "Brad";
			ArrayList<MovieBean> movieAwardBeans = new ArrayList<MovieBean>();
			SQLconnection.getActorAwards(actor, movieAwardBeans);

			System.out.println("");
			System.out.println("#4. Awards won by movies with Brad Pitt casted in them: ");
			System.out.println("");

			for (int i = 0; i < movieAwardBeans.size(); i++) {
				System.out.print(movieAwardBeans.get(i).getMovieTitle());
				System.out.print(" - ");
				System.out.print(movieAwardBeans.get(i).getAward());
				System.out.println("");
			}

		}

	}

}

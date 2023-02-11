import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        ArrayList<String> uniqueCastMembers = new ArrayList<String>();
        for (Movie movie : movies) {
            String[] castMembers = movie.getCast().split("\\|");
            for (String castMember : castMembers) {
                if (!uniqueCastMembers.contains(castMember)) {
                    uniqueCastMembers.add(castMember);
                }
            }
        }

        System.out.print("Enter a cast member search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        ArrayList<String> results = new ArrayList<String>();
        for (String castMember : uniqueCastMembers) {
            if (castMember.toLowerCase().contains(searchTerm)) {
                results.add(castMember);
            }
        }

        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

        if (results.size() == 0) {
            System.out.println("No results found.");
            return;
        }

        System.out.print("Enter the number of the cast member: ");
        int castMemberNum = scanner.nextInt() - 1;
        String castMember = results.get(castMemberNum);

        ArrayList<String> movieTitles = new ArrayList<String>();
        ArrayList<Movie> castMovies = new ArrayList<Movie>();
        for (Movie movie : movies) {
            String[] castMembers = movie.getCast().split("\\|");
            for (String cast : castMembers) {
                if (cast.equals(castMember)) {
                    movieTitles.add(movie.getTitle());
                    castMovies.add(movie);
                    break;
                }
            }
        }

        if (movieTitles.size() == 0) {
            System.out.println("No results found.");
            return;
        }

        for (int i = 0; i < movieTitles.size(); i++) {
            System.out.println((i + 1) + ". " + movieTitles.get(i));
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice2 = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = castMovies.get(choice2 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }
    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String keyWord = scanner.nextLine();

        // prevent case sensitivity
        keyWord = keyWord.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getKeywords();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(keyWord) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres() {
        ArrayList<String> uniqueGenres = new ArrayList<String>();
        for (Movie movie : movies) {
            String[] genres = movie.getGenres().split("\\|");
            for (String genre : genres) {
                if (!uniqueGenres.contains(genre)) {
                    uniqueGenres.add(genre);
                }
            }
        }

        for (int i = 0; i < uniqueGenres.size(); i++) {
            String currentMin = uniqueGenres.get(i);
            int minIndex = i;
            for (int j = i + 1; j < uniqueGenres.size(); j++) {
                if (uniqueGenres.get(j).compareTo(currentMin) < 0) {
                    currentMin = uniqueGenres.get(j);
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                uniqueGenres.set(minIndex, uniqueGenres.get(i));
                uniqueGenres.set(i, currentMin);
            }
        }

        if (uniqueGenres.size() == 0) {
            System.out.println("No results found.");
            return;
        }

        for (int i = 0; i < uniqueGenres.size(); i++) {
            System.out.println((i + 1) + ". " + uniqueGenres.get(i));
        }

        System.out.print("Choose a genre: ");
        int genreNum = scanner.nextInt() - 1;
        scanner.nextLine();
        String genre = uniqueGenres.get(genreNum);

        ArrayList<String> results = new ArrayList<String>();
        ArrayList<Movie> genreMovie = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(genre)) {
                results.add(movie.getTitle());
                genreMovie.add(movie);
            }
        }

        for (int i = 0; i < results.size(); i++) {
            String currentMin = results.get(i);
            int minIndex = i;
            for (int j = i + 1; j < results.size(); j++) {
                if (results.get(j).compareTo(currentMin) < 0) {
                    currentMin = results.get(j);
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                results.set(minIndex, results.get(i));
                results.set(i, currentMin);
            }
        }

        if (results.size() == 0) {
            System.out.println("No results found.");
            return;
        }

        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }


        System.out.print("Choose a title: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = genreMovie.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated() {
        ArrayList<Movie> highestRatedMovies = new ArrayList<Movie>();
        for (Movie movie : movies) {
            int index = 0;
            while (index < highestRatedMovies.size()) {
                if (movie.getUserRating() > highestRatedMovies.get(index).getUserRating())
                {
                   break;
                }
                index++;
            }
            highestRatedMovies.add(index, movie);
            if (highestRatedMovies.size() > 50) {
                highestRatedMovies.remove(50);
            }
        }

        if (highestRatedMovies.size() == 0) {
            System.out.println("No results found.");
            return;
        }

        for (int i = 0; i < highestRatedMovies.size(); i++) {
            System.out.println((i + 1) + ". " + highestRatedMovies.get(i).getTitle() + " - " + highestRatedMovies.get(i).getUserRating());
        }

        System.out.print("Choose a title: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = highestRatedMovies.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> highestRevenueMovies = new ArrayList<Movie>();
        for (Movie movie : movies) {
            int index = 0;
            while (index < highestRevenueMovies.size()) {
                if (!(movie.getRevenue() > highestRevenueMovies.get(index).getRevenue()))
                {
                    index++;
                }
            }
            highestRevenueMovies.add(index, movie);
            if (highestRevenueMovies.size() > 50) {
                highestRevenueMovies.remove(50);
            }
        }

        if (highestRevenueMovies.size() == 0) {
            System.out.println("No results found.");
            return;
        }

        for (int i = 0; i < highestRevenueMovies.size(); i++) {
            System.out.println((i + 1) + ". " + highestRevenueMovies.get(i).getTitle() + " - " + highestRevenueMovies.get(i).getRevenue());
        }

        System.out.print("Choose a title: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = highestRevenueMovies.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}

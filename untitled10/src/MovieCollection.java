import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                results.add(movies.get(i));
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

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

    private void searchCast() {
        System.out.print("Enter a cast member search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        ArrayList<String> uniqueCastMembers = new ArrayList<>();

        for (Movie movie : movies) {
            String[] castMembers = movie.getCast().split("\\|");
            for (String castMember : castMembers) {
                castMember = castMember.trim().toLowerCase();
                if (castMember.contains(searchTerm) && !uniqueCastMembers.contains(castMember)) {
                    uniqueCastMembers.add(castMember);
                }
            }
        }

        sortList(uniqueCastMembers);

        for (int i = 0; i < uniqueCastMembers.size(); i++) {
            System.out.println((i + 1) + ". " + uniqueCastMembers.get(i));
        }

        System.out.println("Select a cast member by number:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > uniqueCastMembers.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        String selectedCastMember = uniqueCastMembers.get(choice - 1);
        ArrayList<Movie> castMovies = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getCast().toLowerCase().contains(selectedCastMember)) {
                castMovies.add(movie);
            }
        }


        sortMoviesByTitle(castMovies);


        System.out.println("Movies featuring " + selectedCastMember + ":");
        for (int i = 0; i < castMovies.size(); i++) {
            System.out.println((i + 1) + ". " + castMovies.get(i).getTitle());
        }

    }

    private void sortList(ArrayList<String> list) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                    String temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    swapped = true;
                }
            }
        } while (swapped);
    }

    private void sortMoviesByTitle(ArrayList<Movie> movies) {

        for (int j = 1; j < movies.size(); j++) {
            Movie key = movies.get(j);
            int i = j - 1;
            while (i >= 0 && movies.get(i).getTitle().compareTo(key.getTitle()) > 0) {
                movies.set(i + 1, movies.get(i));
                i--;
            }
            movies.set(i + 1, key);
        }
    }




    private void searchKeywords() {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (Movie movie : movies) {
            String movieKeywords = movie.getKeywords().toLowerCase();

            if (movieKeywords.contains(searchTerm)) {
                results.add(movie);
            }
        }
        sortResults(results);

        if (results.isEmpty()) {
            System.out.println("No movies found with the keyword: " + searchTerm);
        } else {
            for (int i = 0; i < results.size(); i++) {
                String title = results.get(i).getTitle();
                int choiceNum = i + 1;
                System.out.println("" + choiceNum + ". " + title);
            }

            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= results.size()) {
                    Movie selectedMovie = results.get(choice - 1);
                    displayMovieInfo(selectedMovie);
                } else {
                    System.out.println("Invalid choice. Please select a number from the list.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }


            System.out.println("\n** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
    }


    private void listGenres() {
        Set<String> uniqueGenres = new TreeSet<>();
        for (Movie movie : movies) {
            String[] genres = movie.getGenres().split("\\|");
            for (String genre : genres) {
                uniqueGenres.add(genre.trim());
            }
        }


        int index = 1;
        for (String genre : uniqueGenres) {
            System.out.println(index + ". " + genre);
            index++;
        }

        System.out.print("Select a genre by number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();


        String selectedGenre = (String) uniqueGenres.toArray()[choice - 1];

        ArrayList<Movie> genreMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (Arrays.asList(movie.getGenres().split("\\|")).contains(selectedGenre)) {
                genreMovies.add(movie);
            }
        }

        genreMovies.sort(Comparator.comparing(Movie::getTitle));

        System.out.println("Movies in the " + selectedGenre + " genre:");
        for (int i = 0; i < genreMovies.size(); i++) {
            System.out.println((i + 1) + ". " + genreMovies.get(i).getTitle());
        }

        System.out.print("Select a movie by number to learn more: ");
        int movieChoice = scanner.nextInt();
        scanner.nextLine();

        if (movieChoice >= 1 && movieChoice <= genreMovies.size()) {
            Movie selectedMovie = genreMovies.get(movieChoice - 1);
            displayMovieInfo(selectedMovie);
        } else {
            System.out.println("Invalid choice.");
        }

        System.out.println("\n** Press enter to exit back to the main menu**");
        scanner.nextLine();

    }

    private void listHighestRated() {
        movies.sort((movie1, movie2) -> Double.compare(movie2.getUserRating(), movie1.getUserRating()));


        for (int i = 0; i < Math.min(movies.size(), 50); i++) {
            System.out.println((i + 1) + ". " + movies.get(i).getTitle() + ": " + movies.get(i).getUserRating());
        }


        System.out.print("Select a movie by number to learn more: ");
        int choice = scanner.nextInt();
        scanner.nextLine();



        if (choice >= 1 && choice <= Math.min(movies.size(), 50)) {
            Movie selectedMovie = movies.get(choice - 1);
            displayMovieInfo(selectedMovie);
        } else {
            System.out.println("Invalid choice.");
        }

        System.out.println("\n** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void listHighestRevenue() {

        movies.sort((movie1, movie2) -> Integer.compare(movie2.getRevenue(), movie1.getRevenue()));


        for (int i = 0; i < Math.min(movies.size(), 50); i++) {
            System.out.println((i + 1) + ". " + movies.get(i).getTitle() + ": $" + movies.get(i).getRevenue());
        }


        System.out.print("Select a movie by number to learn more: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 1 && choice <= Math.min(movies.size(), 50)) {
            Movie selectedMovie = movies.get(choice - 1);
            displayMovieInfo(selectedMovie);
        } else {
            System.out.println("Invalid choice.");
        }

        System.out.println("\n** Press Enter to Return to Main Menu **");
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
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
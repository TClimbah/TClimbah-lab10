import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.IOException;

public class WordCounter {
    public static int wordCount;

    public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText {
        String index = new String(text);
        if(index.contains(stopword)) {
            String newString = index.substring(0, index.indexOf(stopword));
            String[] newerString = newString.split(" ");
            wordCount = newerString.length;
        }
        else if(stopword == null) {
            String[] newerString = index.split(" ");
            wordCount = newerString.length;
        }
        else {
            throw new InvalidStopwordException("couldnt find the stop word in StringBuffer");
        }
        if (wordCount < 5) {
            throw new TooSmallText("Not enough words, need 5 minimum");
        }
        else {
            return wordCount;
        }
    }
    public static StringBuffer processFile(String path) throws EmptyFileException {
        Scanner scraper = new Scanner(System.in);
        StringBuffer str = new StringBuffer();
        int i = 0;
        while (i == 0) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                String parser;
                int empty = 0;
                while((parser = reader.readLine()) != null) {
                    str.append(parser).append("\n");
                    empty++;
                }
                reader.close();
                if (empty == 0) {
                    throw new EmptyFileException(path);
                }
                i++;
                scraper.close();
                return str;
            }
            catch (FileNotFoundException e){
            System.out.println("File not found. Please re-enter the file path: ");
            path = scraper.nextLine();
            str = new StringBuffer();
            }
            catch (IOException e){
                System.out.println("Error reading file");
            }
        }
        return str;
    }
    public static void main(String[] args) {
        Scanner scraper = new Scanner(System.in);
        int i = 0;
        // Argument 1
        if (args.length > 0) {
            try {
                i = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                i = 0;
            }
        }
        while(i != 1 && i != 2) {
            System.out.println("Choose wheather you want to process a file (1), or process a text(2)");
            i = scraper.nextInt();
        }
        // Argument 2
        String stopWord = "";
        if (args.length > 1) {
            stopWord = args[1];
        }
        if (stopWord.isEmpty()) {
            System.out.println("Choose a stop word: ");
            stopWord = scraper.nextLine();
        }
        StringBuffer text = new StringBuffer();
        if (i == 1) { // processing the file
            System.out.println("Enter file path: ");
            String path = scraper.nextLine();
            try {
                text = processFile(path);
            }
            catch (EmptyFileException e) {
                text = new StringBuffer("");
            }
        }
        else if (i == 2) { // processing text
            System.out.println("Enter your text: ");
            text.append(scraper.nextLine());
        }
        boolean stopwordFound = text.toString().contains(stopWord);
        if (!stopwordFound) {
            System.out.println("You have one more chance to input a stopword");
            stopWord = scraper.nextLine();
            stopwordFound = text.toString().contains(stopWord);
        }
        try {
        int words = processText(text, stopWord);
        }
        catch (TooSmallText e) {
            System.out.println("Not enough words.");
        }
        catch (InvalidStopwordException e) {
            System.out.println("Stopword is not found.");
        }
    }
}

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class WordCounter {

    public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText {
        String newText = text.toString();
        int wordCount = 0;
        if (stopword != null && !stopword.isEmpty()) {
            wordCount++;
            int index = newText.indexOf(stopword);
            if (index == -1) {
                throw new InvalidStopwordException("Couldn't find stopword: " + stopword);
            }
            newText = newText.substring(0, index);
        }
        Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
        Matcher matcher = regex.matcher(newText);
        while (matcher.find()) {
            wordCount++;
        }
        if (wordCount < 5) {
           throw new TooSmallText("Only found " + wordCount + " words.");
        }
        else {
            return wordCount;
        }
    }
    public static StringBuffer processFile(String path) throws EmptyFileException {
        Scanner scraper = new Scanner(System.in);
        StringBuffer str = new StringBuffer();
        while (true) {
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
            System.out.println("Choose whether you want to process a file (1), or process a text(2)");
            i = scraper.nextInt();
            scraper.nextLine();
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
        System.out.println("Found " + words + " words.");
        }
        catch (TooSmallText e) {
            System.out.println("Not enough words.");
        }
        catch (InvalidStopwordException e) {
            System.out.println("Stopword is not found.");
        }
    }
}

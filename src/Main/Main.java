package Main;

import Objects.Word;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Text file initialization
        String file = "src/Resources/sample.txt";

        //Array of every word in the text (in order)
        ArrayList<String> words = new ArrayList<>();

        //Places every word inside the array
        try(Scanner current = new Scanner(new File(file))){
            while(current.hasNext()){
                words.add(current.next());
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File " + file + " not found");
        }

        //Stats
        System.out.println("Upload complete. You have " + words.size() + " words in your databank.");

        //Creates an array with every word and potential follow-up words
        ArrayList<Word> wordBank = new ArrayList<>();
        for(int i = 0; i < words.size() - 1; i++){
            String current = words.get(i);
            String next = words.get(i+1);

            //Checks if the word was already instantiated before making a new instance
            if(!wordBank.stream().anyMatch(Word -> current.equals(Word.getCurrent()))){
                wordBank.add(new Word(current));
            }

            //Adds follow-up word to the "next" list of the current word
            Word corresponding = null;
            for(Word j: wordBank){
                if(j.getCurrent().equals(current)){
                    corresponding = j;
                }
            }
            corresponding.addNext(next);
        }

        //Interface
        String choice = null;
        int num = 0;
        int init = 0;
        String word = null;
        String sentence = "";
        Scanner in = new Scanner(System.in);
        Random random = new Random();

        while(!"x".equals(choice)){
            System.out.println("g: Generate a sentence | x: Exit");
            choice = in.nextLine();
            switch(choice){
                case "g":
                    System.out.println("What length do you wish your sentence to be?");
                    num = Integer.parseInt(in.nextLine());

                    System.out.println("Generating a sentence with " + num + " words.");

                    sentence = "";
                    word = null;

                    //Initializes the first word
                    init = random.nextInt(wordBank.size());
                    word = wordBank.get(init).getCurrent();
                    sentence = word + " ";

                    //Chooses the next word based on the current one
                    for(int i = 1; i < num; i++){
                        Word current = null;

                        //Finding the right word
                        for(Word search : wordBank){
                            if(search.getCurrent().equals(word)){
                                current = search;
                                break;
                            }
                        }
                        if(word == null || current == null || current.getNext().isEmpty()){
                            init = random.nextInt(wordBank.size());
                            word = String.valueOf(wordBank.get(init).getCurrent());

                        }
                        else{
                            word = current.getNext().get(random.nextInt(current.getNext().size())).toString();
                        }
                        sentence += word + " ";
                    }
                    System.out.println("Here is your sentence:");
                    System.out.println(sentence);
                    break;

                case "x":
                    System.out.println("Closing");
                    choice = "x";
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
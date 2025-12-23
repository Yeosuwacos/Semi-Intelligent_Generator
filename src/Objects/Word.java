package Objects;

import java.util.ArrayList;

public class Word {

    private String current;
    private ArrayList<String> next;

    //Creates a Word object which contains the current and next word
    public Word(String current){
        this.current = current;
        next = new ArrayList<>();
    }

    //Adds a potential next word in the list
    public void addNext(String next){
        this.next.add(next);
    }

    //Getters
    public String getCurrent() {
        return current;
    }

    public ArrayList<String> getNext() {
        return next;
    }
}

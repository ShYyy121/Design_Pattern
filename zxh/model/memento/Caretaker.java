package model.memento;

import java.util.ArrayList;

public class Caretaker {

    private static ArrayList<Memento> mementos = new ArrayList<>();

    public static Memento getRecall() {
        if(mementos.size() > 0){
            return mementos.remove(mementos.size() - 1);
        }
        return null;
    }
    public static Memento getStudent(){
        if (mementos.size()>0) {
            return mementos.get(mementos.size() - 1);
        }
        else {
            return mementos.get(mementos.size());
        }
    }
    public static void add(Memento memento) {
        mementos.add(memento);
    }
}


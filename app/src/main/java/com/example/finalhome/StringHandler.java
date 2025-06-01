package com.example.finalhome;
import java.util.ArrayList;

public class StringHandler {

    private ArrayList<String> stringList;

    public StringHandler() {
        stringList = new ArrayList<>();
    }

    public void addStringsToList(String string1, String string2) {
        if (string1 != null && string2 != null) {
            stringList.add(string1);
            stringList.add(string2);
        } else {
            System.out.println("One or both strings are null and cannot be added.");
        }
    }

    public ArrayList<String> getStringList() {
        return stringList;
    }
}

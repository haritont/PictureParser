package org.example;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.setUrl("https://www.google.ru/");
        parser.setText("кися");
        parser.Start();
        parser.End();
    }
}
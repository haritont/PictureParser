package org.example;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.setUrl("https://www.google.ru/?&bih=754&biw=1536&hl=ru");
        parser.setText("рецепты");
        parser.Start();
        parser.End();
    }
}
package org.example;

public class Main {
    public static void main(String[] args) {
       Server s = new Server();
       Client c1 = new Client(s);
       Client c2 = new Client(s);
       s.addActive(c1);
       s.addActive(c2);
    }
}
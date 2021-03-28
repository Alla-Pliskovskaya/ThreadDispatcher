package main;

import p1.ThreadDispatcher;

public class Main
{
    public static void main(String[] args) {
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance();
        dispatcher.setMaxPoolSize(3);
    }
}


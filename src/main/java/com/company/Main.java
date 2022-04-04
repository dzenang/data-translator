package com.company;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Parser p = ParserFactory.createParser(args[0], args[1]);
        p.transform();

    }
}

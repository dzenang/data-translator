package com.company;

public class ParserFactory {
    public static Parser createParser(String intputType, String outputType) {
        Parser parser;
        switch (intputType) {
            case "csv" :
                parser = new CsvParser(Parser.OutputType.fromString(outputType));
                break;
            case "prn" :
                parser = new PrnParser(Parser.OutputType.fromString(outputType));
                break;
            default:
                parser = null;
                break;
        }
        return parser;
    }
}

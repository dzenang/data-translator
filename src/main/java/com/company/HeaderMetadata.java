package com.company;

import java.util.function.Function;

public class HeaderMetadata {
    private int from;
    private int to;
    private Function<String, String> formatData;

    public HeaderMetadata(int from, int to, Function<String, String> formatData) {
        this.from = from;
        this.to = to;
        this.formatData = formatData;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Function<String, String> getFormatData() {
        return formatData;
    }
}

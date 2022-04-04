package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Parser {
    List<Map<String, String>> output = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();
    OutputType outputType;

    abstract void parse();
    abstract Function<String, String> formatDate();
    abstract Function<String, String> formatNumber();

    private void writeJson() throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(System.out, output);
    }

    private void writeHtml() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>");
        sb.append("\n<table>");
        boolean setHeaders = false;
        for (Map<String, String> map : output) {
            sb.append("\n<tr>\n");
            if(!setHeaders) {
                sb.append(map.keySet().stream().collect(Collectors.joining("</th><th>", "<th>", "</th>")));
                setHeaders = true;
            } else {
                sb.append(map.values().stream().collect(Collectors.joining("</td><td>", "<th>", "</th>")));
            }
            sb.append("\n</tr>");

        }
        sb.append("\n</table>");
        sb.append("\n</body></html>");
        // output result
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8"))){
            bw.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transform() throws IOException {
        parse();
        //transform
        if(outputType.equals(OutputType.JSON)) {
            writeJson();
        } else {
            writeHtml();
        }
    }
    enum OutputType {
        HTML("html"),
        JSON("json");
        private String type;
        OutputType(String type) {
            this.type = type;
        }
        static OutputType fromString(String type) {
            return Arrays.stream(values()).filter(e -> e.type.equalsIgnoreCase(type)).findFirst().orElse(null);
        }
    }
}

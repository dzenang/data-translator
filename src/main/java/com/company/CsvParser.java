package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;

public class CsvParser extends Parser {
    public CsvParser(OutputType outputType) {
        this.outputType = outputType;
    }
    @Override
    void parse() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            List<String> headers = new ArrayList<>();
            String line;
            boolean gotHeaders = false;
            while ((line = br.readLine()) != null) {
                Map<String, String> obj = new LinkedHashMap<>();
                if (!gotHeaders) {
                    headers.addAll(extractData(line));
                    gotHeaders = true;
                } else {
                    List<String> value = extractData(line);
                    for (int i = 0; i < headers.size(); i++) {
                        switch (headers.get(i)) {
                            case "Credit Limit":
                                obj.put(headers.get(i), this.formatNumber().apply(value.get(i)));
                                break;
                            case "Birthday":
                                obj.put(headers.get(i), this.formatDate().apply(value.get(i)));
                                break;
                            default:
                                obj.put(headers.get(i), value.get(i));
                        }
                    }
                }
                if (!obj.isEmpty())
                    output.add(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> extractData(String line) {
        boolean quotation = false;
        List<String> values = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == ',' && !quotation) {
                values.add(sb.toString());
                sb = new StringBuilder();
            } else if (c == '\"') {
                quotation = !quotation;
            } else {
                sb.append(c);
            }
        }
        values.add(sb.toString());
        return values;
    }

    @Override
    Function<String, String> formatDate() {
        return s -> s;
    }

    @Override
     Function<String, String> formatNumber() {
        return (s) -> String.format("%.2f", Double.parseDouble(s));
    }
}

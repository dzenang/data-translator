package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

public class PrnParser extends Parser {
    private List<HeaderMetadata> columnLength = Arrays.asList(
            new HeaderMetadata(0, 16, (s) -> s),
            new HeaderMetadata(16, 38, (s) -> s),
            new HeaderMetadata(38, 47, (s) -> s),
            new HeaderMetadata(47, 61, (s) -> s),
            new HeaderMetadata(61, 74, formatNumber()),
            new HeaderMetadata(74, 82, formatDate())
            );

    @Override
    Function<String, String> formatDate(){
        return (s) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date d = null;
            try {
                d = sdf.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sdf.applyPattern("dd/MM/yyyy");
            return sdf.format(d);
        };
    }

    @Override
    Function<String, String> formatNumber(){
        return (s) -> {
            double number = Double.parseDouble(s);
            number /= 100;
            return String.format("%.2f", number);
        };
    }

    public PrnParser (OutputType outputType) {
        this.outputType = outputType;
    }
    @Override
    void parse() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            List<String> headers = new ArrayList<>(columnLength.size());
            String line;
            boolean gotHeaders = false;
            while ((line = br.readLine()) != null) {
                Map<String, String> obj = new LinkedHashMap<>();
                for (int i = 0; i < columnLength.size(); i++) {
                    if (!gotHeaders) {
                        headers.add(line.substring(columnLength.get(i).getFrom(), columnLength.get(i).getTo()).trim());
                    } else {
                        String value = line.substring(columnLength.get(i).getFrom(), columnLength.get(i).getTo()).trim();
                        value = columnLength.get(i).getFormatData().apply(value);
                        obj.put(headers.get(i), value); //0,16 : 16, 38 : 38, 47
                    }
                }
                if (!obj.isEmpty())
                    output.add(obj);
                gotHeaders = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

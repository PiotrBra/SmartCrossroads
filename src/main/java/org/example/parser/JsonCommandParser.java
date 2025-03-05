package org.example.parser;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.*;
import java.io.*;

public class JsonCommandParser {
    private final ObjectMapper mapper;

    public JsonCommandParser(){
        mapper = new ObjectMapper();
    }

    // Metoda parsująca dane wejściowe – oczekujemy obiektu JSON z kluczem "commands"
    public List<Map<String, Object>> parseInput(String inputFile) throws IOException {
        Map<String, List<Map<String, Object>>> jsonMap =
                mapper.readValue(new File(inputFile),
                        new TypeReference<Map<String, List<Map<String, Object>>>>(){});
        return jsonMap.get("commands");
    }

    // Metoda zapisująca wynik symulacji w formacie JSON
    public void writeOutput(String outputFile, List<Map<String, Object>> stepStatuses) throws IOException {
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("stepStatuses", stepStatuses);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), outputMap);
    }
}


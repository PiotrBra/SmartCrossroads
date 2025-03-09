package org.example.controller;

import org.example.simulation.Simulation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Collectors;

class SimulationControllerTest {

    private Path tempOutputFile;

    @BeforeEach
    void setUp() throws IOException {
        // Tworzymy tymczasowy plik wyjściowy, do którego zapiszemy rezultaty symulacji.
        tempOutputFile = Files.createTempFile("simulation-output", ".json");
    }

    @AfterEach
    void tearDown() throws IOException {
        // Usuwamy tymczasowy plik po wykonaniu testu.
        Files.deleteIfExists(tempOutputFile);
    }

    @Test
    void testSimulationWithFiles() throws Exception {
        // Pobieramy ścieżkę do pliku wejściowego z katalogu resources.
        String inputFilePath = getClass().getResource("/input.json").getFile();

        // Inicjujemy symulację (zakładamy, że Simulation ma konstruktor domyślny).
        Simulation simulation = new Simulation();
        SimulationController controller = new SimulationController(simulation);

        // Uruchamiamy symulację korzystając z pliku wejściowego.
        controller.runSimulation(inputFilePath);

        // Zapisujemy wyniki symulacji do tymczasowego pliku.
        controller.saveResults(tempOutputFile.toString());

        // Odczytujemy zawartość wygenerowanego pliku wyjściowego.
        String generatedOutput = Files.lines(tempOutputFile, StandardCharsets.UTF_8)
                .collect(Collectors.joining(System.lineSeparator()))
                .trim();

        // Odczytujemy oczekiwany wynik z pliku resources (output.json).
        String expectedOutput = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/output.json"), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining(System.lineSeparator())).trim();

        // Porównujemy wygenerowany JSON z oczekiwanym, ignorując różnice formatowania.
        JSONAssert.assertEquals(expectedOutput, generatedOutput, JSONCompareMode.STRICT);
    }
}

package com.vsemaniv.brc.service;

import com.vsemaniv.brc.exceptions.InputFileException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Responsible for such operations related to routes:
 * - keeps path to input file
 * - parses input file with routes
 * - keeps routes
 */
@Component
public class RoutesManager {

    private static Logger LOG = Logger.getLogger(RoutesManager.class);

    private static String filePath;

    private int[][] routes;

    // Auxiliary variables
    //Indicates if routes array is already initialized
    private boolean routsArrayExists;
    //Index of route to process
    private int currentRoutIndex;

    /**
     * Parses input file on application start
     * <p>
     * Is executed during spring context initialization on start of application.
     * Is executed one time per application life cycle because the component has default bean scope - singleton
     */
    @PostConstruct
    public void init() {
        long start = System.currentTimeMillis();
        LOG.info("Starting process input files with routes, path=" + filePath);
        parseInputFile();
        LOG.info(String.format("Input File was processed successfully, processing time is %s ms", System.currentTimeMillis() - start));
    }

    /**
     * Returns array with routes
     * <p>
     * Each element of array is route
     * <p>
     * A route is represented by array of integers. Where 0 element is route Id, other elements are stations Ids
     * <p>
     * This multidimensional array has similar structure of elements to input file, except first line. The array doesn't
     * contain the first line.
     *
     * @return array with routes
     */
    public int[][] getRoutes() {
        return routes;
    }

    /**
     * Sets path to input file
     *
     * @param filePath the path to input file
     */
    public static void setFilePath(String filePath) {
        RoutesManager.filePath = filePath;
    }

    /**
     * Parses input file with routes line by line
     */
    private void parseInputFile() {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(this::parseLine);
        } catch (IOException e) {
            throw new InputFileException("Error occurred during parse file", e);
        }
    }

    /**
     * Processes a line of the input file
     *
     * @param line a line of the input file
     */
    private void parseLine(String line) {
        String[] splitLine = line.split("\\s+");
        if (routsArrayExists) {
            parseRouteLine(splitLine);
        } else {
            initializeRoutesArray(splitLine);
        }
    }

    /**
     * Processes a route line (index>0) from input file
     *
     * @param splitLine a split line of input file
     */
    private void parseRouteLine(String[] splitLine) {
        int[] stationsList = new int[splitLine.length];
        for (int i = 0; i < splitLine.length; i++) {
            stationsList[i] = Integer.parseInt(splitLine[i]);

        }
        routes[currentRoutIndex] = stationsList;
        currentRoutIndex++;
    }

    /**
     * Initializes array for routes
     * <p>
     * Gets capacity from elements with index 0 of specified array
     *
     * @param splitLine the split first line of the input file
     */
    private void initializeRoutesArray(String[] splitLine) {
        if (splitLine.length == 1) {
            int routesCount = Integer.parseInt(splitLine[0]);
            routsArrayExists = true;
            routes = new int[routesCount][];
            LOG.info("Array for routes was initialized successfully, routes count=" + routesCount);
        } else {
            throw new InputFileException("A count of routes was expected in first line, but got such line " + Arrays.toString(splitLine));
        }
    }
}

package com.vsemaniv.brc;

import com.vsemaniv.brc.exceptions.InputFileException;
import com.vsemaniv.brc.service.RoutesManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static Logger LOG = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        if (args.length != 0) {
            LOG.info("Path to input file was specified, path=" + args[0]);
            RoutesManager.setFilePath(args[0]);
        } else {
            LOG.error("Please specify path to file before start service");
            throw new InputFileException("Missed path to input file.");
        }
        SpringApplication.run(Application.class, args);
    }
}

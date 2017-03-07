package com.vsemaniv.brc.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * Object of the class responsible for verification whether there is direct route for stations
 */
@Component
public class BrcService {

    @Autowired
    private RoutesManager routesManager;

    private static Logger LOG = Logger.getLogger(BrcService.class);

    /**
     * Checks if there is a route which passes through departure station and arrival station in the same manner.
     *
     * @param departureStationId the departure station identifier
     * @param arrivalStationId   the arrival station identifier
     * @return true if direct route exists
     */
    public boolean isDirect(int departureStationId, int arrivalStationId) {
        long start = System.currentTimeMillis();

        Optional<int[]> routeOptional = Arrays.stream(routesManager.getRoutes())
                .filter(x -> isDirectRoute(x, departureStationId, arrivalStationId)).findFirst();

        boolean isDirect = false;
        if (routeOptional.isPresent()) {

            LOG.info(String.format("Direct route was found, dep_sid=%s, arr_sid=%s, routeId=%s, search took %s ms",
                    routeOptional.get()[0], departureStationId, arrivalStationId,
                    System.currentTimeMillis() - start));

            isDirect = true;
        } else {

            LOG.info(String.format("Direct route was NOT found, dep_sid=%s, arr_sid=%s, search took %s ms",
                    departureStationId, arrivalStationId,
                    System.currentTimeMillis() - start));
        }

        return isDirect;
    }

    /**
     * Checks if specified route is direct for specified stations
     * <p>
     * Note: Starts verification from 1 index, because first is route identifier
     *
     * @param singleRouteArray    the array which represents route
     * @param departureStationId the departure station identifier
     * @param arrivalStationId   the arrival station identifier
     * @return true if the route is direct for specified stations
     */
    private boolean isDirectRoute(int[] singleRouteArray, int departureStationId, int arrivalStationId) {
        boolean containsDeparture = false;
        int i = 1;
        while (!containsDeparture && i < singleRouteArray.length) {
            if (singleRouteArray[i] == departureStationId) {
                containsDeparture = true;
            }
            i++;
        }
        while (i < singleRouteArray.length) {
            if (singleRouteArray[i] == arrivalStationId) {
                return true;
            }
            i++;
        }
        return false;
    }
}

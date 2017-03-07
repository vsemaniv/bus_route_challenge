package com.vsemaniv.brc.service;

import com.vsemaniv.brc.domain.BrcResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class BrcController {

    @Autowired
    private BrcService brcService;

    @RequestMapping(path = "/direct", method = RequestMethod.GET)
    public BrcResponse direct(@RequestParam(value = "dep_sid") int departureStationId,
                              @RequestParam(value = "arr_sid") int arrivalStationId) {
        boolean isDirect = brcService.isDirect(departureStationId, arrivalStationId);
        return new BrcResponse(departureStationId, arrivalStationId, isDirect);
    }
}

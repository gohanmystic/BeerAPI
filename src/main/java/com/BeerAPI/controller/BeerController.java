package com.BeerAPI.controller;

import java.lang.reflect.Type;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BeerAPI.common.enums.MessageCode;
import com.BeerAPI.common.enums.StatusCode;
import com.BeerAPI.common.support.ResponseSupport;
import com.BeerAPI.dto.req.beer.ReqCreateBeer;
import com.BeerAPI.dto.req.beer.ReqDeleteBeer;
import com.BeerAPI.dto.req.beer.ReqFetchBeer;
import com.BeerAPI.dto.req.beer.ReqUpdateBeer;
import com.BeerAPI.dto.res.beer.ResBeer;
import com.BeerAPI.service.BeerService;

@RestController
@RequestMapping( value = "/api/v1/beer", produces = MediaType.APPLICATION_JSON_VALUE )
public class BeerController {

    @Autowired
    private BeerService beerService;

    @Autowired
    private ResponseSupport responseSupport;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{beerId}")
    public ResponseEntity<?> findBeerById(@PathVariable Integer beerId ) {

        ResBeer resBeer =
                modelMapper.map(beerService.findBeerById(beerId),
                        ResBeer.class);

        return ResponseEntity
                .ok(responseSupport.fetchSuccess(resBeer, MessageCode.SUCCESS_GET_DETAIL));
    }

    @PostMapping(value = "list")
    public ResponseEntity<?> findAllBeer(
            @Valid @RequestBody ReqFetchBeer reqFetchBeer) {

        Type type = new TypeToken<List<ResBeer>>(){}.getType();

        List<ResBeer> beerResponseList =
                modelMapper.map(beerService.fetchBeer(reqFetchBeer),
                                type);

        return ResponseEntity
                .ok(responseSupport.fetchSuccess(beerResponseList, MessageCode.SUCCESS_DATA));
    }

    @PostMapping(value = "create")
    public ResponseEntity<?> saveEmployee(
            @Valid @RequestBody ReqCreateBeer reqCreateBeer) {

        ResBeer resBeer =
                modelMapper.map(beerService.createBeer(reqCreateBeer),
                                ResBeer.class);

        return ResponseEntity
                .ok(responseSupport.fetchSuccess(resBeer, MessageCode.SUCCESS_CREATE));
    }

    @DeleteMapping(value = "/{beerId}")
    public ResponseEntity<?> deleteBeerById(
            @Valid @RequestBody ReqDeleteBeer reqDeleteBeer) {

        boolean isDeleted = beerService.deleteBeer(reqDeleteBeer);

        if (isDeleted) {

            return ResponseEntity
                    .ok(responseSupport.fetchSuccess(MessageCode.SUCCESS_DELETE));
        } else {

            return ResponseEntity
                    .ok(responseSupport.fetchError(StatusCode.SYSTEM_FAILURE, MessageCode.ERROR_DELETE));
        }
    }

    @PutMapping(value = "/{beerId}")
    public ResponseEntity<?> updateBeer(
            @Valid @RequestBody ReqUpdateBeer reqUpdateBeer) {

        ResBeer resBeer =
                modelMapper.map(beerService.updateBeer(reqUpdateBeer),
                                ResBeer.class);

        return ResponseEntity
                .ok(responseSupport.fetchSuccess(resBeer, MessageCode.SUCCESS_UPDATE));
    }
}

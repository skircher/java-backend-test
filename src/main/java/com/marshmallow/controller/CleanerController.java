package com.marshmallow.controller;

import com.marshmallow.vo.RequestVO;
import com.marshmallow.vo.ResponseVO;
import com.marshmallow.service.CleanerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller class which handles requests to localhost:PORT/api with a @PostMapping to /cleaner.
 * localhost:PORT/api/cleaner validates POST content against RequestVO and
 * calls the cleaner service, returning a valid ResponseVO or error response.
 * @author skircher
 */
@RestController
@RequestMapping("/api")
public class CleanerController {

    @Autowired
    private CleanerService service;


    @PostMapping(path = "/cleaner",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<ResponseVO> cleaner(@Valid @RequestBody RequestVO requestBody ){

        ResponseVO response = service.doService(requestBody);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * GlobalExceptionHandler would be more elegant, but I had some issues implementing it.
     * For now, clean up the explicitly handled Exceptions, and those caught by validation,
     * but let Spring provide an error response for anything that might have snuck through the cracks.
     */
    @ExceptionHandler
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Timestamp", new Date());
        body.put("Status", 400);

        //Get all fields errors
        List<String> errors = e.getBindingResult()
                                  .getFieldErrors()
                                  .stream()
                                  .map(x -> x.getDefaultMessage())
                                  .collect(Collectors.toList());

        body.put("Errors", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

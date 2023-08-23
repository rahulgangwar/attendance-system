package com.example.controller;

import com.example.entity.Swipe;
import com.example.enums.SwipeType;
import com.example.exception.DataNotFoundException;
import com.example.service.EmployeeService;
import com.example.service.SwipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/swipe")
@RestController
public class SwipeController {

    @Autowired private SwipeService swipeService;
    @Autowired private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Swipe>> getAllSwipes() {
        return new ResponseEntity<>(swipeService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/swipe/{empId}")
    public ResponseEntity<List<Swipe>> getSwipesForEmployee(@PathVariable Long empId) {
        return new ResponseEntity<>(swipeService.getSwipesForEmployee(empId), HttpStatus.OK);
    }

    @PostMapping(path = "/swipe/{empId}/{swipeType}")
    public ResponseEntity<Swipe> swipeIn(@PathVariable Long empId, SwipeType swipeType) {
        return employeeService
                .findById(empId)
                .map(employee -> swipeService.swipeInForEmployee(employee, swipeType))
                .map(swipe -> new ResponseEntity<>(swipe, HttpStatus.OK))
                .orElseThrow(
                        () ->
                                new DataNotFoundException(
                                        "No employee found for employee id : " + empId));
    }
}

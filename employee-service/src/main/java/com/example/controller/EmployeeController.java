package com.example.controller;

import com.example.dto.EmployeeAttendanceDTO;
import com.example.entity.Employee;
import com.example.exception.DataNotFoundException;
import com.example.helper.AttendanceHelper;
import com.example.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/employee")
@RestController
public class EmployeeController {

    @Autowired private EmployeeService employeeService;
    @Autowired private AttendanceHelper attendanceHelper;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Employee>> getAll() {
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        log.info("Getting data for employee with id : " + id);
        return new ResponseEntity<>(
                employeeService
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new DataNotFoundException(
                                                "Employee not found for id: " + id)),
                HttpStatus.OK);
    }

    @GetMapping(path = "/attendance")
    public ResponseEntity<Map<Long, EmployeeAttendanceDTO>> getAttendance() {
        log.info("Getting attendance details ..");
        return new ResponseEntity<>(attendanceHelper.getEmployeeAttendance(), HttpStatus.OK);
    }
}

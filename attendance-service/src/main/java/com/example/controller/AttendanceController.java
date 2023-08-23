package com.example.controller;

import com.example.entity.Attendance;
import com.example.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/attendance")
@RestController
public class AttendanceController {

    @Autowired private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<Attendance>> getAttendance() {
        log.info("Getting attendance details ..");
        return new ResponseEntity<>(attendanceService.getAll(), HttpStatus.OK);
    }
}

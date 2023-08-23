package com.example.dto;

import com.example.enums.AttendanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAttendanceDTO implements Serializable {
    private Long empId;
    private LocalDate date;
    private AttendanceType attendanceType;
}

package com.example.dto;

import com.example.enums.AttendanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeAttendanceDTO implements Serializable {
    private Long empId;
    private LocalDate date;
    private AttendanceType attendanceType;
}

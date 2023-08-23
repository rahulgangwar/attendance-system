package com.example.service;

import com.example.dto.EmployeeAttendanceDTO;
import com.example.entity.Attendance;
import com.example.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
public class AttendanceService extends AbstractService<Attendance, Long> {
    @Autowired private EmployeeService employeeService;

    public Attendance markAttendance(EmployeeAttendanceDTO employeeAttendance) {
        Long empId = employeeAttendance.getEmpId();
        Optional<Employee> employee = employeeService.findById(empId);
        if (employee.isPresent()) {
            Optional<Attendance> optionalAttendance =
                    getAttendance(empId, employeeAttendance.getDate());
            if (optionalAttendance.isPresent()) {
                log.info("Attendance is already marked. Details : {}", optionalAttendance.get());
                return null;
            } else {
                Attendance attendance =
                        create(
                                new Attendance(
                                        employee.get(),
                                        employeeAttendance.getDate(),
                                        employeeAttendance.getAttendanceType()));
                log.info("Attendance marked: " + attendance);
                return attendance;
            }
        } else {
            log.error("Employee not found for id : " + employeeAttendance.getEmpId());
            return null;
        }
    }

    public Optional<Attendance> getAttendance(Long empId, LocalDate date) {
        return getAll().stream()
                .filter(attendance -> attendance.getEmployee().getId().equals(empId))
                .filter(attendance -> attendance.getDate().equals(date))
                .findFirst();
    }
}

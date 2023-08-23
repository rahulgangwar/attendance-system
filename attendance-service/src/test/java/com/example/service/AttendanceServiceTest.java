package com.example.service;

import com.example.dto.EmployeeAttendanceDTO;
import com.example.entity.Attendance;
import com.example.entity.Employee;
import com.example.enums.AttendanceType;
import com.example.repository.AttendanceRepository;
import com.example.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class AttendanceServiceTest {
    @MockBean EmployeeRepository employeeRepository;
    @MockBean AttendanceRepository attendanceRepository;
    @Autowired AttendanceService attendanceService;

    @Test
    void testMarkAttendance() {
        Employee employee = new Employee("rahul");
        when(employeeRepository.findById(1l)).thenReturn(Optional.of(employee));
        when(attendanceRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        Attendance markedAttendance = mock(Attendance.class);
        when(attendanceRepository.save(any())).thenReturn(markedAttendance);

        EmployeeAttendanceDTO employeeAttendance =
                new EmployeeAttendanceDTO(1l, LocalDate.now(), AttendanceType.PRESENT);

        assertThat(attendanceService.markAttendance(employeeAttendance))
                .isEqualTo(markedAttendance);
    }
}

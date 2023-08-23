package com.example;

import com.example.dto.EmployeeAttendanceDTO;
import com.example.entity.Employee;
import com.example.entity.Swipe;
import com.example.enums.AttendanceType;
import com.example.helper.AttendanceHelper;
import com.example.helper.KafkaHelper;
import com.example.service.EmployeeService;
import com.example.service.SwipeService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttendanceHelperTest {

    @Mock private EmployeeService employeeService;
    @Mock private SwipeService swipeService;
    @Mock private KafkaHelper kafkaHelper;
    private AttendanceHelper attendanceHelper;

    @BeforeEach
    void init() {
        attendanceHelper = new AttendanceHelper(employeeService, swipeService, kafkaHelper);
    }

    @Test
    void testEmployeeAbsentWhenNoSwipes() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(1l);

        when(employeeService.getAll()).thenReturn(Collections.singletonList(employee));
        when(swipeService.getSwipesForEmployeeForToday(any(), any()))
                .thenReturn(Collections.EMPTY_LIST);

        Map<Long, EmployeeAttendanceDTO> data = attendanceHelper.getEmployeeAttendance();
        assertThat(data).hasSize(1);
        assertThat(CollectionUtils.get(data.values(), 0).getAttendanceType())
                .isEqualTo(AttendanceType.ABSENT);
    }

    @Test
    void testEmployeeAbsentWhenHrsNotSufficient() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(1l);

        when(employeeService.getAll()).thenReturn(Collections.singletonList(employee));

        Swipe swipeIn = mock(Swipe.class);
        when(swipeIn.getTime()).thenReturn(LocalDate.now().atTime(6, 0));

        Swipe swipeOut = mock(Swipe.class);
        when(swipeOut.getTime()).thenReturn(LocalDate.now().atTime(8, 0));

        when(swipeService.getSwipesForEmployeeForToday(any(), any()))
                .thenReturn(Arrays.asList(swipeIn, swipeOut));

        Map<Long, EmployeeAttendanceDTO> data = attendanceHelper.getEmployeeAttendance();
        assertThat(data).hasSize(1);
        assertThat(CollectionUtils.get(data.values(), 0).getAttendanceType())
                .isEqualTo(AttendanceType.ABSENT);
    }

    @Test
    void testEmployeeHalfDay() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(1l);

        when(employeeService.getAll()).thenReturn(Collections.singletonList(employee));

        Swipe swipeIn = mock(Swipe.class);
        when(swipeIn.getTime()).thenReturn(LocalDate.now().atTime(6, 0));

        Swipe swipeOut = mock(Swipe.class);
        when(swipeOut.getTime()).thenReturn(LocalDate.now().atTime(11, 0));

        when(swipeService.getSwipesForEmployeeForToday(any(), any()))
                .thenReturn(Arrays.asList(swipeIn, swipeOut));

        Map<Long, EmployeeAttendanceDTO> data = attendanceHelper.getEmployeeAttendance();
        assertThat(data).hasSize(1);
        assertThat(CollectionUtils.get(data.values(), 0).getAttendanceType())
                .isEqualTo(AttendanceType.HALF_DAY);
    }

    @Test
    void testEmployeePresent() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(1l);

        when(employeeService.getAll()).thenReturn(Collections.singletonList(employee));

        Swipe swipeIn = mock(Swipe.class);
        when(swipeIn.getTime()).thenReturn(LocalDate.now().atTime(6, 0));

        Swipe swipeOut = mock(Swipe.class);
        when(swipeOut.getTime()).thenReturn(LocalDate.now().atTime(18, 0));

        when(swipeService.getSwipesForEmployeeForToday(any(), any()))
                .thenReturn(Arrays.asList(swipeIn, swipeOut));

        Map<Long, EmployeeAttendanceDTO> data = attendanceHelper.getEmployeeAttendance();
        assertThat(data).hasSize(1);
        assertThat(CollectionUtils.get(data.values(), 0).getAttendanceType())
                .isEqualTo(AttendanceType.PRESENT);
    }
}

package com.example.helper;

import com.example.dto.EmployeeAttendanceDTO;
import com.example.entity.Employee;
import com.example.entity.Swipe;
import com.example.enums.AttendanceType;
import com.example.enums.SwipeType;
import com.example.service.EmployeeService;
import com.example.service.SwipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class AttendanceHelper {

    private final EmployeeService employeeService;
    private final SwipeService swipeService;
    private final KafkaHelper kafkaHelper;

    public AttendanceHelper(
            EmployeeService employeeService, SwipeService swipeService, KafkaHelper kafkaHelper) {
        this.employeeService = employeeService;
        this.swipeService = swipeService;
        this.kafkaHelper = kafkaHelper;
    }

    public Map<Long, EmployeeAttendanceDTO> getEmployeeAttendance() {

        List<Employee> employees = employeeService.getAll();
        Map<Long, EmployeeAttendanceDTO> employeeAttendanceDetails = new HashMap<>();

        for (Employee employee : employees) {
            log.info("Processing swipes for emp : " + employee);
            Optional<Swipe> firstSwipeIn =
                    swipeService
                            .getSwipesForEmployeeForToday(employee.getId(), SwipeType.SWIPE_IN)
                            .stream()
                            .min(Comparator.comparing(Swipe::getTime));
            Optional<Swipe> lastSwipeOut =
                    swipeService
                            .getSwipesForEmployeeForToday(employee.getId(), SwipeType.SWIPE_OUT)
                            .stream()
                            .max(Comparator.comparing(Swipe::getTime));

            if (!firstSwipeIn.isPresent()) {
                log.info("No swipe in present for the employee. Marking absent.");
                EmployeeAttendanceDTO employeeAttendance =
                        new EmployeeAttendanceDTO(
                                employee.getId(),
                                LocalDateTime.now().toLocalDate(),
                                AttendanceType.ABSENT);
                employeeAttendanceDetails.put(employee.getId(), employeeAttendance);
                kafkaHelper.publish("attendance", employeeAttendance);
            } else if (!lastSwipeOut.isPresent()) {
                log.error("No swipe out data present for employee !!");
            } else {
                EmployeeAttendanceDTO employeeAttendance =
                        new EmployeeAttendanceDTO(
                                employee.getId(),
                                LocalDateTime.now().toLocalDate(),
                                getAttendanceType(firstSwipeIn.get(), lastSwipeOut.get()));
                employeeAttendanceDetails.put(employee.getId(), employeeAttendance);
                kafkaHelper.publish("attendance", employeeAttendance);
            }
        }
        return employeeAttendanceDetails;
    }

    private AttendanceType getAttendanceType(Swipe swipeIn, Swipe swipeOut) {
        Duration duration = Duration.between(swipeIn.getTime(), swipeOut.getTime());
        AttendanceType attendanceType = AttendanceType.getAttendanceTypeFromHrs(duration.toHours());

        log.info("Total hrs : " + duration.toHours() + ". Attendance type : " + attendanceType);
        return attendanceType;
    }
}

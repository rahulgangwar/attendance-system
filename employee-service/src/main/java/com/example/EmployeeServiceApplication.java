package com.example;

import com.example.entity.Employee;
import com.example.enums.SwipeType;
import com.example.service.EmployeeService;
import com.example.service.SwipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@SpringBootApplication
public class EmployeeServiceApplication implements CommandLineRunner {
    @Autowired private EmployeeService employeeService;
    @Autowired private SwipeService swipeService;

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<Employee> employees = employeeService.getAll();
        log.info("Total employees found : " + employees.size());
        if (employees.isEmpty()) {
            // Add Employees
            Employee employeeRam = employeeService.create(new Employee("Ram")); // Test ABSENT
            Employee employeeKaran = employeeService.create(new Employee("Karan")); // Test HALF_DAY
            Employee employeeRahul = employeeService.create(new Employee("Rahul")); // Test PRESENT

            // Create swipes for Ram (Total time = 2 hrs)
            swipeService.swipeInForEmployee(
                    employeeRam, SwipeType.SWIPE_IN, LocalDate.now().atTime(6, 0));
            swipeService.swipeInForEmployee(
                    employeeRam, SwipeType.SWIPE_OUT, LocalDate.now().atTime(7, 0));

            // Create swipes for Karan (Total time = 5 hrs)
            swipeService.swipeInForEmployee(
                    employeeKaran, SwipeType.SWIPE_IN, LocalDate.now().atTime(6, 0));
            swipeService.swipeInForEmployee(
                    employeeKaran, SwipeType.SWIPE_OUT, LocalDate.now().atTime(11, 0));

            // Create swipes for Rahul (Total time = 9 hrs)
            swipeService.swipeInForEmployee(
                    employeeRahul, SwipeType.SWIPE_IN, LocalDate.now().atTime(6, 0));
            swipeService.swipeInForEmployee(
                    employeeRahul, SwipeType.SWIPE_OUT, LocalDate.now().atTime(18, 0));
        } else {
            log.info("Skipping data creation. Data already present.");
        }
    }
}

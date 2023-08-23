package com.example.service;

import com.example.entity.Employee;
import com.example.entity.Swipe;
import com.example.enums.SwipeType;
import com.example.util.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwipeService extends AbstractService<Swipe, Long> {

    public Swipe swipeInForEmployee(
            Employee employee, SwipeType type, LocalDateTime localDateTime) {
        return create(new Swipe(employee, type, localDateTime));
    }

    public Swipe swipeInForEmployee(Employee employee, SwipeType type) {
        return create(new Swipe(employee, type));
    }

    public List<Swipe> getSwipesForEmployee(Long empId) {
        return getAll().stream()
                .filter(swipe -> swipe.getEmployee().getId().equals(empId))
                .collect(Collectors.toList());
    }

    public List<Swipe> getSwipesForEmployee(Long empId, SwipeType swipeType) {
        return getAll().stream()
                .filter(swipe -> swipe.getEmployee().getId().equals(empId))
                .filter(swipe -> swipe.getType().equals(swipeType))
                .collect(Collectors.toList());
    }

    public List<Swipe> getSwipesForEmployeeForToday(Long empId, SwipeType swipeType) {
        return getAll().stream()
                .filter(swipe -> DateUtil.isToday(swipe.getTime()))
                .filter(swipe -> swipe.getEmployee().getId().equals(empId))
                .filter(swipe -> swipe.getType().equals(swipeType))
                .collect(Collectors.toList());
    }
}

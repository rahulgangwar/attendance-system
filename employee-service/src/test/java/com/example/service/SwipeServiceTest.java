package com.example.service;

import com.example.entity.Employee;
import com.example.entity.Swipe;
import com.example.enums.SwipeType;
import com.example.repository.SwipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class SwipeServiceTest {

    @MockBean private SwipeRepository swipeRepository;
    @Autowired private SwipeService swipeService;

    @BeforeEach
    public void setup() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(1l);
        when(swipeRepository.findAll())
                .thenReturn(
                        Arrays.asList(
                                new Swipe(employee, SwipeType.SWIPE_IN, LocalDateTime.now()),
                                new Swipe(
                                        employee,
                                        SwipeType.SWIPE_IN,
                                        LocalDateTime.now().minusDays(1))));
    }

    @Test
    void testGetSwipes() {
        assertThat(swipeService.getSwipesForEmployee(1l)).hasSize(2);
    }

    @Test
    void testGetSwipesForToday() {
        assertThat(swipeService.getSwipesForEmployeeForToday(1l, SwipeType.SWIPE_IN)).hasSize(1);
    }
}

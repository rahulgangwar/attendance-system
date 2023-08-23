package com.example;

import com.example.entity.Employee;
import com.example.entity.Swipe;
import com.example.enums.SwipeType;
import com.example.repository.EmployeeRepository;
import com.example.repository.SwipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiTest {
    @Autowired MockMvc mockMvc;
    @MockBean EmployeeRepository employeeRepository;
    @MockBean SwipeRepository swipeRepository;

    @Test
    void testGetEmployees() throws Exception {
        Employee employee = new Employee("rahul");
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        MvcResult result =
                mockMvc.perform(get("/employee/getAll"))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().isOk())
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("[{\"id\":null,\"name\":\"rahul\"}]");
    }

    @Test
    void testAttendance() throws Exception {

        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(1l);

        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        Swipe swipeIn = mock(Swipe.class);
        when(swipeIn.getTime()).thenReturn(LocalDate.now().atTime(6, 0));
        when(swipeIn.getEmployee()).thenReturn(employee);
        when(swipeIn.getType()).thenReturn(SwipeType.SWIPE_IN);

        Swipe swipeOut = mock(Swipe.class);
        when(swipeOut.getTime()).thenReturn(LocalDate.now().atTime(18, 0));
        when(swipeOut.getEmployee()).thenReturn(employee);
        when(swipeOut.getType()).thenReturn(SwipeType.SWIPE_OUT);

        when(swipeRepository.findAll()).thenReturn(Arrays.asList(swipeIn, swipeOut));

        MvcResult result =
                mockMvc.perform(get("/employee/attendance"))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().isOk())
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content)
                .isEqualTo(
                        "{\"1\":{\"empId\":1,\"date\":\""
                                + LocalDate.now()
                                + "\",\"attendanceType\":\"PRESENT\"}}");
    }
}

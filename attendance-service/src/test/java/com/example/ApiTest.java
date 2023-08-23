package com.example;

import com.example.entity.Attendance;
import com.example.entity.Employee;
import com.example.enums.AttendanceType;
import com.example.repository.AttendanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiTest {
    @Autowired MockMvc mockMvc;
    @MockBean AttendanceRepository attendanceRepository;

    @Test
    void testGetEmployees() throws Exception {
        Employee employee = new Employee("rahul");

        Attendance attendance = new Attendance(employee, LocalDate.now(), AttendanceType.PRESENT);
        when(attendanceRepository.findAll()).thenReturn(Collections.singletonList(attendance));

        MvcResult result =
                mockMvc.perform(get("/attendance"))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().isOk())
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content)
                .isEqualTo(
                        "[{\"id\":null,\"employee\":{\"id\":null,\"name\":\"rahul\"},\"date\":\"" +
                                LocalDate.now() +
                                "\",\"type\":\"PRESENT\"}]");
    }
}

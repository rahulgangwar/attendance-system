package com.example;

import com.example.dto.EmployeeAttendanceDTO;
import com.example.enums.AttendanceType;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

 class EmployeeAttendanceDeserializerTest {

    @Test
     void testDeserialize() {
        EmployeeAttendanceDTO employeeAttendanceDTO =
                new EmployeeAttendanceDeserializer()
                        .deserialize(
                                "attendance",
                                "{\"empId\":1,\"date\":[2023,4,22],\"attendanceType\":\"PRESENT\"}"
                                        .getBytes(StandardCharsets.UTF_8));

        assertThat(employeeAttendanceDTO).isNotNull();
        assertThat(employeeAttendanceDTO.getEmpId()).isEqualTo(1l);
        assertThat(employeeAttendanceDTO.getDate()).hasToString("2023-04-22");
        assertThat(employeeAttendanceDTO.getAttendanceType()).isEqualTo(AttendanceType.PRESENT);
    }
}

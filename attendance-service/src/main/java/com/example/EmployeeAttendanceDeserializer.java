package com.example;

import com.example.dto.EmployeeAttendanceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

@Slf4j
public class EmployeeAttendanceDeserializer implements Deserializer<EmployeeAttendanceDTO> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public EmployeeAttendanceDTO deserialize(String data, byte[] bytes) {
        try {
            log.info("Deserializing...");
            EmployeeAttendanceDTO employeeAttendance =
                    objectMapper.readValue(
                            new String(bytes, StandardCharsets.UTF_8), EmployeeAttendanceDTO.class);
            log.info("Data : " + employeeAttendance);
            return employeeAttendance;
        } catch (Exception e) {
            throw new SerializationException(
                    "Error when deserializing byte[] to EmployeeAttendance");
        }
    }
}

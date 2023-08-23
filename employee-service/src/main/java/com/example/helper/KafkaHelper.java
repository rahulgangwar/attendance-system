package com.example.helper;

import com.example.dto.EmployeeAttendanceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaHelper {

    @Autowired private KafkaTemplate<String, EmployeeAttendanceDTO> kafkaTemplate;

    public void publish(String key, EmployeeAttendanceDTO employeeAttendance) {
        ListenableFuture<SendResult<String, EmployeeAttendanceDTO>> future =
                kafkaTemplate.send("EmployeeAttendance", key, employeeAttendance);

        future.addCallback(
                new ListenableFutureCallback<SendResult<String, EmployeeAttendanceDTO>>() {
                    @Override
                    public void onSuccess(SendResult<String, EmployeeAttendanceDTO> result) {
                        log.info(
                                "-------- > Sent message=["
                                        + key
                                        + " : "
                                        + employeeAttendance
                                        + "]  to "
                                        + "partition "
                                        + result.getRecordMetadata().partition()
                                        + " with offset=["
                                        + result.getRecordMetadata().offset()
                                        + "]");
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.error(
                                "Unable to send message=["
                                        + key
                                        + " : "
                                        + employeeAttendance
                                        + "] due to : "
                                        + ex.getMessage());
                    }
                });
    }
}

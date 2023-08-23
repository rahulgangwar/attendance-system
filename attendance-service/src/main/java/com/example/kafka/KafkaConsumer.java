package com.example.kafka; // package com.example.kafka;

import com.example.dto.EmployeeAttendanceDTO;
import com.example.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
public class KafkaConsumer {
    @Autowired private AttendanceService attendanceService;

    @KafkaListener(
            groupId = "foo",
            topicPartitions =
                    @TopicPartition(
                            topic = "EmployeeAttendance",
                            partitionOffsets = {
                                @PartitionOffset(partition = "0", initialOffset = "0")
                            }),
            containerFactory = "kafkaListenerContainerFactory")
    public void listenToPartition0(
            @Payload EmployeeAttendanceDTO employeeAttendance,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        attendanceService.markAttendance(employeeAttendance);
    }
}

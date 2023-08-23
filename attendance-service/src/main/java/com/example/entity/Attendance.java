package com.example.entity;

import com.example.enums.AttendanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne private Employee employee;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AttendanceType type;

    public Attendance(Employee employee, LocalDate date, AttendanceType attendanceType) {
        this.employee = employee;
        this.date = date;
        this.type = attendanceType;
    }
}

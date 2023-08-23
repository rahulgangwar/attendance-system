package com.example.entity;

import com.example.enums.SwipeType;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@Entity
public class Swipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne private Employee employee;

    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    private SwipeType type;

    public Swipe() {}

    public Swipe(Employee employee, SwipeType type) {
        this.employee = employee;
        this.time = LocalDateTime.now();
        this.type = type;
    }

    public Swipe(Employee employee, SwipeType type, LocalDateTime localDateTime) {
        this.employee = employee;
        this.time = localDateTime;
        this.type = type;
    }
}

package com.example.springdemo.event;

import lombok.Data;

@Data
public class BaseEvent {

    private final EventType type;
}

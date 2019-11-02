package com.example.springdemo.event;

import com.example.springdemo.dto.PatientProblemDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class SensorAnomalyEvent extends BaseEvent{

    private final PatientProblemDTO patientProblemDTO;

    public SensorAnomalyEvent(PatientProblemDTO patientProblemDTO)
    {
        super(EventType.SENSOR_ANOMALY);
        this.patientProblemDTO = patientProblemDTO;

    }
}

package com.example.springdemo.repositories;

import com.example.springdemo.entities.Sensor;
import org.springframework.data.repository.Repository;

public interface SensorRepository extends CRUDRepository<Sensor>, Repository<Sensor, Integer> {
}

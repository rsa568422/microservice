package com.example.microservice.application.port.in;

import com.example.microservice.application.dto.NewSchedulerDTO;
import lombok.NonNull;

public interface RegisterNewSchedulerUseCase {

    void registerNewScheduler(@NonNull NewSchedulerDTO newScheduler);
}

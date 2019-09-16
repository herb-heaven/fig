package com.herb.heaven.fig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class FigConfiguration {

    @Autowired(required = false)
    FigWorker figWorker;

    @Bean
    public SnowFlake snowFlake(){
        String id = UUID.randomUUID().toString();
        return new SnowFlake(figWorker.createWorkId(),id);
    }

    @Bean
    public FigWorkerValidator figWorkerValidator(SnowFlake snowFlake){
        FigWorkerValidator figWorkerValidator = new FigWorkerValidator(snowFlake.getWorkId(),figWorker);
        figWorkerValidator.enableValidator();
        return figWorkerValidator;
    }

}

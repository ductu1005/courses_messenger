//package com.datn.batch;
//
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//
//public class UserTasklet implements Tasklet {
//    int counter = 0;
//
//
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        if (counter == 5) {
//            counter = 0;
//            return RepeatStatus.FINISHED;
//        } else {
//            counter++;
//            return RepeatStatus.CONTINUABLE;
//        }
//    }
//}

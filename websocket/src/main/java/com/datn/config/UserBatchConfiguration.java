//package com.datn.config;
//
//import com.datn.batch.MdlUserWriter;
//import com.datn.batch.RestUserReader;
//import com.datn.batch.UserProcessor;
//import com.datn.batch.UserTasklet;
//import com.datn.entity.MdlUserEntity;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JdbcCursorItemReader;
//import org.springframework.batch.item.support.CompositeItemProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//
//@Configuration
//@Log4j2
//public class UserBatchConfiguration {
//
//
//    @Bean
//    public Job userMdlReaderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new JobBuilder("userReadJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(taskletStep(jobRepository, transactionManager))
//                .build();
//    }
//
//    @Bean
//    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("userReaderStep", jobRepository).<MdlUserEntity, MdlUserEntity>
//                        chunk(10, transactionManager)
//                .reader(restBookReader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//
//    }
//
//    @Bean
//    public Step taskletStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
//        return new StepBuilder("taskletStep", jobRepository)
//                .tasklet(new UserTasklet(), platformTransactionManager)
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public ItemReader<MdlUserEntity> restBookReader() {
//        return new RestUserReader(new RestTemplate());
//    }
//
//    @StepScope
//    @Bean
//    public ItemWriter<MdlUserEntity> writer() {
//        return new MdlUserWriter();
//    }
//
//    @StepScope
//    @Bean
//    public ItemProcessor<MdlUserEntity, MdlUserEntity> processor() {
//        CompositeItemProcessor<MdlUserEntity, MdlUserEntity> processor = new CompositeItemProcessor<>();
//        processor.setDelegates(List.of(new UserProcessor()));
//        return processor;
//    }
//
//    @Bean
//    @StepScope
//    public ItemReader<MdlUserEntity> reader() {
//        JdbcCursorItemReader<MdlUserEntity> itemReader = new JdbcCursorItemReader<>();
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
//        dataSource.setUrl("jdbc:mariadb://localhost:3308");
//        dataSource.setUsername("root");
//        dataSource.setPassword("");
//
//        itemReader.setDataSource(dataSource);
//        itemReader.setSql("SELECT * FROM mdl_user WHERE deleted = 0");
//        itemReader.setRowMapper((resultSet, i) -> {
//            MdlUserEntity entity = new MdlUserEntity();
//            entity.setUserId(resultSet.getLong("id"));
//            entity.setUsername(resultSet.getString("username"));
//            entity.setEmail(resultSet.getString("email"));
//            entity.setPassword(resultSet.getString("password"));
//            log.info("{user}: " + entity);
//            return entity;
//        });
//        return itemReader;
//    }
//
//}
//
//

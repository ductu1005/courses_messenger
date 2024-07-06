//package com.datn.batch;
//
//import com.datn.entity.MdlUserEntity;
//import com.datn.repository.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Slf4j
//public class MdlUserWriter implements ItemWriter<MdlUserEntity> {
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Override
//    public void write(Chunk<? extends MdlUserEntity> chunk) throws Exception {
//        userRepository.saveAll(chunk.getItems());
//    }
//}

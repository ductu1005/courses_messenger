//package com.datn.batch;
//
//import com.datn.entity.MdlUserEntity;
//import org.springframework.batch.item.ItemProcessor;
//
//public class UserProcessor implements ItemProcessor<MdlUserEntity, MdlUserEntity> {
//
//    @Override
//    public MdlUserEntity process(MdlUserEntity item) throws Exception {
//        item.setUsername(item.getUsername().toUpperCase());
//        return item;
//    }
//}
//

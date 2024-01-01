//package com.IOT.IOT_system.service.impl;
//
//import com.IOT.IOT_system.model.RFID;
//import com.IOT.IOT_system.model.Stream_bit;
//import com.IOT.IOT_system.repository.RfidRepository;
//import com.IOT.IOT_system.repository.Stream_bit_Repo;
//import com.IOT.IOT_system.service.RFID_Service;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.integration.mqtt.support.MqttHeaders;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageHandler;
//import org.springframework.messaging.MessagingException;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.Optional;
//
//@Service
//@Slf4j
//public class MqttMessageHandler implements MessageHandler {
//    private final RFID_Service rfid_Service;
//    @Autowired
//    private RfidRepository rfidRepo;
//    private final ObjectMapper mapper;
//    private Stream_bit_Repo SB_repo;
//
//
//    public MqttMessageHandler(RFID_Service rfidService, ObjectMapper mapper, Stream_bit_Repo sbRepo) {
//        rfid_Service = rfidService;
//
//        this.mapper = mapper;
//        SB_repo = sbRepo;
//    }
//
//    @Override
//    public void handleMessage(Message<?> message) throws MessagingException {
//
//        try {
//            String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
//            var rawData = message.getPayload().toString();
//            String myMessage = mapper.readValue(rawData, String.class);
////            Optional<RFID> RFID_O = rfidRepo.findByIDx(myMessage);
////            if(RFID_O.isPresent()){
////                rfidRepo.deleteRFIDBy(myMessage);
////            }else{
////                RFID rfid = new RFID();
////                Date time = new Date();
////                rfid.setDate(time);
////                rfid.getIDx(myMessage);
////
////                rfid_Service.save(rfid);
////                log.info(rfid.toString());
////            }
//            switch (topic) {
//                case "UID-In":
//                    Optional<RFID> RFID_I = rfidRepo.findByIDx(myMessage);
//                    if(RFID_I.isPresent()){
//                        log.info("xe bạn bị tịch thu");
//                    }else{
//                        RFID rfid = new RFID();
//                        Date time = new Date();
//                        rfid.setDate(time);
//                        rfid.setIDx(myMessage);
//
//                        rfid_Service.save(rfid);
//                        log.info(rfid.toString());
//
//                    }
//                    break;
//                case "UID-Out":
//                    Optional<RFID> RFID_O = rfidRepo.findByIDx(myMessage);
//                    if(RFID_O.isPresent()){
//                        rfidRepo.deleteRFIDBy(myMessage);
//                    }else{
//
//                        log.info("xe không có trong hệ thống");
//
//                    }
//                    break;
//                case "pos":
//                    Stream_bit x = new Stream_bit();
//                    x.setStreamBit(myMessage);
//                    SB_repo.save(x);
//                    break;
//                default:
//                    log.warn("Unhandled topic: " + topic);
//            }
//        } catch (Exception e) {
//            log.error("something went wrong!");
//        }
//    }
//}

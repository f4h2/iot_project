//package com.IOT.IOT_system.config;
//
//import com.IOT.IOT_system.model.RFID;
//import com.IOT.IOT_system.model.Stream_bit;
//import com.IOT.IOT_system.repository.RfidRepository;
//import com.IOT.IOT_system.repository.Stream_bit_Repo;
//import com.IOT.IOT_system.service.RFID_Service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.core.MessageProducer;
//import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
//import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
//import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
//import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
//import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
//import org.springframework.integration.mqtt.support.MqttHeaders;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.MessageHandler;
//import org.springframework.messaging.MessagingException;
//
//import java.util.Date;
//import java.util.Optional;
//
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class MqttServerConfig {
//    private final RFID_Service rfid_Service;
//    @Autowired
//    private RfidRepository rfidRepo;
//    private final ObjectMapper mapper;
//    private Stream_bit_Repo SB_repo;
//
//
//
//    @Bean
//    public MqttPahoClientFactory mqttClientFactory() {
//        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        MqttConnectOptions options = new MqttConnectOptions();
//
//        options.setServerURIs(new String[] {"tcp://820d38034a484dc4924168e678006e8f.s2.eu.hivemq.cloud:8883"});
//        options.setUserName("loc.nkt204763");
//        String pass = "thegioinaylasao2002";
//        options.setPassword(pass.toCharArray());
//        options.setCleanSession(true);
//
//        factory.setConnectionOptions(options);
//
//        return factory;
//    }
//
//    @Bean
//    public MessageChannel mqttInputChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageProducer inbound() {
//        MqttPahoMessageDrivenChannelAdapter adapter =
//                new MqttPahoMessageDrivenChannelAdapter("serverIn", mqttClientFactory(), "UID-In", "UID-Out", "pos");
//
//        adapter.setCompletionTimeout(5000);
//        adapter.setConverter(new DefaultPahoMessageConverter());
//        adapter.setQos(2);
//        adapter.setOutputChannel(mqttInputChannel());
//        return adapter;
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannel")
//    public MessageHandler handler() {
//        return new MessageHandler() {
//
//            @Override
//            public void handleMessage(Message<?> message) throws MessagingException {
//
//                try {
//                    String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
//                    var rawData = message.getPayload().toString();
//                    String myMessage = mapper.readValue(rawData, String.class);
//
//                    switch (topic) {
//                        case "UID-In":
//                            Optional<RFID> RFID_I = rfidRepo.findByIDx(myMessage);
//                            if (RFID_I.isPresent()) {
//                                log.info("xe bạn bị tịch thu");
//                            } else {
//                                RFID rfid = new RFID();
//                                Date time = new Date();
//                                rfid.setDate(time);
//                                rfid.setIDx(myMessage);
//
//                                rfid_Service.save(rfid);
//                                log.info(rfid.toString());
//
//                            }
//                            break;
//                        case "UID-Out":
//                            Optional<RFID> RFID_O = rfidRepo.findByIDx(myMessage);
//                            if (RFID_O.isPresent()) {
//                                rfidRepo.deleteRFIDBy(myMessage);
//                            } else {
//
//                                log.info("xe không có trong hệ thống");
//
//                            }
//                            break;
//                        case "pos":
//                            Stream_bit x = new Stream_bit();
//                            x.setStreamBit(myMessage);
//                            SB_repo.save(x);
//                            break;
//                        default:
//                            log.warn("Unhandled topic: " + topic);
//                    }
//                } catch (Exception e) {
//                    log.error("something went wrong!");
//                }
//            }
//        };
//    }
//
//    @Bean
//    public MessageChannel mqttOutboundChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttOutboundChannel")
//    public MessageHandler mqttOutbound() {
//        // clientId is generated using a random number
//        MqttPahoMessageHandler messageHandler =
//                new MqttPahoMessageHandler("serverOut", mqttClientFactory());
//        messageHandler.setAsync(true);
//        messageHandler.setDefaultTopic("#");
//        messageHandler.setDefaultRetained(false);
//        return messageHandler;
//    }
//}
//

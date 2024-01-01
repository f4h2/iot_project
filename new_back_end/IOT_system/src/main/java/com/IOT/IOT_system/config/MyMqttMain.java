//package com.IOT.IOT_system.config;
//import com.IOT.IOT_system.model.RFID;
//import com.IOT.IOT_system.model.Stream_bit;
//import com.IOT.IOT_system.repository.RfidRepository;
//import com.IOT.IOT_system.repository.Stream_bit_Repo;
//import com.IOT.IOT_system.service.RFID_Service;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hivemq.client.mqtt.MqttClient;
//import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Date;
//import java.util.Optional;
//
//import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
//import static java.nio.charset.StandardCharsets.UTF_8;
//
//public class MyMqttMain {
//    @Autowired
//    private static RFID_Service rfid_Service ;
//    @Autowired
//    private static RfidRepository rfidRepo;
//    @Autowired
//    private  ObjectMapper mapper;
//    private static Stream_bit_Repo SB_repo;
//    public static void main(String[] args) throws Exception {
//
//        final String host = "820d38034a484dc4924168e678006e8f.s2.eu.hivemq.cloud";
//        final String username = "loc.nkt204763";
//        final String password = "thegioinaylasao2002";
//
//        // create an MQTT client
//        final Mqtt5BlockingClient client = MqttClient.builder()
//                .useMqttVersion5()
//                .serverHost(host)
//                .serverPort(8883)
//                .sslWithDefaultConfig()
//                .buildBlocking();
//
//        // connect to HiveMQ Cloud with TLS and username/pw
//        client.connectWith()
//                .simpleAuth()
//                .username(username)
//                .password(UTF_8.encode(password))
//                .applySimpleAuth()
//                .send();
//
//        System.out.println("Connected successfully");
//
//        // subscribe to the topic "my/test/topic"
//        client.subscribeWith()
//                .topicFilter("UID-Out")
//                .send();
//
//        client.subscribeWith()
//                .topicFilter("UID-In")
//                .send();
//
//        client.subscribeWith()
//                .topicFilter("pos")
//                .send();
//
//        // set a callback that is called when a message is received (using the async API style)
//        client.toAsync().publishes(ALL, publish -> {
//            String topic = publish.getTopic().toString();
//            String payload = UTF_8.decode(publish.getPayload().get()).toString();
//
//            switch (topic) {
//                        case "UID-In":
//                            Optional<RFID> RFID_I = rfidRepo.findByIDx(payload);
//                             final Logger log = LogManager.getLogger(MyMqttMain.class);
//                            if (RFID_I.isPresent()) {
//                                break;
//                            } else {
//                                RFID rfid = new RFID();
//                                Date time = new Date();
//                                rfid.setDate(time);
//                                rfid.setIDx(payload);
//
//                                rfid_Service.save(rfid);
//                                log.info(rfid.toString());
//
//                            }
//                            System.out.println("Received message: " +
//                                    publish.getTopic() + " -> " +
//                                    UTF_8.decode(publish.getPayload().get()));
//                            break;
//                        case "UID-Out":
//                            Optional<RFID> RFID_O = rfidRepo.findByIDx(payload);
//                            if (RFID_O.isPresent()) {
//                                rfidRepo.deleteRFIDBy(payload);
//                            } else {
//
//                                break;
//
//                            }
//                            System.out.println("Received message: " +
//                                    publish.getTopic() + " -> " +
//                                    UTF_8.decode(publish.getPayload().get()));
//                            break;
//                        case "pos":
//                            Stream_bit x = new Stream_bit();
//                            x.setStreamBit(payload);
//                            SB_repo.save(x);
//                            System.out.println("Received message: " +
//                                    publish.getTopic() + " -> " +
//                                    UTF_8.decode(publish.getPayload().get()));
//
//                            break;
//                        default:
//                            break;
//
//                    }
//            client.disconnect();
//        });
//
//        // publish a message to the topic "my/test/topic"
//        client.publishWith()
//                .topic("my/test/topic")
//                .payload(UTF_8.encode("Hello"))
//                .send();
//    }
//}
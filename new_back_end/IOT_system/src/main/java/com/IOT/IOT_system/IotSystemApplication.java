



//	@Bean
//	public WebMvcConfigurer configure() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry reg) {
//				reg.addMapping("/**").allowedOrigins("*");
//			}
//		};
//
//	}

//	@Configuration
//	class OutConfiguration{
//		@Bean
//		RouterFunction<ServerResponse> http(MessageChannel out){
//			return route()
//					.GET("/SEND/{name}", request =>{
//						var name :String = request.pathVariable("name");
//						var message :Message<String> = MessageBuilder.withPayload("hello, hivemq and"+name + "!").build();
//						out.send(message);
//						return ServerResponse.ok().build();
//
//			}).build();
//		}
//	}
package com.IOT.IOT_system;

import com.IOT.IOT_system.model.RFID;
import com.IOT.IOT_system.model.RFID_out;
import com.IOT.IOT_system.model.Stream_bit;
import com.IOT.IOT_system.repository.RfidRepository;
import com.IOT.IOT_system.repository.Rfid_out;
import com.IOT.IOT_system.repository.Stream_bit_Repo;
import com.IOT.IOT_system.service.RFID_Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

@SpringBootApplication
public class IotSystemApplication {
    @Autowired
    private RFID_Service rfidService;
    @Autowired
    private RfidRepository rfidRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Stream_bit_Repo streamBitRepo;
    @Autowired
    private Rfid_out rfid_out;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(IotSystemApplication.class, args);
        IotSystemApplication mqttMain = context.getBean(IotSystemApplication.class);
        mqttMain.runMqttClient();
    }



    public void runMqttClient() {
        final String host = "820d38034a484dc4924168e678006e8f.s2.eu.hivemq.cloud";
        final String username = "loc.nkt204763";
        final String password = "thegioinaylasao2002";

        /**
         * Building the client with ssl.
         */
        final Mqtt5BlockingClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(8884)
                .sslWithDefaultConfig()
                .webSocketConfig()
                .serverPath("mqtt")
                .applyWebSocketConfig()
                .buildBlocking();

        /**
         * Connect securely with username, password.
         */
        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();

        System.out.println("Connected successfully");

        /**
         * Subscribe to the topic "my/test/topic" with qos = 2 and print the received message.
         */

        client.subscribeWith()
                .topicFilter("UID-Out")
                .qos(MqttQos.EXACTLY_ONCE)
                .send();

        // subscribe to the topic "UID-In"
        client.subscribeWith()
                .topicFilter("UID-In")
                .qos(MqttQos.EXACTLY_ONCE)
                .send();

        // subscribe to the topic "pos"
        client.subscribeWith()
                .topicFilter("pos")
                .qos(MqttQos.EXACTLY_ONCE)
                .send();

        /**
         * Set a callback that is called when a message is received (using the async API style).
         * Then disconnect the client after a message was received.
         */
        client.toAsync().publishes(ALL,publish -> {
            String topic = publish.getTopic().toString();
            String payload = new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8);

            switch (topic) {
                case "UID-In":
                    Optional<RFID> rfidOptional = rfidRepository.findByIDx(payload);
                    if (!rfidOptional.isPresent()) {
                        RFID rfid = new RFID();
                        Date time = new Date();
                        rfid.setDate(time);
                        rfid.setIDx(payload);

                        rfidService.save(rfid);
                        System.out.println(rfid.toString());
                    }
                    System.out.println("Received message: " +
                            publish.getTopic() + " -> " +
                            new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8));
                    break;
                case "UID-Out":
                    Optional<RFID> rfidOutOptional = rfidRepository.findByIDx(payload);
                    if (rfidOutOptional.isPresent()) {
                        rfidRepository.deleteRFID(payload);
//                       Long x = rfidRepository.deleteByIDx(payload);
                        RFID_out x = new RFID_out();
                        Date time = new Date();
                        x.setDate_out(time);
                        x.setID_out(payload);
                        rfid_out.save(x);

                    }
                    System.out.println("Received message: " +
                            publish.getTopic() + " -> " +
                            new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8));
                    break;
                case "pos":
                    Stream_bit streamBit = new Stream_bit();
                    streamBit.setStreamBit(payload);
                    streamBitRepo.save(streamBit);
                    System.out.println("Received message: " +
                            publish.getTopic() + " -> " +
                            new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8));
                    break;
                default:
                    break;
            }
        });

        /**
         * Publish "Hello" to the topic "my/test/topic" with qos = 2.
         */
        client.publishWith()
                .topic("my/test/topic")
                .payload(UTF_8.encode("Hello"))
                .qos(MqttQos.EXACTLY_ONCE)
                .send();
    }
    }


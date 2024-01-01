//package com.IOT.IOT_system.config;
//
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.springframework.stereotype.Service;
//
//import javax.net.ssl.SSLSocketFactory;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
//@Service
//public class MqttPublisherServiceImpl implements MqttPublisherService, MqttCallback {
//
//    public static void publishPayload(MqttClient client, Object object, String topic) throws MqttException {
//        client.publish(
//                topic,
//                CultzymeUtils.objectToJson(object).getBytes(UTF_8),
//                2, // QoS = 2
//                false);
//    }
//
//    public MqttClient getMqttConnection() throws MqttException {
//        MqttClient client = new MqttClient(
//                "", // serverURI in format: "protocol://name:port"
//                MqttClient.generateClientId(), // ClientId
//                new MemoryPersistence()); // Persistence
//
//        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
//        mqttConnectOptions.setUserName("my_user");
//        mqttConnectOptions.setPassword("my_pass".toCharArray());
//        mqttConnectOptions.setSocketFactory(SSLSocketFactory.getDefault());
//        client.setCallback(this);
//        client.connect(mqttConnectOptions);
//        client.subscribe("test/topic");
//        return client;
//    }
//
//    @Override
//    public Boolean publishCultzymeData(CultzymeMqttActuatorsData cultzymeMqttActuatorsData, CultzymeMqttTargetTemperatureData cultzymeMqttTargetTemperatureData) {
//        try {
//            MqttClient client = this.getMqttConnection();
//            publishPayload(client, cultzymeMqttActuatorsData, MqttPublisherTopics.CULTZYME_MQTT_ACTUATORS);
//            publishPayload(client, cultzymeMqttTargetTemperatureData, MqttPublisherTopics.CULTZYME_MQTT_TARGET_TEMPERATURE);
//            //client.disconnect();
//        } catch (MqttException e) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void connectionLost(Throwable throwable) {
//
//    }
//
//    @Override
//    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
//        System.out.println("Message arrived" + mqttMessage.toString());
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//
//    }
//}
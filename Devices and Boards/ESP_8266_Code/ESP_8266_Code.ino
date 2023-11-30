#include <SPI.h>
#include <MFRC522.h>
#include <Servo.h>
#include <map>
#include <PubSubClient.h>
#include <ESP8266WiFi.h>

#define SS_PIN 15
#define RST_PIN 3
#define MAX_SIZE 2
// WIFI
const char* ssid = "TOKHANH";
const char* password = "1234abcd";
// MQTT
const char* mqttServer = "broker.hivemq.com";
const int mqttPort = 1883;
const char* mqtt_username = "your_mqtt_client_username";
const char* mqtt_password = "your_mqtt_client_password";
WiFiClient espClient;
PubSubClient client(espClient);

std::map<String,int> isIn;
MFRC522::MIFARE_Key key;
MFRC522 rfid(SS_PIN, RST_PIN); // Instance of the class

String uid;
Servo servo;  // create servo object to control a servo
Servo servo2;  // create servo object to control a servo
const int servoPin = 4;
const int servoP = 5;

int slot = 0;
int count = 0 ;
// Init array that will store new NUID
byte nuidPICC[4];
int ir = 16;
int ir_in = 10;
int ir_out = 0;
int ir5 = 2;
int flag_open = 0;   // check ir open status
int flag_close = 0;  // check ir close status 

void setup() {
 	Serial.begin(9600);
  pinMode(ir, INPUT);
  pinMode(ir_in, INPUT);
  pinMode(ir_out, INPUT);
  pinMode(ir5, INPUT);

 	SPI.begin(); // Init SPI bus
 	rfid.PCD_Init(); // Init MFRC522
 	Serial.println();
 	Serial.print(F("Reader :"));
 	rfid.PCD_DumpVersionToSerial();
  servo.attach(servoPin);
  servo2.attach(servoP);
  // WIFI
  setup_wifi();
  client.setServer(mqttServer, mqttPort);

 	for (byte i = 0; i < 6; i++) {
 			key.keyByte[i] = 0xFF;
 	}
}

// DONE
void setup_wifi() {
  delay(10);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void reconnect() {
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    String clientId = "ESP8266Client-";   // Create a random client ID
    clientId += String(random(0xffff), HEX);  // Need to fix
    if (client.connect(clientId.c_str(), mqtt_username, mqtt_password)) {
      Serial.println("connected");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      delay(5000);
    }
  }
}

// Main
void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  count++;
  int p1 = digitalRead(ir);
  int x_open = digitalRead(ir_in);
  int x_close = digitalRead(ir_out);
  int p2 = digitalRead(ir5);

  // delay 2s - 1 message send
  String position = String(p1)+"-"+String(p2);
  if(count % 200 == 0 ){
    Serial.print("Carpark Number 1 - 2: ");
    
    Serial.println(position);
    delay(500);
  }
  if(count % 200 == 0)  client.publish("pos", position.c_str());
  if(count == 800) count = 0;

  // Close O
  if(x_open==1 && flag_open==1){
    flag_open=0;
    for (int pos = 180; pos >= 0; pos -= 1) {
      servo.write(pos);
      delay(3);
    }
    slot++;
    delay(200);
  }

  // Close C
  if(x_close==1 && flag_close==1){
    flag_close=0;
    for (int pos = 180; pos >= 0; pos -= 1) {
      servo2.write(pos);
      delay(3);
    }
    slot--;
    delay(200);
  }

 	// Reset the loop if no new card present on the sensor/reader. This saves the entire process when idle.
 	if ( ! rfid.PICC_IsNewCardPresent())
 			return;
 	// Verify if the NUID has been readed
 	if ( ! rfid.PICC_ReadCardSerial())
 			return;

 	if (rfid.uid.uidByte[0] != nuidPICC[0]  ) {
 			uid = convertToHexString(rfid.uid.uidByte, rfid.uid.size);
      uid.toUpperCase();
      Serial.println(uid);
      if(uid == " 23 7A 69 04" || uid == " 63 E4 20 0D")  {  // If UID is invalid

        if(slot >= MAX_SIZE)  
          Serial.print(F("The car park is Full !"));
        // Open O
        if(x_open==0 && flag_open==0 && isIn[uid]!=1 && slot < MAX_SIZE) {
          flag_open=1;
          isIn[uid]=1;
          Serial.print(F("The UID get In: "));
          Serial.print(uid);
          client.publish("UID-In", uid.c_str());  // Send message
          for (int pos = 0; pos <= 180; pos += 1) {
            servo.write(pos);
            delay(3);
          }
          delay(200);
        }

        // Open C
        if(x_close==0 && flag_close==0 && isIn[uid]==1){
          flag_close = 1;
          isIn[uid] = 0;
          Serial.print(F("The UID get OUT: "));
          Serial.print(uid);
          client.publish("UID-Out", uid.c_str());  // Send message
          
          for (int pos = 0; pos <= 180; pos += 1) {
            servo2.write(pos);
            delay(3);
          }
          delay(200);
        }
      }
 	}
  Serial.println();
  delay(500);
}


String convertToHexString(byte *buffer, byte bufferSize) {
  String result;

  for (byte i = 0; i < bufferSize; i++) {
    result += (buffer[i] < 0x10 ? " 0" : " ");
    result += String(buffer[i], HEX);
  }
  return result;
}



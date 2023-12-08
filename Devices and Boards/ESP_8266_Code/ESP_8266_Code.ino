#include <SPI.h>
#include <MFRC522.h>
#include <Servo.h>
#include <map>
#include <PubSubClient.h>
#include <ESP8266WiFi.h>
#include <WiFiClientSecure.h>

#define SS_PIN 15
#define RST_PIN 3
#define MAX_SIZE 2

const char* ssid = "TOKHANH";
const char* password = "1234abcd";
const char* mqttServer = "bd5df580e574426986dde62f9307c993.s2.eu.hivemq.cloud";
const int mqttPort = 8883;
const char* mqttUser = "tocaohoang";
const char* mqttPassword = "Ngay20thang8";
WiFiClientSecure  espClient;
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

static const char *root_ca PROGMEM = R"EOF(
-----BEGIN CERTIFICATE-----
MIIFazCCA1OgAwIBAgIRAIIQz7DSQONZRGPgu2OCiwAwDQYJKoZIhvcNAQELBQAw
TzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh
cmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwHhcNMTUwNjA0MTEwNDM4
WhcNMzUwNjA0MTEwNDM4WjBPMQswCQYDVQQGEwJVUzEpMCcGA1UEChMgSW50ZXJu
ZXQgU2VjdXJpdHkgUmVzZWFyY2ggR3JvdXAxFTATBgNVBAMTDElTUkcgUm9vdCBY
MTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAK3oJHP0FDfzm54rVygc
h77ct984kIxuPOZXoHj3dcKi/vVqbvYATyjb3miGbESTtrFj/RQSa78f0uoxmyF+
0TM8ukj13Xnfs7j/EvEhmkvBioZxaUpmZmyPfjxwv60pIgbz5MDmgK7iS4+3mX6U
A5/TR5d8mUgjU+g4rk8Kb4Mu0UlXjIB0ttov0DiNewNwIRt18jA8+o+u3dpjq+sW
T8KOEUt+zwvo/7V3LvSye0rgTBIlDHCNAymg4VMk7BPZ7hm/ELNKjD+Jo2FR3qyH
B5T0Y3HsLuJvW5iB4YlcNHlsdu87kGJ55tukmi8mxdAQ4Q7e2RCOFvu396j3x+UC
B5iPNgiV5+I3lg02dZ77DnKxHZu8A/lJBdiB3QW0KtZB6awBdpUKD9jf1b0SHzUv
KBds0pjBqAlkd25HN7rOrFleaJ1/ctaJxQZBKT5ZPt0m9STJEadao0xAH0ahmbWn
OlFuhjuefXKnEgV4We0+UXgVCwOPjdAvBbI+e0ocS3MFEvzG6uBQE3xDk3SzynTn
jh8BCNAw1FtxNrQHusEwMFxIt4I7mKZ9YIqioymCzLq9gwQbooMDQaHWBfEbwrbw
qHyGO0aoSCqI3Haadr8faqU9GY/rOPNk3sgrDQoo//fb4hVC1CLQJ13hef4Y53CI
rU7m2Ys6xt0nUW7/vGT1M0NPAgMBAAGjQjBAMA4GA1UdDwEB/wQEAwIBBjAPBgNV
HRMBAf8EBTADAQH/MB0GA1UdDgQWBBR5tFnme7bl5AFzgAiIyBpY9umbbjANBgkq
hkiG9w0BAQsFAAOCAgEAVR9YqbyyqFDQDLHYGmkgJykIrGF1XIpu+ILlaS/V9lZL
ubhzEFnTIZd+50xx+7LSYK05qAvqFyFWhfFQDlnrzuBZ6brJFe+GnY+EgPbk6ZGQ
3BebYhtF8GaV0nxvwuo77x/Py9auJ/GpsMiu/X1+mvoiBOv/2X/qkSsisRcOj/KK
NFtY2PwByVS5uCbMiogziUwthDyC3+6WVwW6LLv3xLfHTjuCvjHIInNzktHCgKQ5
ORAzI4JMPJ+GslWYHb4phowim57iaztXOoJwTdwJx4nLCgdNbOhdjsnvzqvHu7Ur
TkXWStAmzOVyyghqpZXjFaH3pO3JLF+l+/+sKAIuvtd7u+Nxe5AW0wdeRlN8NwdC
jNPElpzVmbUq4JUagEiuTDkHzsxHpFKVK7q4+63SM1N95R1NbdWhscdCb+ZAJzVc
oyi3B43njTOQ5yOf+1CceWxG1bQVs5ZufpsMljq4Ui0/1lvh+wjChP4kqKOJ2qxq
4RgqsahDYVvTH9w7jXbyLeiNdd8XM2w9U/t7y0Ff/9yi0GE44Za4rF2LN9d11TPA
mRGunUHBcnWEvgJBQl9nJEiU0Zsnvgc/ubhPgXRR4Xq37Z0j4r7g1SgEEzwxA57d
emyPxgcYxn/eR44/KJ4EBs+lVDR3veyJm+kXQ99b21/+jh5Xos1AnX5iItreGCc=
-----END CERTIFICATE-----
)EOF";

// void callback(char* topic, byte* payload, unsigned int length) {
//   String incommingMessage = "";
//   for (int i = 0; i < length; i++) incommingMessage+=(char)payload[i];

//   Serial.println("Message arrived ["+String(topic)+"]"+incommingMessage);

//   //--- check the incomming message
//     if( strcmp(topic,"led_state") == 0){
//      if (incommingMessage.equals("1")) digitalWrite(led, HIGH);   // Turn the LED on
//      else digitalWrite(led, LOW);  // Turn the LED off
//   }

// }

void publishMessage(const char* topic, String payload , boolean retained){
  if (client.publish(topic, payload.c_str(), true))
      Serial.println("Message publised ["+String(topic)+"]: "+payload);
}

// DONE
void setup_wifi() {
  delay(10);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
   WiFi.mode(WIFI_STA); 
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
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    String clientId = "ESP8266Client-";   // Create a random client ID
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (client.connect(clientId.c_str(), mqttUser, mqttPassword)) {
      Serial.println("connected");

    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");   // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

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
  #ifdef ESP8266
    espClient.setInsecure();
  #else
    espClient.setCACert(root_ca);      // enable this line and the the "certificate" code for secure connection
  #endif

  client.setServer(mqttServer, mqttPort);
  // client.setCallback(callback);
 	for (byte i = 0; i < 6; i++) {
 			key.keyByte[i] = 0xFF;
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
  if(count % 50 == 0 ){
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
      // else Serial.print(F("Invalid Card!!!"));
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


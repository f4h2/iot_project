#include <SPI.h>
#include <MFRC522.h>
#include <Servo.h>
#include <map>
// #include <fs
#define SS_PIN 15
#define RST_PIN 3
#define MAX_SIZE 2
int slot = 0;
MFRC522 rfid(SS_PIN, RST_PIN); // Instance of the class
String uid;
std::map<String,int> isIn;
MFRC522::MIFARE_Key key;
int count = 0 ;
Servo servo;  // create servo object to control a servo
Servo servo2;  // create servo object to control a servo
const int servoPin = 4;
const int servoP = 5;

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
 	for (byte i = 0; i < 6; i++) {
 			key.keyByte[i] = 0xFF;
 	}
 	Serial.println();
 	Serial.println(F("This code scan the MIFARE Classic NUID."));
}

void loop() {
  count++;
  int p1 = digitalRead(ir);
  int x_open = digitalRead(ir_in);
  int x_close = digitalRead(ir_out);
  int p2 = digitalRead(ir5);

  if(count == 100) count = 1;
  if(count % 10 == 0 ){
    Serial.print("Carpark Number 1 - 2: ");
    String position = String(p1)+" - "+String(p2);
    Serial.println(position);
    delay(500);
  }
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

      if(uid == "23 7A 69 04" || uid == "63 E4 20 0D")  {
        // Open O
        if(slot >= 2)  
          Serial.print(F("The car park is Full !"));

        if(x_open==0 && flag_open==0 && isIn[uid]!=1 && slot < 2) {
          flag_open=1;
          isIn[uid]=1;
          Serial.print(F("The UID get In: "));
          Serial.print(uid);
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
          for (int pos = 0; pos <= 180; pos += 1) {
            servo2.write(pos);
            delay(3);
          }
          delay(200);
        }

      }
      else Serial.print(F("Invalid Card!!!"));
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



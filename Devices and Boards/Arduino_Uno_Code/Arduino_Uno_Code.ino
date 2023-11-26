#define VCC_SenSor1     3    // define new vcc for ir open gate pin
#define GND_SenSor1     2    // define new gnd for ir open gate pin

#define VCC_SenSor2     5    // define new vcc for ir open gate pin
#define GND_SenSor2     4    // define new gnd for ir open gate pin

#define VCC_SenSor3     7    // define new vcc for ir open gate pin
#define GND_SenSor3     6    // define new gnd for ir open gate pin

#define VCC_SenSorI     9    // define new vcc for ir open gate pin
#define GND_SenSorI     8    // define new gnd for ir open gate pin

#define VCC_SenSorO     11    // define new vcc for ir open gate pin
#define GND_SenSorO     10    // define new gnd for ir open gate pin
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(VCC_SenSor1,OUTPUT);     // define a digital pin as output
  digitalWrite(VCC_SenSor1,HIGH);  // set the above pin as high
  pinMode(GND_SenSor1,OUTPUT);     // define a digital pin as output
  digitalWrite(GND_SenSor1,LOW);   // set the above pin as low

  pinMode(VCC_SenSor2,OUTPUT);     // define a digital pin as output
  digitalWrite(VCC_SenSor2,HIGH);  // set the above pin as high
  pinMode(GND_SenSor2,OUTPUT);     // define a digital pin as output
  digitalWrite(GND_SenSor2,LOW);   // set the above pin as low

  pinMode(VCC_SenSor3,OUTPUT);     // define a digital pin as output
  digitalWrite(VCC_SenSor3,HIGH);  // set the above pin as high
  pinMode(GND_SenSor3,OUTPUT);     // define a digital pin as output
  digitalWrite(GND_SenSor3,LOW);   // set the above pin as low

  pinMode(VCC_SenSorI,OUTPUT);     // define a digital pin as output
  digitalWrite(VCC_SenSorI,HIGH);  // set the above pin as high
  pinMode(GND_SenSorI,OUTPUT);     // define a digital pin as output
  digitalWrite(GND_SenSorI,LOW);   // set the above pin as low

  pinMode(VCC_SenSorO,OUTPUT);     // define a digital pin as output
  digitalWrite(VCC_SenSorO,HIGH);  // set the above pin as high
  pinMode(GND_SenSorO,OUTPUT);     // define a digital pin as output
  digitalWrite(GND_SenSorO,LOW);   // set the above pin as low
}

void loop() {
  // put your main code here, to run repeatedly:
  //
}
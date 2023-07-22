#define BUTTON 2

void setup() {
  Serial.begin(9600);
  pinMode(BUTTON, INPUT_PULLUP);
}

void loop() {
  if (digitalRead(BUTTON) == HIGH) {
    Serial.println(0);
  } else {
    Serial.println(1);
  }

  
  
  delay(50);
}

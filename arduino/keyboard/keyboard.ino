#define BUTTON_COUNT 24

int buttons[BUTTON_COUNT] = {
 30, 32, 34, 36, 38, 40, // left top
 31, 33, 35, 37, 39, 41, // left bottom
 52, 50, 48, 46, 44, 42, // right top
 53, 51, 49, 47, 45, 43  // right bottom
};

unsigned char buttonBytes[3] = { 0, 0, 0 };
unsigned char codeBytes[3] = { 0, 0, 0 };

void setup() 
{
  Serial.begin(9600);

  int buttonIndex;
  for ( buttonIndex = 0; buttonIndex < BUTTON_COUNT; buttonIndex++ ) {
    pinMode(buttons[buttonIndex], INPUT_PULLUP);
  }
}

int noButtonPresseed() 
{
  return buttonBytes[0] == 0 && buttonBytes[1] == 0 && buttonBytes[2] == 0;
}

void setCodeByte(int buttonIndex, int isUp) 
{
  int byteIndex = buttonIndex / 8;
  int bitIndex = 7 - (buttonIndex % 8);


  if (isUp) {
    buttonBytes[byteIndex] |= 1 << bitIndex;
    codeBytes[byteIndex] |= 1 << bitIndex;
  } else {
    buttonBytes[byteIndex] &= ~(1 << bitIndex);
  }
}

int codeBytesClear() 
{
  return codeBytes[0] == 0 && codeBytes[1] == 0 && codeBytes[2] == 0;
}

void clearCodeBytes()
{
  codeBytes[0] = 0;
  codeBytes[1] = 0;
  codeBytes[2] = 0;
}

void printCodeBytes()
{
  Serial.print(codeBytes[0]);
  Serial.print(codeBytes[1]);
  Serial.print(codeBytes[2]);
  Serial.println();
}

void listenToPins()
{
  int buttonIndex;
  for ( buttonIndex = 0; buttonIndex < BUTTON_COUNT; buttonIndex++ ) {
    int isUp = ! digitalRead(buttons[buttonIndex]) == HIGH;
    setCodeByte(buttonIndex, isUp);
  }
}

void loop() 
{
  listenToPins();

  if (noButtonPresseed()) {
    if (! codeBytesClear()) {
      printCodeBytes();
      clearCodeBytes();
    }
  }

  delay(10);
}
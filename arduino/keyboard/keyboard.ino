#define BUTTON_COUNT 24

int buttons[BUTTON_COUNT] = {
  53, 52, 51, 50, 49, 48, // left top
  47, 46, 45, 44, 43, 42, // left bottom
  41, 40, 39, 38, 37, 36, // right top
  35, 34, 33, 32, 31, 30  // right bottom
};

unsigned char buttonBytes[3] = { 0, 0, 0 };
unsigned char codeBytes[3] = { 0, 0, 0 };

void setup() 
{
  Serial.begin(9600, SERIAL_8N1);

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
  Serial.print(" ");
  Serial.print(codeBytes[1]);
  Serial.print(" ");
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
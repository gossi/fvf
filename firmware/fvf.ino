String cmdline = "";
int leds[5] = {-1, 8, 9, 10, 11};
boolean flickering = false;
boolean flickerLedOn = false;
int flickerLed;
int flickerDelay;


void setup() {
  Serial.begin(9600);
  for (int i = 1; i <= 4; i++) {
    pinMode(leds[i], OUTPUT);
  }
}

void loop() {
  // flickers, if flicker led is on
  if (flickering) {
    if (flickerLedOn) {
      ledOff(flickerLed);
    } else {
      ledOn(flickerLed);
    }
    
    flickerLedOn = !flickerLedOn;
    delay(flickerDelay);
  }
}

void serialEvent() {
  if (Serial.available()) {
    char input = (char)Serial.read();
    
    if (input == '\n') {
      parseCommand(cmdline);
      cmdline = "";
    } else {
      cmdline += input;
    }    
  }
}

void parseCommand(String cmd) {
  bool found = false;
  String command;
  String tail;
  cmd.trim();
  
  for (int i = 0; i < cmd.length() && !found; i++) {
    if (cmd.substring(i, i + 1) == " ") {
      found = true;
      command = cmd.substring(0, i);
      tail = cmd.substring(i + 1);
    }
  }
  
  if (!found) {
    command = cmd;
  }
      
  if (command == "on") {
    if (tail == "") {
      return error(1);
    }
    return cmdLedOn(tail.toInt());
  }
          
  else if (command == "off") {
    if (tail == "") {
      return error(1);
    }
    return cmdLedOff(tail.toInt());
  }
      
  else if (command == "measurement") {
    // parse flicker commands: measurement [mode] [flickerLed] [frequency] [onDuration] [offDuration]
    String params[5] = {};
    int lastDelim = 0;
    int p = 0;
    for (int t = 0; t < tail.length(); t++) {
      if (tail.substring(t, t + 1) == " ") {
        params[p] = tail.substring(lastDelim, t);
        lastDelim = t + 1;
        p++;
      } else if (t == tail.length() - 1) {
        params[p] = tail.substring(lastDelim);
      }
    }
    
    if (p < 4) {
      return error(3);
    }

    return cmdMeasurement(params[0].toInt(), params[1].toInt(), params[2].toFloat(), params[3].toInt(), params[4].toInt());
  }
  
  else if (command == "flicker") {
    String params[2] = {};
    int lastDelim = 0;
    int p = 0;
    for (int t = 0; t < tail.length(); t++) {
      if (tail.substring(t, t + 1) == " ") {
        params[p] = tail.substring(lastDelim, t);
        lastDelim = t + 1;
        p++;
      } else if (t == tail.length() - 1) {
        params[p] = tail.substring(lastDelim);
      }
    }
    
    if (p < 1) {
      return error(4);
    }
    
    return cmdFlicker(params[0].toInt(), params[1].toFloat());
  }

  else if (command == "ping") {
    if (tail == "") {
      Serial.println("pong");
    } else {
      Serial.print("pong ");
      Serial.println(tail);
    }
    return;
  }
      
  return error(2);
}

void ledOn(int led) {
  digitalWrite(leds[led], HIGH);
}

void ledOff(int led) {
  digitalWrite(leds[led], LOW);
}

void ledsOn(int mode) {
  for (int i = 1; i <= mode; i++) {
    ledOn(i);
  }
  Serial.print("ons ");
  Serial.println(mode);
}

void ledsOff(int mode) {
  for (int i = 1; i <= mode; i++) {
    ledOff(i);
  }
  Serial.print("offs ");
  Serial.println(mode);
}

void ledFlicker(int led, float frequency, int duration) {
  frequency = 1 / frequency * 1000 / 2;
  int timePassed = 0;
  int on = false;
  
  while (timePassed < duration) {
    if (on) {
      ledOff(led);
    } else {
      ledOn(led);
    }
    
    on = !on;
    
    int cycle = timePassed + frequency > duration ? duration - timePassed : frequency;
    timePassed += frequency;
    
    delay(cycle);
  }
}

void error(int errorNumber) {
  Serial.print("error ");
  Serial.println(errorNumber);
}

void cmdLedOn(int led) {
  ledOn(led);
  Serial.print("on ");
  Serial.println(led);
}

void cmdLedOff(int led) {
  ledOff(led);
  Serial.print("off ");
  Serial.println(led);
  if (led == flickerLed) {
    flickerLedOn = false;
    flickering = false;
  }
}

void cmdFlicker(int led, float frequency) {
  if (flickering && led != flickerLed) {
    ledOff(flickerLed);
    Serial.print("off ");
    Serial.println(flickerLed);
    flickerLedOn = false;
  }
  flickering = true;
  flickerDelay = 1 / frequency * 1000 / 2;
  flickerLed = led;
  
  Serial.print("flicker ");
  Serial.println(led);
}

void cmdMeasurement(int mode, int flickerLed, float frequency, int onDuration, int offDuration) {
  Serial.println("measurement on");
  ledsOff(mode);
  delay(offDuration);
  ledsOn(mode);
  delay(onDuration);
  ledsOff(mode);
  
  for (int i = 1; i <= mode; i++) {
    Serial.print("on ");
    Serial.println(i);
    if (i == flickerLed) {
      ledFlicker(i, frequency, onDuration);
    } else {
      ledOn(i);
      delay(onDuration);
    }
    Serial.print("off ");
    Serial.println(i);
    ledOff(i);
    delay(offDuration);
  }

  ledsOn(mode);
  Serial.println("measurement off");
}

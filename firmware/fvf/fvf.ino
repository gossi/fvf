String cmdline = "";
int leds[5] = {-1, 8, 9, 10, 11};
const int LED_ON_FREQUENCY = 100;

/* -- ---------------------------------------------------------------------------------- -- */
/* -- Stuff for the loop                                                                 -- */
/* -- ---------------------------------------------------------------------------------- -- */

/**
 * Setup the Leds
 */
void setup() {
  Serial.begin(9600);
  for (int i = 1; i <= 4; i++) {
    pinMode(leds[i], OUTPUT);
  }
}

/**
 * The main loop - does nothing
 */
void loop() {
}

/**
 * Processing incoming messages on the Serial Port
 */
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

/**
 * This function parses all incoming commands and decides what to do next
 */
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
  
  // command: on [led]    
  if (command == "on") {
    if (tail == "") {
      return error(1);
    }
    return cmdLedOn(tail.toInt());
  }

  // command: off [led]
  else if (command == "off") {
    if (tail == "") {
      return error(1);
    }
    return cmdLedOff(tail.toInt());
  }
  
  // command: measurement [mode] [flickerLed] [frequency] [onDuration] [offDuration] [[light]] [[dark]]
  else if (command == "measurement") {
    String params[7] = {};
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
    
    if (p < 4 || p == 5) {
      return error(3);
    }
    
    // set default light:dark ratio
    if (p < 4) {
      params[5] = "1";
      params[6] = "1";
    }

    return cmdMeasurement(params[0].toInt(), params[1].toInt(), params[2].toFloat(), params[3].toInt(), params[4].toInt(), params[5].toInt(), params[6].toInt());
  }
  
  // command: flicker [led] [frequency] [duration] [[light]] [[dark]]
  else if (command == "flicker") {
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
    
    if (p < 2 || p == 3) {
      return error(4);
    }
    
    // set default light:dark ratio
    if (p < 2) {
      params[3] = "1";
      params[4] = "1";
    }
    
    return ledFlicker(params[0].toInt(), params[1].toFloat(), params[2].toInt(), params[3].toInt(), params[4].toInt());
  }

  // command: ping [[sequence]]
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

/* -- ---------------------------------------------------------------------------------- -- */
/* -- Internal helper stuff                                                              -- */
/* -- ---------------------------------------------------------------------------------- -- */

/**
 * Turns on the given led
 *
 * @param led the given led (a value of 1 to 4)
 */
void ledOn(int led) {
  digitalWrite(leds[led], HIGH);
}

/**
 * Turns off the given led
 *
 * @param led the given led (a value of 1 to 4)
 */
void ledOff(int led) {
  digitalWrite(leds[led], LOW);
}

/**
 * Turns on multiple leds, depending on the given mode
 *
 * @param mode the mode can either be 2 or 4
 */
void ledsOn(int mode) {
  for (int i = 1; i <= mode; i++) {
    ledOn(i);
  }
  Serial.print("ons ");
  Serial.println(mode);
}

/**
 * Turns off multiple leds, depending on the given mode
 *
 * @param mode the mode can either be 2 or 4
 */
void ledsOff(int mode) {
  for (int i = 1; i <= mode; i++) {
    ledOff(i);
  }
  Serial.print("offs ");
  Serial.println(mode);
}

/**
 * Flickers a led at a given frequency, duration and light:dark ratio
 *
 * @param led the led
 * @param frequency the frequency [hz]
 * @param duration the duration [ms]
 * @param light the light ratio part
 * @param dark the dark ratio part
 */
void ledFlicker(int led, float frequency, int duration, int light, int dark) {
  // transform hz to ms for one cycle
  float cycleTime = 1 / frequency * 1000;
  int timePassed = 0;
  int on = false;
  float onDuration = (float)light / ((float)light + (float)dark) * cycleTime;
  float offDuration = (float)dark / ((float)light + (float)dark) * cycleTime;
  int halfCycleTime = 0;
  
  while (timePassed < duration) {
    if (on) {
      ledOff(led);
      halfCycleTime = offDuration;
    } else {
      ledOn(led);
      halfCycleTime = onDuration;
    }
    
    on = !on;
    
    int cycle = timePassed + halfCycleTime > duration ? duration - timePassed : halfCycleTime;
    timePassed += cycle;
    
    delay(cycle);
  }
}

/**
 * Called whenever an error occurs
 *
 * @param errorNumber the error number
 */
void error(int errorNumber) {
  Serial.print("error ");
  Serial.println(errorNumber);
}

/* -- ---------------------------------------------------------------------------------- -- */
/* -- Command Handler                                                                    -- */
/* -- ---------------------------------------------------------------------------------- -- */

/**
 * Command: led on
 * 
 * Turns on the given led and sends a message to the serial port of turning the led on.
 *
 * @param led the led
 */
void cmdLedOn(int led) {
  ledOn(led);
  Serial.print("on ");
  Serial.println(led);
}

/**
 * Command: led off
 * 
 * Turns off the given led and sends a message to the serial port of turning the led off.
 *
 * @param led the led
 */
void cmdLedOff(int led) {
  ledOff(led);
  Serial.print("off ");
  Serial.println(led);
}

/**
 * Command: measurement
 *
 * Handles the complete measurement routine
 *
 * @param mode the mode either 2 or 4
 * @param flickerLed the flickering led in this measurement
 * @param onDuration the duration for how long a led is on [ms]
 * @param offDuration the duration for how long a led is off [ms]
 * @param light part of the light:dark ratio
 * @param dark part of the light:dark ratio
 */
void cmdMeasurement(int mode, int flickerLed, float frequency, int onDuration, int offDuration, int light, int dark) {
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
      ledFlicker(i, frequency, onDuration, light, dark);
    } else {
      ledFlicker(i, LED_ON_FREQUENCY, onDuration, light, dark);
    }
    Serial.print("off ");
    Serial.println(i);
    ledOff(i);
    delay(offDuration);
  }

  ledsOn(mode);
  Serial.println("measurement off");
}


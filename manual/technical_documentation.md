# Technical Documentation

## Pieces

The FVF measurement system contains of multiple pieces:

- Tube 
- Hardware LED controller (Arduino One)
- Client Software

## Components

The software is spread across multiple components written in multiple programming languages:

- Arduino Firmware (C++)
- LED Driver (Java)
- Client Software (Java)

## Communication

The client software talks to the Arduino board through a serial port connection. The rxtx ([1],[2]) interface is used.

[1]: http://playground.arduino.cc/Interfacing/Java
[2]: http://rxtx.qbang.org/

### Protocol

The communication operates on a protocol. The driver sends a command with arguments (input) and the hardware send feedback on what is happening (output: led on and off).

#### Schema

The following schema is used for every command, in every direction:

```
cmd [arg1] [arg2] ... [argN]
```

#### Input

Commands send to the hardware

##### on

Turns on a led

```
on [led]
```

__Arguments__

- `led` (int) - the led number

##### flicker

Flickers a led

```
flicker [led] [frequency]
```

__Arguments__

- `led` (int) - the led number
- `frequency` (int) - the flicker frequency [hz]


##### off

Turns off a led

```
off [led]
```

__Arguments__

- `led` (int) - the led number


##### measurement

Runs a measurement sequence.

```
measurement [mode] [flickerLed] [frequency] [onDuration] [offDuration]
```

__Arguments__

- `mode` (int) - `2` for two leds and `4` for four leds
- `flickerLed` (int) - Which led will flicker
- `frequency` (float) - The frequency for the flickering led [hz]
- `onDuration` (int) - The duration, the leds will be on [ms]
- `offDuration` (int) - The duration, the leds will be off [ms]


##### ping

Sends a ping

```
ping [seq*]
```

__Arguments__

- `seq*` (misc) - An optional sequence identifier (optional)

#### Output

Feedback received from the hardware.

##### on

Send when a led is turned on (or flickering, when measurement command was used).

```
on [led]
```

__Arguments__

- `led` (int) - the led number

##### flicker

Send when a led is flickering

```
flicker [led]
```

__Arguments__

- `led` (int) - the led number


##### off

Send when a led is turned off.

```
off [led]
```

__Arguments__

- `led` (int) - the led number

##### measurement

Send when a measurement is started or finished

```
measurement [state]
```

__Arguments__

- `state` (string) - `on` when the measurement started and `off` when it's finished

##### ons

Send when more than one led is turned on (during a measurement)

```
ons [mode]
```

__Arguments__

- `mode` (int) - is either 2 or 4, depending the first value of the `measurement` input command

##### offs

Send when more than one led is turned off (during a measurement)

```
offs [mode]
```

__Arguments__

- `mode` (int) - is either 2 or 4, depending the first value of the `measurement` input command

##### pong

Answers a ping with a pong

```
pong [seq]
```

__Arguments__

- `seq` (misc) - The returned sequence identifier (optional)

##### error

Send when an error occured

```
error [number]
```

__Arguments__

- `number` (int) - the error number


#### Error Codes

Error code explanation:

- `0` - Unknown Error
- `1` - Malformed command
- `2` - Unknown command
- `3` - Too few arguments for flicker command


## Troubleshooting

1) Getting a "Port in Use" exception on OSX, when connecting to the Arduino Board
-> See here: https://marcosc.com/2011/10/arduino-java-error-serial-port-already-in-use/

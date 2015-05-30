# Driver

The LED driver is a Java API to send commands to the FVF [firmware](firmware.md) and getting notified about feedback from the firmware.

## Communication

The client software talks to the Arduino board through a serial port connection. For this purpose the RXTX interface is used.

## References

- [RXTX](http://rxtx.qbang.org/)
- [Arduino Java Interface](http://playground.arduino.cc/Interfacing/Java)

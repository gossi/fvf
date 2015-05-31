LED Protocol
============

The firmware communicates with the protocol described here. The driver sends a command with arguments (:ref:`protocol-input`) and the hardware send feedback on what is happening (:ref:`protocol-output`).

Schema
------

The following schema is used for every command, in every direction::

  cmd [arg1] [arg2] ... [argN]

.. _protocol-input:

Input
-----

Commands send to the hardware

.. _protocol-input-on:

on
^^

Turns on a led::

  on [led]


**Arguments**

- ``led`` (int) - the led number

.. _protocol-input-flicker:

flicker
^^^^^^^

Flickers a led::

  flicker [led] [frequency] [duration] [[light]] [[dark]]

**Arguments**

- ``led`` (int) - the led number
- ``frequency`` (int) - the flicker frequency [hz]
- ``duration`` (int) - the duration the led flickers [ms]
- ``light`` (int) - the light part of the light/dark ratio (optional)
- ``dark`` (int) - the dark part of the light/dark ratio (optional)

.. _protocol-input-off:

off
^^^

Turns off a led::

  off [led]

**Arguments**

- ``led`` (int) - the led number

.. _protocol-input-measurement:

measurement
^^^^^^^^^^^

Runs a measurement sequence::

  measurement [mode] [flickerLed] [frequency] [onDuration] [offDuration]

**Arguments**

- ``mode`` (int) - `2` for two leds and `4` for four leds
- ``flickerLed`` (int) - Which led will flicker
- ``frequency`` (float) - The frequency for the flickering led [hz]
- ``onDuration`` (int) - The duration, the leds will be on [ms]
- ``offDuration`` (int) - The duration, the leds will be off [ms]

.. _protocol-input-ping:

ping
^^^^

Sends a ping::

  ping [[seq]]

**Arguments**

- ``seq`` (misc) - An optional sequence identifier (optional)

.. _protocol-output:

Output
------

Feedback received from the hardware.

.. _protocol-output-on:

on
^^

Send when a led is turned on (or flickering, when measurement command was used)::

  on [led]

**Arguments**

- ``led`` (int) - the led number

.. _protocol-output-flicker:

flicker
^^^^^^^

Send when a led is flickering:

  flicker [led]

**Arguments**

- ``led`` (int) - the led number

.. _protocol-output-off:

off
^^^

Send when a led is turned off::

  off [led]

**Arguments**

- ``led`` (int) - the led number

.. _protocol-output-measurement:

measurement
^^^^^^^^^^^

Send when a measurement is started or finished::

  measurement [state]


**Arguments**

- ``state`` (string) - ``on`` when the measurement started and ``off`` when it's finished

.. _protocol-output-ons:

ons
^^^

Send when more than one led is turned on (during a measurement)::

  ons [mode]

**Arguments**

- ``mode`` (int) - is either 2 or 4, depending the first value of the ``measurement`` input command

.. _protocol-output-offs:

offs
^^^^

Send when more than one led is turned off (during a measurement)::

  offs [mode]

**Arguments**

- ``mode`` (int) - is either 2 or 4, depending the first value of the ``measurement`` input command

.. _protocol-output-pong:

pong
^^^^

Answers a ping with a pong::

  pong [seq]

**Arguments**

- ``seq`` (misc) - The returned sequence identifier (optional)

.. _protocol-output-error:

error
^^^^^

Send when an error occured::

  error [number]

**Arguments**

- ``number`` (int) - the error number (see below)


Error Codes
-----------

Error code explanation:

- `0` - Unknown Error
- `1` - Malformed command
- `2` - Unknown command
- `3` - Too few arguments for flicker command


Troubleshooting
---------------

1. Getting a "Port in Use" exception on OSX, when connecting to the Arduino Board
-> See here: https://marcosc.com/2011/10/arduino-java-error-serial-port-already-in-use/

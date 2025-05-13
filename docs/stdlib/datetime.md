# Namespace root.datetime

The datetime module provides the following methods:

`datetime.now()`: returns the current date and time as a unix timestamp, as a number of milliseconds since midnight UTC on January 1, 1970.

`datetime.toDateString <val>`: Takes a unix timestamp and returns a string showing the corresponding date.

`datetime.toTimeString <val>`: Takes a unix timestamp and returns a string showing the corresponding time of day.

`datetime.timer`: Returns a new `Timer` instance.

`datetime.wait <time>`: Pause execution for approximately the specified number of milliseconds, then returns the number of milliseconds actually waited.

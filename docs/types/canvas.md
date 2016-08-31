#Type Canvas

Canvas objects are used for turtle-style graphics.

They have the following methods:

`setPenColor <value>`: Takes an integer color code of the form `65536*r+256*g+b` and changes the color of the canvas' pen to that color.

`setPenSize <value>`: Takes an integer value and sets the size of the canvas' pen to that value.

`save <filename>`: Takes a filename as a string and saves the canvas' image data to that file

`show()`: Shows the canvas' current image data on a GUI

`setPenDown <penDown>`: Sets whether or not moving the pen will draw on the canvas

`move <distance>`: Moves the pen the specified distance in its current direction (originally east)

`turnPen <amount>`: Rotates the pen's direction the specified integer number of degrees clockwise (use negative values to turn counterclockwise)

`getWidth()`: Return the width of the image

`getHeight()`: Return the height of the image

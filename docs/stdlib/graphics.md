#Namespace root.graphics

This namespace provides the method `graphics.canvas`. This method may be called on either of the following:

- A string containing the path to an image file

- An object of any other type containing integer fields `width`, `height`, and optionally `bg`.

It returns either a canvas preinitialized to that image data in the first case, or a canvas of the specified dimensions and background color (defaulting to white) in the second case.

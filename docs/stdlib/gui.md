# Namespace root.gui

**Function gui.show**

```
gui.show <message>
```

Produces a simple message gui with the specified message

**Function gui.warn**

```
gui.warn <message>
```

Produces a simple message gui with the specified message. This should be in some way distinct from the gui produced using the `show` method.

**Function gui.prompt**

```
gui.prompt <message>
```

Produces a simple input gui with the specified message, and returns as a `String` the value the user inputs.

```
gui.prompt()
```

Produces a simple input gui with a (potentially implementation-dependent) default message, and returns as a `String` the value the user inputs.

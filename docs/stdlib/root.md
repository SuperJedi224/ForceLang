#Namespace root

The `root` namespace serves as the language's global scope, and all unprefixed identifiers are automatically prefixed with this namespace before evaluation.

`root` is the only builtin namespace which is not immutable.

Direct subnamespaces:

```
root.io
```

See `io.md`.

```
root.math
```

See `math.md`

```
root.random
```

See `random.md`


**Function root.set**

```
set <field> <value>
```

Sets the specified field to the specified value. Attempting to set an immutable field, or any field of an immutable object, throws an exception.

**Function root.cons**

```
cons <field> <value>
```

Sets the specified field to the specified value. Attempting to set an immutable field, or any field of an immutable object, throws an exception.

Then, marks that field as immutable.

**Function root.label**

```
label <name>
```

A no-op used as a marker for the `goto` and `gotoex` instructions. The argument is a bareword and goes unevaluated. Must be the only function call on the line to work.

**Function root.goto**

```
goto <name>
```

Jumps to the specified label.  The argument is a bareword and goes unevaluated. Throws an exception if no such label exists.

**Function root.gotoex**

```
gotoex <name>
```

As goto, but evaluates the argument first and uses the result as the label name. Throws an exception if no such label exists.

**Function if**

```
if <value>
```

If `value` evaluates to falsy, skips the next line of the program

**Function require**

```
require <module>
```

Attempts to locate the Java class of the specified name and invoke it as a module provider. The module name is a bareword and is not evaluated. On success, returns the instantiated Module object. On failure, throws an exception.

**Constant root.nil**

The null object.

**Constant root.TRUE**

Boolean `TRUE`.

**Constant root.FALSE**

Boolean `FALSE`.

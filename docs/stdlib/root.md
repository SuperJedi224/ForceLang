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

```
cons <field> <value>
```

Sets the specified field to the specified value. Attempting to set an immutable field, or any field of an immutable object, throws an exception.

Then, marks that field as immutable.

**Function label**

```
label <name>
```

A no-op used as a marker for the `goto` and `gotoex` instructions. The argument is a bareword and goes unevaluated.

**Function goto**

```
goto <name>
```

Jumps to the specified label.  The argument is a bareword and goes unevaluated.

```
gotoex <name>
```

As goto, but evaluates the argument first and uses the result as the label name.

**Constant root.nil**

The null object.

**Constant root.TRUE**

Boolean `TRUE`.

**Constant root.FALSE**

Boolean `FALSE`.

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

**Constant root.nil**

The null object.

**Constant root.TRUE**

Boolean `TRUE`.

**Constant root.FALSE**

Boolean `FALSE`.

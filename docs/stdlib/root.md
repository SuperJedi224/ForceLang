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


```
root.string
```

See `string.md`

```
root.gui
```

See `gui.md`

```
root.datetime
```

See `datetime.md`

```
root.graphics
```

See `graphics.md`

**Function root.set**

```
set <field> <value>
```

Sets the specified field to the specified value. Attempting to set an immutable field, or any field of an immutable object, throws an exception. You can also set a position in a list by using `<list>[<index>]` as the field argument.

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

**Function root.if**

```
if <value>
```

Requires the next line to have an indentation level at least as great as that of the current line, throwing an `IndentationException` otherwise.

If `<value>` is falsy:
 - If the next line has the same indentation level as the current line, skip to the line after
 - If the next line has an indentation level exceeding that of the current line, skip to the first line after that to have a lower indentation level than the next line

**Function root.require**

```
require <module>
```

Attempts to locate the specified module initializer (In the reference implementation, module initializers are java classes with the method `public static void load(Module m)`), if it exists; then uses it to initialize a `Module` object which is then returned. If such an initializer is not found, or initialization fails, a `NoSuchModuleException` is thrown.

**Function root.def**

```
def <expression> <newexpression>
```

Redefines the first expression such that every time the parser evaluates that expression exactly it will implicitly evaluate the other expression instead. Should be used sparingly.

**Function root.undef**

```
undef <expression>
```

If the specified expression has been redefined using the `def` instruction, undo the redefinition so that the expression is once again evaluated normally.

**Function root.exit**

```
exit()
```

Exits the program.

```
exit <expression>
```

Outputs the value of the indicated expression to the STDERR, then exits the program as above.

**Constant root.nil**

The null object.

**Constant root.TRUE**

Boolean `TRUE`.

**Constant root.FALSE**

Boolean `FALSE`.

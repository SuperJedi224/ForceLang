#Type Function

An instance of `Function` is an object that can be invoked to perform a specific predefined task, possibly producing a return value.

Arguments are passed by name, and therefore, although the argument will often be evaluated once at the beginning of the function call, how, when, and even if the arguments are evaluated is ultimately dependent on the function.

`Function` objects cannot currently be directly instantiated, but this will likely be changed in a later version.

A function call uses the following syntax:

```
<function> <argument(s)>
```

Additionally, some functions can be called without arguments. The syntax used here looks like this:

```
<function>()
```

All `Function` instances are immutable.

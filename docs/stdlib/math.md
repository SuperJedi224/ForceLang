# Namespace root.math

**Constant math.pi**

An approximation of π. Guaranteed accurate to within one part per 10<sup>15</sup>.


**Constant math.e**

An approximation of e. Guaranteed accurate to within one part per 10<sup>15</sup>.


**Constant math.phi**

An approximation of φ. Guaranteed accurate to within one part per 10<sup>15</sup>.

**Function math.sqrt**

```
math.sqrt <number>
```

Returns an approximation of the square root of the specified number, using four iterations of the babylonian method.

**Function math.ln**

```
math.ln <number>
```

Returns an approximation of the natural logarithm of the specified number. The referecnce implementation is based on the last approximation described [here.](https://en.wikipedia.org/wiki/Natural_logarithm)

**Function math.log**

```
math.log <number>
```

Returns an approximation of the common logarithm of the specified number, derived by using properties of logarithms on the result of `math.ln`.

**Function math.floor**

```
math.floor <number>
```

Returns the given number truncated to the nearest integer.

**Function math.sin**

```
math.sin <number>
```

Returns a rational approximation of the sine of the specified number, based on the taylor series for the sine function.

**Function math.cos**

```
math.cos <number>
```

Returns a rational approximation of the cosine of the specified number, based on the taylor series for the cosine function.

**Function math.tan**

```
math.tan <number>
```

Returns a rational approximation of the tangent of the specified number, calculated by dividing the value returned by `math.sin` by the value returned by `math.cos`

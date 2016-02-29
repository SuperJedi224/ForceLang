#Type Number

The Number type represents an arbitrary precision rational number.

It can represent exactly any value of the form a/b, where a and b are both within the range of java's `BigInteger` type.

Infinity is encoded as `1/0`, Negative Infinity as `-1/0`, and NaN as `0/0`.

A `Number` object's toString method can be called with an integer argument to give that many decimal places. Otherwise, the returned string will simply contain the exact fraction.

Number objects also have the `mult` method, which returns the original number multiplied by the passed number.

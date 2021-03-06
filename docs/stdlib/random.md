# Namespace root.random

**Function random.rand**

```
random.rand()
```

Returns a random number between 0, inclusive, and 1, exclusive, to eighty bits of precision.

```
random.rand <precision>
```

Returns a random number between 0, inclusive, and 1, exclusive, to the specified number of bits of precision. Precision must be a positive integer not in excess of 2<sup>16</sup>.

**Function random.seed**

```
random.seed <seed>
```

Reseeds the RNG with the specified seed. The seed must be an integer within the 64-bit two's complement range.

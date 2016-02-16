# Type Module

`Module` objects are special objects which represent external libraries. They are instantiated by the `require` method, but cannot be instantiated in any other way.

`Module` objects instantiated by the `require` method are automatically marked as immutable before being returned.

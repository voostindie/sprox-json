# Sprox - JSON support

This library provides a limited implementation of the Java `XMLEventReader` interface on top of JSON data., specifically for [Sprox](https://github.com/voostindie/sprox).

This is **not** a general purpose JSON to XML adapter! This implementation provides the smallest implementation possible to make Sprox process JSON objects.

## Alternatives

If you're looking for a more powerful JSON to XML adapter, have a look at:

* [StAXON](https://github.com/beckchr/staxon)
* [Jettison](http://jettison.codehaus.org)

I do not guarantee that Sprox works with these libraries however. I haven't tested it.

## JSR-353 implementation

This library is implemented on top of JSR-353, which is part of the JEE 7 implementation. If the environment you use `sprox-json` in is not a JEE 7 environment, then you must include an implementation yourself.

The [reference implementation in Glassfish](https://jsonp.java.net/download.html) works just fine!

## Limitations

Namespaces and attributes are **not** supported. Instead, this library treats every key as a node, either with text - for JSON values - or with subnodes - for objects and arrays.

## Examples

TODO...

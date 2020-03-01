# Sprox - JSON support

[![Build Status](https://travis-ci.org/voostindie/sprox-json.svg?branch=master)](https://travis-ci.org/voostindie/sprox-json)
[![Code Coverage](https://codecov.io/gh/voostindie/sprox-json/branch/master/graph/badge.svg)](https://codecov.io/gh/voostindie/sprox-json)

`sprox-json` provides a limited implementation of the Java `XMLEventReader` interface specifically to allow [Sprox](https://github.com/voostindie/sprox) to process JSON documents.

This is **not** a general purpose JSON to XML adapter! This implementation provides the smallest implementation possible to make Sprox process JSON objects. Many methods throw an `UnsupportedOperationException`. Only what Sprox uses is implemented.

Like Sprox this library can be used in an OSGi environment. The library is actually a proper bundle.

## Alternatives

If you're looking for a (more) powerful JSON to XML adapter, have a look at:

* [StAXON](https://github.com/beckchr/staxon)
* [Jettison](http://jettison.codehaus.org)

I do not guarantee that Sprox works with these libraries however. I haven't tested it.

## Usage

To use `sprox-json`, add the following dependency to your Maven POM:

```xml
<dependency>
    <groupId>nl.ulso.sprox</groupId>
    <artifactId>sprox-json</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Note that you also need to include Sprox, at least version 4.0.0. That also means you need at least Java 8.

Once your project is set up correctly, you can declare and use a Sprox processor like so (for example):

```java
    private <T> T processJsonString(
            String json,
            Class<T> resultClass,
            Class controllerClass)
            throws XmlProcessorException {
        return
            new StaxBasedXmlProcessorBuilderFactory()
                .createXmlProcessorBuilder(resultClass)
                .setXmlInputFactory(new JsonXmlInputFactory())
                .addControllerClass(controllerClass)
                .buildXmlProcessor()
                .execute(new StringReader(json));
    }
```

Note the `setXmlInputFactory` call in there. That's what you need to configure a non-standard factory like this one.

## JSR 353 - Java API for JSON Processing

`sprox-json` is implemented on top of JSR 353, which is part of the JEE 7 specification. If the environment you use `sprox-json` in is not a JEE 7 environment, then you must provide an implementation yourself.

The [reference implementation in Glassfish](https://jsonp.java.net/download.html) works just fine.

## How it works

`sprox-json` translates events produced by the JSR-353 JSON pull parser into StAX XML events. It does so in a very simple way:

* JSON keys define XML nodes.
* Plain JSON values define XML node text (characters).
* JSON objects define XML node contents (subnodes).
* JSON arrays define lists of XML nodes.

That's it. No namespaces. No attributes. Those are simply not supported. This library doesn't try to be smart.

As an example, consider this JSON document:

```json
{
    "id": "6",
    "items": [
        {
            "name": "first"
        },
        {
            "name": "second"
        }
    ]
}
````

`sprox-json` treats this document as if it were the following XML document:

```xml
<root>
    <id>6</id>
    <items>
        <name>first</name>
    </items>
    <items>
        <name>second</name>
    </items>
</root>
```

You can easily map this XML document with a Sprox controller, using just the `@Node` annotation:

```java
public class Controller {

    @Node
    public Record root(@Node String id, List<Item> items) {
        return new Record(id, items);
    }

    @Node
    public Item items(@Node String name) {
        return new Item(name);
    }
}
```

Here `Record` and `Item` are custom domain classes.

The goal of `sprox-json` is to allow you to process a JSON document into your own system with as little code as possible, just like Sprox does for XML, without polluting your domain classes with silly setters or annotations, without having to generate otherwise useless code just for mapping purposes, and without having to read complete JSON documents into memory. Just pick what you need from the input document and ignore the rest.

## Handling null values

JSON supports null values. XML does not. To work around that issue `sprox-json` produces a most unlikely, hideous string value defined in `JsonXmlConstants.NULL_VALUE` whenever it finds a `null`.

And of course, it's up to you to handle this value.

The easiest way to do this is to use the `JsonValueParserWrapper`. This parser implements the Sprox `Parser` interface and wraps another parser. It converts the ugly constant value back into an actual `null` value that it then returns. It passes any other String values to the parser it wraps. If you do this then in your controller classes all you need to do is define the parameter as an `Optional<String>`, which is exactly what you would want to do anyway.

In case your processor needs to be able to map to all Java primitives and all values might be `null` in the JSON input, call the convenience method `JsonValueParserWrapper.addDefaultJsonParsers(XmlProcessorBuilder<T> builder)` to replace all Sprox built-in parsers with parsers that support JSON null values.

## Custom root node name

A JSON document is defined as a single, unnamed object. Sprox needs a name, so `sprox-json` invents one: "`root`". If this is not to your liking, you can configure it when constructing the `JsonXmlInputFactory`.

## Maximum stack depth

`sprox-json` needs to keep track of a stack of names in the document, otherwise it can't generate the proper events when an element is closed. This introduces a risk: a very deeply nested object structure might blow up your computer's memory.

To prevent this from happening, there is a maximum depth to the stack that `sprox-json` maintains. By default this maximum is set to 64. For no particular reason.

If you know the JSON documents you need to process are nested deeper than that, then you can configure a custom maximum depth when constructing the `JsonXmlInputFactory`.

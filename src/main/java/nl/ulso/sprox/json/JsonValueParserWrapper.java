/*
 * Copyright 2015 Vincent Oostindië
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package nl.ulso.sprox.json;

import nl.ulso.sprox.ParseException;
import nl.ulso.sprox.Parser;
import nl.ulso.sprox.XmlProcessorBuilder;
import nl.ulso.sprox.parsers.*;

import static nl.ulso.sprox.json.JsonXmlConstants.NULL_VALUE;

/**
 * Parser for JSON values that maps the synthetic value "{@value JsonXmlConstants#NULL_VALUE}" back to an actual
 * {@code null}, while all other values are parsed by the wrapped parser.
 */
public class JsonValueParserWrapper<T> implements Parser<T> {

    private final Parser<T> parser;

    private JsonValueParserWrapper(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    public T fromString(String value) throws ParseException {
        return NULL_VALUE.equals(value) ? null : parser.fromString(value);
    }

    public static <T> void addDefaultJsonParsers(XmlProcessorBuilder<T> builder) {
        builder.addParser(withJsonNull(new BooleanParser()), Boolean.TYPE);
        builder.addParser(withJsonNull(new ByteParser()), Byte.TYPE);
        builder.addParser(withJsonNull(new CharacterParser()), Character.TYPE);
        builder.addParser(withJsonNull(new DoubleParser()), Double.TYPE);
        builder.addParser(withJsonNull(new FloatParser()), Float.TYPE);
        builder.addParser(withJsonNull(new IntegerParser()), Integer.TYPE);
        builder.addParser(withJsonNull(new LongParser()), Long.TYPE);
        builder.addParser(withJsonNull(new StringParser()), String.class);
        builder.addParser(withJsonNull(new ShortParser()), Short.TYPE);
    }

    public static <T> Parser<T> withJsonNull(Parser<T> parser) {
        return new JsonValueParserWrapper<>(parser);
    }
}

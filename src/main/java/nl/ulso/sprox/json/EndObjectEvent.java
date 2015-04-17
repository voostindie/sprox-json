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

import javax.xml.namespace.QName;
import javax.xml.stream.events.EndElement;
import java.util.Iterator;

/**
 * Adapts a JSON End Object event to an XML End Element event.
 */
class EndObjectEvent extends JsonEvent implements EndElement {

    private final QName name;

    public EndObjectEvent(String name) {
        this.name = new QName(name);
    }

    @Override
    public QName getName() {
        return name;
    }

    @Override
    public Iterator getNamespaces() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getEventType() {
        return END_ELEMENT;
    }

    @Override
    public boolean isEndElement() {
        return true;
    }

    @Override
    public EndElement asEndElement() {
        return this;
    }
}

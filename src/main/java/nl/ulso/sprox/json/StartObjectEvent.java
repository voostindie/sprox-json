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

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.Iterator;

/**
 * Adapts a JSON Start Object event to an XML Start Element event.
 */
class StartObjectEvent extends JsonEvent implements StartElement {

    private final QName name;

    public StartObjectEvent(String name) {
        this.name = new QName(name);
    }

    @Override
    public QName getName() {
        return name;
    }

    @Override
    public Iterator getAttributes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator getNamespaces() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attribute getAttributeByName(QName name) {
        return null;
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return null;
    }

    @Override
    public int getEventType() {
        return START_ELEMENT;
    }

    @Override
    public boolean isStartElement() {
        return true;
    }

    @Override
    public StartElement asStartElement() {
        return this;
    }
}

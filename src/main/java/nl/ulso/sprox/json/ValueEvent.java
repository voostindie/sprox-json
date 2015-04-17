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

import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 * Adapts a JSON Value event to an XML Characters event.
 */
class ValueEvent extends JsonEvent implements Characters {
    private final String data;

    ValueEvent(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public boolean isWhiteSpace() {
        return false;
    }

    @Override
    public boolean isCData() {
        return false;
    }

    @Override
    public boolean isIgnorableWhiteSpace() {
        return false;
    }

    @Override
    public int getEventType() {
        return XMLEvent.CHARACTERS;
    }

    @Override
    public boolean isCharacters() {
        return true;
    }

    @Override
    public Characters asCharacters() {
        return this;
    }
}

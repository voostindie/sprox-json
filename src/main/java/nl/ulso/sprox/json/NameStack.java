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

import javax.xml.stream.XMLStreamException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Keeps a stack of names in the JSON structure. Every nested object increases the depth of the stack by one.
 */
final class NameStack {

    private final int maxDepth;
    private final Deque<Name> stack;

    public NameStack(String rootNodeName, int maximumStackDepth) {
        stack = new ArrayDeque<>();
        maxDepth = maximumStackDepth;
        stack.push(new Name(rootNodeName));
    }

    void push(String name) throws XMLStreamException {
        if (stack.size() == maxDepth) {
            throw new XMLStreamException("Maximum stack depth reached: " + maxDepth);
        }
        stack.push(new Name(name));
    }

    String peek() {
        return stack.peek().value;
    }

    /*
     * Pops the name from the top of the stack, unless the name denotes a JSON array. In that case the actual pop is
     * postponed.
     */
    String conditionalPop() {
        final Name name = stack.peek();
        if (!name.isArray) {
            stack.pop();
        }
        return name.value;
    }

    String forcePop() {
        return stack.pop().value;
    }

    void markAsArray() {
        stack.peek().isArray = true;
    }

    private static final class Name {
        private final String value;
        private boolean isArray;

        Name(String name) {
            this.value = name;
            this.isArray = false;
        }
    }
}

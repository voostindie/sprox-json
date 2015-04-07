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

import nl.ulso.sprox.Node;

import java.util.List;

/**
 *
 */
public class RootObjectController {

    @Node
    public RootObject root(@Node long id, NestedObject object) {
        return new RootObject(id, object);
    }

    @Node
    public NestedObject object(@Node String string, List<Item> items) {
        return new NestedObject(string, items);
    }

    @Node
    public Item list(@Node int id, @Node String name) {
        return new Item(id, name);
    }

}

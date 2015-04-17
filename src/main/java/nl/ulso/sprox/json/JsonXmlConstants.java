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

public final class JsonXmlConstants {

    /**
     * Null values in the input are mapped to this String value. This is necessary because XML doesn't know the
     * concept of null values.
     * <p>
     * The literal value here aims to be unlikely to be used in real-world scenario's, but still easily identifiable
     * for debugging purposes.
     * </p>
     */
    public static final String NULL_VALUE = "-<[:=NULL=:]>-";

    private JsonXmlConstants() {
    }
}

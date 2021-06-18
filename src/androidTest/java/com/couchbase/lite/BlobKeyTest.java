/**
 * Copyright (c) 2016 Couchbase, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.couchbase.lite.v1;

import com.couchbase.lite.v1.util.Log;

/**
 * Created by hideki on 2/20/15.
 */
public class BlobKeyTest extends LiteTestCase {
    public void testConvertToHex(){
        String src = "Hello World!";
        Log.i(Log.TAG, "SRC => " + src);
        String hex = BlobKey.convertToHex(src.getBytes());
        Log.i(Log.TAG, "HEX => " + hex);
        String dest = new String(BlobKey.convertFromHex(hex));
        Log.i(Log.TAG, "DEST => " + dest);
        assertEquals(src, dest);
        assertEquals(hex.toUpperCase(), hex);
    }
}

/**
 * Original iOS version by  Jens Alfke
 * Ported to Android by Marty Schoch
 *
 * Copyright (c) 2012 Couchbase, Inc. All rights reserved.
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

package com.couchbase.lite.v1.performance;

import com.couchbase.lite.v1.CouchbaseLiteException;
import com.couchbase.lite.v1.Document;
import com.couchbase.lite.v1.TransactionalTask;
import com.couchbase.lite.v1.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test09_LoadDB extends PerformanceTestCase {
    public static final String TAG = "LoadDBPerformance";

    @Override
    protected String getTestTag() {
        return TAG;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        if (!performanceTestsEnabled())
            return;

        // Populate documents into the database:
        char[] chars = new char[getSizeOfDocument()];
        Arrays.fill(chars, 'a');
        final String content = new String(chars);

        boolean success = database.runInTransaction(new TransactionalTask() {
            public boolean run() {
                for (int i = 0; i < getNumberOfDocuments(); i++) {
                    try {
                        Map<String, Object> props = new HashMap<String, Object>();
                        props.put("content", content);
                        Document doc = database.createDocument();
                        doc.putProperties(props);
                    } catch (CouchbaseLiteException e) {
                        Log.e(TAG, "Error when creating a document", e);
                        return false;
                    }
                }
                return true;
            }
        });
        assertTrue(success);
    }

    public void testLoadDBPerformance() throws Exception {
        if (!performanceTestsEnabled())
            return;

        long start = System.currentTimeMillis();
        for (int i = 0; i < getNumberOfRounds(); i++) {
            assertTrue(database.close());
            database = manager.getDatabase(DEFAULT_TEST_DB);
            assertNotNull(database);
        }
        long end = System.currentTimeMillis();
        logPerformanceStats((end - start), getNumberOfDocuments() + ", " +
                getSizeOfDocument() + ", " + getNumberOfRounds());
    }

    private int getSizeOfDocument() {
        return Integer.parseInt(System.getProperty("test9.sizeOfDocument"));
    }

    private int getNumberOfDocuments() {
        return Integer.parseInt(System.getProperty("test9.numberOfDocuments"));
    }

    private int getNumberOfRounds() {
        return Integer.parseInt(System.getProperty("test9.numberOfRounds"));
    }
}

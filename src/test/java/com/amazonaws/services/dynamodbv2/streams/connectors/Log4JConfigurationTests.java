/*
 * Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * 
 * Licensed under the Amazon Software License (the "License"). You may not use this file except in compliance with the License.
 * A copy of the License is located at
 * 
 * http://aws.amazon.com/asl/
 * 
 * or in the "LICENSE.txt" file accompanying this file.
 * 
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.amazonaws.services.dynamodbv2.streams.connectors;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.varia.StringMatchFilter;
import org.junit.Test;

public class Log4JConfigurationTests {

    private static final String SHARD_SYNC_MESSAGE = "has no children";

    @Test
    public void testShardSyncerMessage() throws ClassNotFoundException {
        Logger log = Logger.getRootLogger();
        Appender a = log.getAppender("CONSOLE");
        Filter f = a.getFilter();
        assertEquals(null, f.getNext());
        assertEquals(StringMatchFilter.class, f.getClass());
        assertEquals(Filter.NEUTRAL, a.getFilter().decide(new LoggingEvent(Logger.class.toString(), log, Level.ERROR, "TestMessage", null)));
        assertEquals(Filter.DENY, a.getFilter().decide(new LoggingEvent(Logger.class.toString(), log, Level.ERROR, SHARD_SYNC_MESSAGE, null)));
        assertEquals(Filter.DENY,
            a.getFilter().decide(new LoggingEvent(Logger.class.toString(), log, Level.ERROR, "Shard shard-1234-5432-1234 " + SHARD_SYNC_MESSAGE, null)));
    }

}

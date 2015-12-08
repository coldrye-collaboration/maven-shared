package org.apache.maven.shared.io.location;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.net.URL;

import org.apache.maven.shared.io.logging.MessageHolder;

/**
 * classpath resource locator strategy.
 *
 */
public class ClasspathResourceLocatorStrategy
    implements LocatorStrategy
{

    private String tempFilePrefix = "location.";

    private String tempFileSuffix = ".cpurl";

    private boolean tempFileDeleteOnExit = true;

    /**
     * Create instance.
     */
    public ClasspathResourceLocatorStrategy()
    {
    }

    /**
     * @param tempFilePrefix Prefix.
     * @param tempFileSuffix Suffix.
     * @param tempFileDeleteOnExit delete on exit.
     */
    public ClasspathResourceLocatorStrategy( String tempFilePrefix, String tempFileSuffix,
                                             boolean tempFileDeleteOnExit )
    {
        this.tempFilePrefix = tempFilePrefix;
        this.tempFileSuffix = tempFileSuffix;
        this.tempFileDeleteOnExit = tempFileDeleteOnExit;
    }

    /** {@inheritDoc} */
    public Location resolve( String locationSpecification, MessageHolder messageHolder )
    {
        ClassLoader cloader = Thread.currentThread().getContextClassLoader();

        URL resource = cloader.getResource( locationSpecification );

        Location location = null;

        if ( resource != null )
        {
            location = new URLLocation( resource, locationSpecification, tempFilePrefix, tempFileSuffix,
                                        tempFileDeleteOnExit );
        }
        else
        {
            messageHolder.addMessage( "Failed to resolve classpath resource: " + locationSpecification
                + " from classloader: " + cloader );
        }

        return location;
    }

}

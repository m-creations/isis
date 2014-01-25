/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.isis.applib.services.interaction;

import javax.enterprise.context.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.Programmatic;

/**
 * Register this as a service in order to access context information about any {@link Interaction}.
 */
@RequestScoped
public class InteractionContext {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(InteractionContext.class);

    private Interaction interaction;

    @Programmatic
    public Interaction getInteraction() {
        return interaction;
    }
    
    /**
     * <b>NOT API</b>: intended to be called only by the framework.
     * @param interaction
     */
    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

}

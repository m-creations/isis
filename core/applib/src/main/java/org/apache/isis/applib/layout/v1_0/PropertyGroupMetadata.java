/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.isis.applib.layout.v1_0;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;

@XmlType(
        propOrder = {
                "name"
                , "actions"
                , "properties"
        }
)
public class PropertyGroupMetadata implements ColumnContent, ActionOwner, Serializable, HasPath, Owned<ColumnMetadata> {

    private static final long serialVersionUID = 1L;

    public PropertyGroupMetadata() {
    }

    public PropertyGroupMetadata(final String name) {
        setName(name);
    }

    private String name;

    /**
     * Corresponds to the {@link MemberOrder#name()} (when applied to properties).
     */
    @XmlAttribute(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    private List<ActionLayoutMetadata> actions = Lists.newArrayList();

    @XmlElementWrapper(required = false)
    @XmlElement(name = "action", required = false)
    public List<ActionLayoutMetadata> getActions() {
        return actions;
    }

    public void setActions(List<ActionLayoutMetadata> actionLayoutMetadatas) {
        this.actions = actionLayoutMetadatas;
    }



    private List<PropertyLayoutMetadata> properties = Lists.newArrayList();

    @XmlElement(name = "property", required = true)
    public List<PropertyLayoutMetadata> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyLayoutMetadata> properties) {
        this.properties = properties;
    }


    private ColumnMetadata owner;
    /**
     * Owner.
     *
     * <p>
     *     Set programmatically by framework after reading in from XML.
     * </p>
     */
    @XmlTransient
    public ColumnMetadata getOwner() {
        return owner;
    }

    public void setOwner(final ColumnMetadata owner) {
        this.owner = owner;
    }




    private String path;

    @Programmatic
    @XmlTransient
    public String getPath() {
        return path;
    }

    @Programmatic
    public void setPath(final String path) {
        this.path = path;
    }




    public static class Util {
        private Util(){}
        public static Function<? super PropertyGroupMetadata, String> nameOf() {
            return new Function<PropertyGroupMetadata, String>() {
                @Nullable @Override
                public String apply(@Nullable final PropertyGroupMetadata propertyGroupMetadata) {
                    return propertyGroupMetadata.getName();
                }
            };
        }
    }

}
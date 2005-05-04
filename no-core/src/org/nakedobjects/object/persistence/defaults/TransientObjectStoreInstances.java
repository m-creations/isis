package org.nakedobjects.object.persistence.defaults;

import org.nakedobjects.NakedObjects;
import org.nakedobjects.object.NakedObject;
import org.nakedobjects.object.NakedObjectRuntimeException;
import org.nakedobjects.object.persistence.InstancesCriteria;
import org.nakedobjects.object.persistence.Oid;
import org.nakedobjects.object.reflect.PojoAdapterFactory;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;


/*
 * The objects need to store in a repeatable sequence so the elements and
 * instances method return the same data for any repeated call, and so that one
 * subset of instances follows on the previous. This is done by keeping the
 * objects in the order that they where created.
 */
class TransientObjectStoreInstances {
    protected final Hashtable objectInstances = new Hashtable();
    protected final Hashtable titleIndex = new Hashtable();

    public Enumeration elements() {
        Vector v = new Vector(objectInstances.size());
        for (Enumeration e = objectInstances.keys(); e.hasMoreElements();) {
            Oid oid = (Oid) e.nextElement();
            v.addElement(getObject(oid));
        }
        return v.elements();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        Logger.getLogger(TransientObjectStoreInstances.class).info("finalizing instances");
    }

    public NakedObject getObject(Oid oid) {
        if (loaded().isLoaded(oid)) {
            return loaded().getLoadedObject(oid);
        } else {
            Object pojo = objectInstances.get(oid);
            if (pojo == null) {
                return null;
            }
            NakedObject object = NakedObjects.getPojoAdapterFactory().createNOAdapter(pojo);
            if (object.getOid() == null) {
                object.setOid(oid);
                object.setResolved();
            } else if (!object.getOid().equals(oid)) {
                throw new NakedObjectRuntimeException("Requested object with OID " + oid
                        + ", but got object (with different oid): " + object);
            }
            loaded().loaded(object);

            return object;
        }
    }

    public Oid getOidFor(Object object) {
        Enumeration enumeration = objectInstances.keys();
        while (enumeration.hasMoreElements()) {
            Oid oid = (Oid) enumeration.nextElement();
            if (objectInstances.get(oid).equals(object)) {
                return oid;
            }
        }

        return null;
    }

    public boolean hasInstances() {
        return numberOfInstances() > 0;
    }

 /*   public NakedObject instanceMatching(String title) {
        Oid oid = (Oid) titleIndex.get(title);
        return oid == null ? null : getObject(oid);
    }
*/
    public void instances(InstancesCriteria criteria, Vector instances) {
        Enumeration e = elements();
        while (e.hasMoreElements()) {
            NakedObject element = (NakedObject) e.nextElement();
            if(criteria.matches(element)) {
                instances.addElement(element);
            }
        }    
    }

    public void instances(Vector instances) {
        Enumeration e = elements();
        while (e.hasMoreElements()) {
            instances.addElement(e.nextElement());
        }
    }

    protected PojoAdapterFactory loaded() {
        return NakedObjects.getPojoAdapterFactory();
    }

    public int numberOfInstances() {
        return objectInstances.size();
    }

    public void remove(Oid oid) {
        NakedObject object;
        object = getObject(oid);
        objectInstances.remove(oid);
        loaded().unloaded(object);
    }

    public void save(NakedObject object) {
        objectInstances.put(object.getOid(), object.getObject());
        titleIndex.put(object.titleString().toLowerCase(), object.getOid());
    }

    public void shutdown() {
        objectInstances.clear();
        titleIndex.clear();
    }

}

/*
 * Naked Objects - a framework that exposes behaviourally complete business
 * objects directly to the user. Copyright (C) 2000 - 2005 Naked Objects Group
 * Ltd
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * The authors can be contacted via www.nakedobjects.org (the registered address
 * of Naked Objects Group is Kingsway House, 123 Goldworth Road, Woking GU21
 * 1NR, UK).
 */
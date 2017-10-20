package com.campus.embeddeddb.namespace;

import com.campus.data.PropertyName;

/**
 * Defines constants for Embeddeddb service name and action namess. 
 */
public class EmbeddeddbNamespace extends PropertyName {
    static {init(EmbeddeddbNamespace.class);}
    static public String EmbeddeddbService = "embeddeddb",
    // action names
    setup,
    insert,
    select;
    
}
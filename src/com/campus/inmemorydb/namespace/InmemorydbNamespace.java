package com.campus.inmemorydb.namespace;

import com.campus.data.PropertyName;

/**
 * Defines constants for Inmemorydb service name and action namess. 
 */
public class InmemorydbNamespace extends PropertyName {
    static {init(BlockStorageNamespace.class);}
    static public String InmemorydbService = "inmemorydb",
    // action names
    setup,
    insert,
    select;
    
}
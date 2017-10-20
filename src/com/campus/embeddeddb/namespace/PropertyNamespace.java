package com.campus.embeddeddb.namespace;

import com.campus.data.PropertyName;

/**
 * Defines constants for property names in the property pool. 
 */
public class PropertyNamespace extends PropertyName {
    static {init(PropertyNamespace.class);}
    static public String
    DatabaseName,
    Params,
    Result,
    SelectQuery,
    InsertQuery,
    CreateQuery,
    TruncateStatement;
}
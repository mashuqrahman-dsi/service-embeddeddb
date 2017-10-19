package com.campus.inmemorydb;

import java.util.Map;

import com.campus.data.DataInfo;
import com.campus.data.DataProcessor;
import com.campus.data.PropertyInfo;
import com.campus.service.InternalModule;
import com.campus.service.Service;
import com.campus.service.ServiceRequest;
import com.campus.service.SystemBootstrap;
import com.campus.persistence.domain.DomainList;
import com.campus.persistence.domain.DomainObject;
import com.campus.util.Reason;
import com.campus.util.ReasonCode;
import com.campus.util.ReasonException;


/**
 * Manages the discovery and retrieval of xml component definitions. 
 */
public class InmemorydbModule {
    static public final String MODULE = "inmemorydb";
    static InternalModule _module;
    
    static private synchronized InternalModule init() {
        if (_module == null) _module = SystemBootstrap.getInternalModule(MODULE);
        return _module;
    }

    static public ServiceRequest canSubmit(String serviceName$, String actionName$, Map header$, Map params$) {
        if (_module == null) init();
        return _module.canSubmit(serviceName$, actionName$, header$, params$);
    }

    static public ServiceRequest submit(String serviceName$, String actionName$, Map header$, Map params$) {
        if (_module == null) init();
        return _module.submit(serviceName$, actionName$, header$, params$);
    }

    static public Service getService(String name$) {
        if (_module == null) init();
    	return _module.getService(name$);
    }

    static public PropertyInfo getPropertyInfo(String name$) {
        if (_module == null) init();
        return _module.getPropertyInfo(name$);
    }

    static public DataInfo getDataInfo(String name$) {
        if (_module == null) init();
        return _module.getDataInfo(name$);
    }

    static public DataProcessor getDataProcessor(String name$) {
        if (_module == null) init();
        return _module.getDataProcessor(name$);
    }

    static public DomainList getDomainList(String name$) {
        if (_module == null) init();
        return _module.getDomainList(name$);
    }

    static public DomainObject getDomainObject(String name$) {
        if (_module == null) init();
        return _module.getDomainObject(name$);
    }

    static public boolean hasConfig(String key$) {
        if (_module == null) init();
        return _module.hasConfig(key$);
    }

    static public String getConfig(String key$) {
        if (_module == null) init();
        return _module.getConfig(key$);
    }

}
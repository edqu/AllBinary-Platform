/*
* AllBinary Open License Version 1
* Copyright (c) 2011 AllBinary
* 
* By agreeing to this license you and any business entity you represent are
* legally bound to the AllBinary Open License Version 1 legal agreement.
* 
* You may obtain the AllBinary Open License Version 1 legal agreement from
* AllBinary or the root directory of AllBinary's AllBinary Platform repository.
* 
* Created By: Travis Berthelot
* 
*/
package allbinary.osgi.service;

import abcs.logic.communication.log.Log;
import abcs.logic.communication.log.LogUtil;
import java.util.Iterator;
import java.util.Vector;

import allbinary.osgi.OSGIServiceInterface;
import allbinary.osgi.OSGIServiceVisitorInterface;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

abstract public class InputAutomationServiceConsumer
{
    private String registryName;
    private BundleContext bundleContext;
    private OSGIServiceVisitorInterface osgiServiceVisitorInterface;
        
    public InputAutomationServiceConsumer(
        String registryName, BundleContext bundleContext,
        OSGIServiceVisitorInterface osgiServiceVisitorInterface)
    {
        this.setRegistryName(registryName);
        this.setBundleContext(bundleContext);
        this.setOsgiServiceVisitorInterface(osgiServiceVisitorInterface);
    }
    
    public void process()
    throws Exception
    {
        LogUtil.put(new Log("Start", this, "process"));
        
        Vector vector = OSGIServiceUtil.getServicesObjectVector(
            this.getBundleContext(),
            this.getServiceReferences());
        
        LogUtil.put(new Log("Processing " + vector.size() + " Services", this, "process"));
        Iterator iterator = vector.iterator();
        while(iterator.hasNext())
        {
            OSGIServiceInterface osgiServiceInterface = 
                (OSGIServiceInterface) iterator.next();
            if(!getOsgiServiceVisitorInterface().visit(osgiServiceInterface))
            {
                throw new Exception("Unable to process service: " + osgiServiceInterface);
            }
        }
    }

    private ServiceReference[] getServiceReferences()
        throws Exception
    {
         return this.getBundleContext().getServiceReferences(
             this.getRegistryName(), null);
    }
    
    public BundleContext getBundleContext()
    {
        return bundleContext;
    }

    public void setBundleContext(BundleContext aBundleContext)
    {
        bundleContext = aBundleContext;
    }

    public String getRegistryName()
    {
        return registryName;
    }

    public void setRegistryName(String registryName)
    {
        this.registryName = registryName;
    }

    public OSGIServiceVisitorInterface getOsgiServiceVisitorInterface()
    {
        return osgiServiceVisitorInterface;
    }

    public void setOsgiServiceVisitorInterface(OSGIServiceVisitorInterface osgiServiceVisitorInterface)
    {
        this.osgiServiceVisitorInterface = osgiServiceVisitorInterface;
    }
}

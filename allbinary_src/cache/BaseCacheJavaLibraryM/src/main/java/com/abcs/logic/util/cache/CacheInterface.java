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
package com.abcs.logic.util.cache;

//import java.util.Set;

/* Use this cache if you want to manually remove objects
 * from the cache
 */
public interface CacheInterface
{
    void add(CacheableInterface cacheableInterface) throws Exception;
    void add(CacheableInterface[] cacheableInterfaces) throws Exception;
    //Set keySet();
    void clear() throws Exception;
}

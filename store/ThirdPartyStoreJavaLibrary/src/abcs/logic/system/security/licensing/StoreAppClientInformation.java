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
package abcs.logic.system.security.licensing;

import org.allbinary.thirdparty.store.LongArrayIdentifierInterface;

/**
 *
 * @author user
 */
public class StoreAppClientInformation
extends AbeClientInformation
implements LongArrayIdentifierInterface
{
    private final long[] longArrayIdentifier;

    public StoreAppClientInformation(
        String name, String version, String specialName, long[] longArrayIdentifier)
    {
        super(name, version, specialName);

        this.longArrayIdentifier = longArrayIdentifier;
    }

    /**
     * @return the longArrayIdentifier
     */
    public long[] getLongArrayIdentifier()
    {
        return longArrayIdentifier;
    }
}

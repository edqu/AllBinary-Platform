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
package abcs.logic.system.os;

import abcs.logic.java.character.CharacterSet;

public interface OperatingSystemInterface
{    
   String getName();
   String getVersion();
   String getArch();
   CharacterSet getCharacterSet();
   String toString();      
}
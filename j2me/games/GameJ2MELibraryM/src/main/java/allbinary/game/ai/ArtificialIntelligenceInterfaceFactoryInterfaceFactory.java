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
package allbinary.game.ai;

import java.util.Hashtable;

import org.allbinary.util.BasicArrayList;

public class ArtificialIntelligenceInterfaceFactoryInterfaceFactory
{
   private static ArtificialIntelligenceInterfaceFactoryInterfaceFactory SINGLETON = new ArtificialIntelligenceInterfaceFactoryInterfaceFactory();
   
   private BasicArrayList list;

   public void clear()
   {
       list = new BasicArrayList();
   }
   
   public static ArtificialIntelligenceInterfaceFactoryInterfaceFactory getInstance()
   {
       return SINGLETON;
   }
   
   public ArtificialIntelligenceInterfaceFactoryInterface getInstance(Hashtable hashtable)
   {
      Integer typeInteger = (Integer) hashtable.get(BasicAI.ID);
       
      int type = typeInteger.intValue();
      
      ArtificialIntelligenceInterfaceFactoryInterface artificialIntelligenceInterfaceFactoryInterface = 
            (ArtificialIntelligenceInterfaceFactoryInterface) list.objectArray[type - 1];
      
      return artificialIntelligenceInterfaceFactoryInterface;
   }
   
   public void add(ArtificialIntelligenceInterfaceFactoryInterface artificialIntelligenceInterfaceFactoryInterface)
   {
       list.add(artificialIntelligenceInterfaceFactoryInterface);
   }
}
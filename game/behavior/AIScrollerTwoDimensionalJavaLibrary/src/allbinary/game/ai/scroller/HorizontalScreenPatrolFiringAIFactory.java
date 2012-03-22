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
package allbinary.game.ai.scroller;

import java.util.Hashtable;

import allbinary.ai.ArtificialIntelligenceInterface;
import allbinary.game.ai.ArtificialIntelligenceInterfaceFactoryInterface;
import allbinary.game.input.GameInput;
import allbinary.layer.AllBinaryLayer;

public class HorizontalScreenPatrolFiringAIFactory
    implements ArtificialIntelligenceInterfaceFactoryInterface
{
    public ArtificialIntelligenceInterface getInstance(
          Hashtable hashtable, AllBinaryLayer ownerLayerInterface, GameInput gameInput)
          throws Exception
    {
        //ArtificialIntelligenceInterface[] artificialIntelligenceInterface =
            //new ArtificialIntelligenceInterface[1];
        
        //artificialIntelligenceInterface[0] = 
        return new HorizontalScreenPatrolAI(hashtable, ownerLayerInterface, gameInput);

        //artificialIntelligenceInterface[1] = 
          //  new TimedFireAI(3200, ownerLayerInterface, gameInput);

        //artificialIntelligenceInterface[2] = 
          //  new FlockingAI(hashtable, ownerLayerInterface, gameInput);
        
    	//return new ArrayAI(artificialIntelligenceInterface, ownerLayerInterface, gameInput);
    }
}

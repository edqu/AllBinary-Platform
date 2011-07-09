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
package allbinary.logic.visual.dhtml.style;

public class StyleData
{
	private static final StyleData instance = new StyleData();
	
	   public static StyleData getInstance() {
			return instance;
		}
	
   private StyleData()
   {
   }
   
   public final String NAME = "STYLE_NAME";

   public final int NAMEMAXLEN = 255;
}

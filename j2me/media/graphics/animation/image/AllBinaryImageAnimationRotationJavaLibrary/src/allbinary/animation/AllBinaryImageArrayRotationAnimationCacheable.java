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
package allbinary.animation;

import com.abcs.logic.util.cache.CacheableInterface;

public class AllBinaryImageArrayRotationAnimationCacheable 
   extends AllBinaryAdjustedImageArrayRotationAnimation 
		implements CacheableInterface {

	//private Object key;
	
	public AllBinaryImageArrayRotationAnimationCacheable(Object object) 
	throws Exception 
	{
		super(object);
		//this.key = key;
	}
	
	/*
	public AllBinaryImageRotationAnimationCacheable(
			MEImage[] imageArray, int angleIncrement, int totalAngle, int dx, int dy) 
	throws Exception 
	{
		super(imageArray, angleIncrement, totalAngle, dx, dy);
	}
	*/
	
	public Object getKey() {
		return null;
	}

	public String toString() {
		return null;
	}
}

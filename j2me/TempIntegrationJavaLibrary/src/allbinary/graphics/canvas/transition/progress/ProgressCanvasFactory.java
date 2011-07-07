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
package allbinary.graphics.canvas.transition.progress;

import abcs.logic.basic.string.StringUtil;

import allbinary.graphics.color.BasicColor;

public class ProgressCanvasFactory {

    private static ProgressCanvas PROGRESS_FORM_SCREEN = new ProgressCanvas(
            StringUtil.getInstance(), BasicColor.BLACK, BasicColor.WHITE);
    
    public static ProgressCanvas getInstance()
    {
        return PROGRESS_FORM_SCREEN;
    }
    
}

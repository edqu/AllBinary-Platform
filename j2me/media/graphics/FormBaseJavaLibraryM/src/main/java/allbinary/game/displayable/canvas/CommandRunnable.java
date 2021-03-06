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

package allbinary.game.displayable.canvas;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;

import abcs.logic.basic.string.CommonStrings;
import abcs.logic.communication.log.LogFactory;
import abcs.logic.communication.log.LogUtil;
import allbinary.graphics.displayable.MyCanvas;

public class CommandRunnable implements Runnable
{
    private final CommandFormInputProcessor commandFormInputProcessor;
    private final Command command;
    
    public CommandRunnable(
            CommandFormInputProcessor commandFormInputProcessor, Command command)
    {
        this.commandFormInputProcessor = commandFormInputProcessor;
        this.command = command;
    }
    
   public void run()
   {
      try
      {
         LogUtil.put(LogFactory.getInstance(CommonStrings.getInstance().START_RUNNABLE, this, CommonStrings.getInstance().RUN));

         MyCanvas canvas = this.commandFormInputProcessor.getCanvas();

         CommandListener commandListener =
                 canvas.getCustomCommandListener();

         commandListener.commandAction(command, canvas);

         LogUtil.put(LogFactory.getInstance(CommonStrings.getInstance().END_RUNNABLE, this, CommonStrings.getInstance().RUN));
         
      } catch (Exception e)
      {
         LogUtil.put(LogFactory.getInstance(CommonStrings.getInstance().EXCEPTION, this, CommonStrings.getInstance().RUN, e));
      }

   }
}

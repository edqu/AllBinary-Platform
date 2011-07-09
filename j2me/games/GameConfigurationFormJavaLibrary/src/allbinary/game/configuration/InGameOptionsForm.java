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
package allbinary.game.configuration;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;

import abcs.logic.basic.string.CommonStrings;
import abcs.logic.communication.log.LogFactory;
import abcs.logic.communication.log.LogUtil;
import allbinary.game.commands.GameCommandsFactory;
import allbinary.game.configuration.feature.GameFeatureFormUtil;
import allbinary.game.configuration.feature.InGameFeatureChoiceGroups;
import allbinary.graphics.color.BasicColor;
import allbinary.graphics.displayable.screen.CommandForm;

public class InGameOptionsForm extends CommandForm
{
    public static final Command DISPLAY  = new Command("Options In Game", Command.SCREEN, 1);
    public static final Command SAVE  = new Command("Save", Command.SCREEN, 1);
    public static final Command DEFAULT  = new Command("Default", Command.SCREEN, 1);
    
    protected InGameOptionsForm(CommandListener commandListener, String title,
            BasicColor backgrounBasicColor, BasicColor foregroundBasicColor)
        throws Exception
    {
        super(commandListener, title, backgrounBasicColor, foregroundBasicColor);

        LogUtil.put(LogFactory.getInstance(CommonStrings.getInstance().START, this, CommonStrings.getInstance().CONSTRUCTOR));

        GameFeatureFormUtil gameFeatureFormUtil = 
            GameFeatureFormUtil.getInstance();
        
        gameFeatureFormUtil.addChoiceGroup(this, 
                InGameFeatureChoiceGroups.getExclusiveInstance().get(),
                Choice.EXCLUSIVE);
        
        gameFeatureFormUtil.addChoiceGroup(this, 
                InGameFeatureChoiceGroups.getMultipleInstance().get(),
                Choice.MULTIPLE);

        this.initCommands(commandListener);
    }
    
    public void initCommands(CommandListener cmdListener)
    {
        this.removeAllCommands();

        this.addCommand(GameCommandsFactory.getInstance().CLOSE_AND_SHOW_GAME_CANVAS);
        this.addCommand(this.DEFAULT);

        this.setCommandListener(cmdListener);
    }

}

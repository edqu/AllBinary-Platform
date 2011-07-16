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
package bundle.input.automation.robot;

import allbinary.input.automation.robot.InputRobotInterface;

public class InputAutomationRobotService 
    implements InputAutomationRobotServiceInterface
{
    private InputRobotInterface inputRobotInterfaceArray[];
    
    public InputAutomationRobotService(
        InputRobotInterface inputRobotInterfaceArray[])
    {
        this.setInputRobotInterfaceArray(
            inputRobotInterfaceArray);
    }

    public InputRobotInterface[] getInputRobotInterfaceArray()
    {
        return inputRobotInterfaceArray;
    }

    public void setInputRobotInterfaceArray(InputRobotInterface[] inputRobotInterfaceArray)
    {
        this.inputRobotInterfaceArray = inputRobotInterfaceArray;
    }
}
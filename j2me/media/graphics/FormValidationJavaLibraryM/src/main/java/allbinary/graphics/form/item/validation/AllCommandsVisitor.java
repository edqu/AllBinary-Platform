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
package allbinary.graphics.form.item.validation;

import abcs.logic.java.bool.BooleanFactory;
import allbinary.logic.basic.util.visitor.Visitor;

public class AllCommandsVisitor extends Visitor
{
    public AllCommandsVisitor()
    {
    }
    
    public Object visit(Object object)
    {
        return BooleanFactory.getInstance().TRUE;
    }
}

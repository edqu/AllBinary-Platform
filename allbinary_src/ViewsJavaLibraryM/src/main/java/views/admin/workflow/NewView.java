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
package views.admin.workflow;

import abcs.logic.communication.log.LogFactory;
import org.w3c.dom.Node;
import org.w3c.dom.Document;

import abcs.logic.communication.log.LogUtil;

import allbinary.business.DynamicObjectData;

import allbinary.logic.visual.transform.info.TransformInfoHttpInterface;
import allbinary.logic.visual.transform.info.TransformInfoInterface;

import allbinary.logic.control.validate.ValidationComponentInterface;

import allbinary.logic.control.workflow.NewWorkFlowFactory;

public class NewView extends WorkFlowView implements ValidationComponentInterface
{
   public NewView(TransformInfoInterface transformInfoInterface) throws Exception
   {
      super(transformInfoInterface);
      
      TransformInfoHttpInterface httpTransformInfoInterface = 
         (TransformInfoHttpInterface) this.getTransformInfoInterface();

      this.workFlowInterface = NewWorkFlowFactory.getInstance(
         (String) httpTransformInfoInterface.getPropertiesHashMap().get(
            DynamicObjectData.NAME));
   }
   
   public Boolean isValid()
   {
      try
      {
         return Boolean.TRUE;
      }
      catch(Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.VIEWERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failed to validate",this,"isValid()",e));
         }
         return Boolean.FALSE;
      }
   }
   
   public String validationInfo()
   {
      try
      {
         StringBuffer stringBuffer = new StringBuffer();

         stringBuffer.append("Please Contact Administrator");
         
         return stringBuffer.toString();
      }
      catch(Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.VIEWERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failed to generate validation error info",this,"validationInfo()",e));
         }
         return "Error Getting Validation Info";
      }
   }
   
   public Document toValidationInfoDoc()
   {
      return null;
   }
   
   public Node toValidationInfoNode(Document document)
   {
      return null;
   }
   
}

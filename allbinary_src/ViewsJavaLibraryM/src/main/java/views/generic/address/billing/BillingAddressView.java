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
package views.generic.address.billing;

import abcs.logic.communication.log.LogFactory;
import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import abcs.logic.communication.log.LogUtil;

import allbinary.business.user.address.StreetAddress;
import allbinary.business.user.address.StreetAddressData;

import allbinary.logic.visual.transform.info.TransformInfoInterface;
import allbinary.data.tree.dom.DomNodeInterface;

import views.generic.address.StatesView;

import views.business.context.modules.storefront.HttpStoreComponentView;

public class BillingAddressView 
   extends HttpStoreComponentView
   implements DomNodeInterface
{
   private HttpServletRequest request;

   protected StreetAddress streetAddress;

   public BillingAddressView(TransformInfoInterface transformInfoInterface) throws Exception
   {
      super(transformInfoInterface);
      this.request = (HttpServletRequest) this.getPageContext().getRequest();
   }

   protected HttpServletRequest getRequest()
   {
      return this.request;
   }
      
   public Node toXmlNode(Document document) throws Exception
   {
      try
      {
         Node formNode = document.createElement(StreetAddressData.FORM);
         formNode.appendChild(streetAddress.toXmlNode(document));
         return formNode;
      }
      catch(Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(
         abcs.logic.communication.log.config.type.LogConfigType.XSLLOGGINGERROR))
         {
            LogUtil.put(LogFactory.getInstance("Command Failed",this,"toXmlNode",e));
         }
         throw e;
      }
   }
   
   public void addDomNodeInterfaces()
   {
      this.addDomNodeInterface((DomNodeInterface) this);
      this.addDomNodeInterface((DomNodeInterface) new StatesView());
   }
   
   public String view() throws Exception
   {
      try
      {
         this.addDomNodeInterfaces();
         return super.view();
      }
      catch(Exception e)
      {
         String error = "Failed to view BillingAddress";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.TAGHELPERERROR))
         {
            LogUtil.put(LogFactory.getInstance(error,this,"view()",e));
         }
         throw e;
      }
   }   
}
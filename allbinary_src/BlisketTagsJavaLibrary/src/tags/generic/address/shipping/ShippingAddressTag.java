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
package tags.generic.address.shipping;

import allbinary.business.user.address.ShippingAddressData;

import abcs.logic.system.security.licensing.LicensingException;

import tags.StoreValidationTransformTag;

import abcs.logic.communication.http.request.AbResponseHandler;
import javax.servlet.jsp.JspTagException;

public class ShippingAddressTag extends StoreValidationTransformTag
{
   private String value;
   
   public ShippingAddressTag()
   {
   }
   
   /*
   public void setValue(String value)
   {
      this.value=value;
   }
   */
   
   public int doStartTag() throws JspTagException
   {
      try
      {
         
         
         if(this.getCommand()!=null)
         {
            
            //this.getPropertiesHashMap().put(StreetAddressData.INDEX,this.value);
            if (this.getCommand().compareTo(allbinary.globals.GLOBALS.VIEW)==0)
            {
               this.setName("Basic Shipping Address View");
               this.setObjectFile("views.generic.address.shipping.ViewShippingAddressesView");
            }
            else
               if (this.getCommand().compareTo(allbinary.globals.GLOBALS.EDIT)==0)
               {
                  this.setName("Basic Shipping Address View");
                  this.setObjectFile("views.generic.address.shipping.EditShippingAddressesView");
               }
               else
                  if (this.getCommand().compareTo(allbinary.globals.GLOBALS.NEW)==0)
                  {
                     this.setName("Basic Shipping Address View");
                     this.setObjectFile("views.generic.address.shipping.NewShippingAddressesView");
                  }
                  else
                     if (this.getCommand().compareTo(allbinary.globals.GLOBALS.INSERT)==0)
                     {
                        this.setName("Basic Shipping Address View");
                        this.setObjectFile("views.generic.address.shipping.AddShippingAddressesView");
                     }
                     else
                        if (this.getCommand().compareTo(allbinary.globals.GLOBALS.DELETE)==0)
                        {
                           this.setName("Basic Shipping Address View");
                           this.setObjectFile("views.generic.address.shipping.DeleteShippingAddressesView");
                        }
                        else
                           if (this.getCommand().compareTo(ShippingAddressData.SELECT)==0)
                           {
                              this.setName("Basic Shipping Address View");
                              this.setObjectFile("views.generic.address.shipping.SelectShippingAddressesView");
                           }
                           else
                              if (this.getCommand().compareTo(ShippingAddressData.SETTOBILLINGADDRESS)==0)
                              {
                                 this.setName("Basic Shipping Address View");
                                 this.setObjectFile("views.generic.address.shipping.SetShippingAddressesView");
                              }
            
                              else
                              {
                                 throw new Exception("No Such View Command: " + this.getCommand());
                              }
            return super.doStartTag();
         }
         throw new Exception("Command Null");
      }
      catch(LicensingException e)
      {
         AbResponseHandler.sendJspTagLicensingRedirect(
         this.pageContext,
         e);
         return SKIP_BODY;
      }
      catch(Exception e)
      {
         AbResponseHandler.sendJspTagRedirect(
         this.pageContext,
         e);
         return SKIP_BODY;
      }
   }
}

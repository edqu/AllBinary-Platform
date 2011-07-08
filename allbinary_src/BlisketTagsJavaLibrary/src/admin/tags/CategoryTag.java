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
package admin.tags;

import java.lang.reflect.Method;

import javax.servlet.jsp.JspTagException;

import abcs.logic.communication.http.request.AbResponseHandler;
import abcs.logic.communication.log.LogFactory;
import abcs.logic.communication.log.LogUtil;
import abcs.logic.system.security.licensing.LicensingException;
import admin.taghelpers.CategoryHelperFactory;
import admin.taghelpers.CategoryRequestHelperFactory;
import allbinary.business.category.CategoryData;
import allbinary.logic.visual.transform.info.TransformInfoData;

public class CategoryTag extends TableTag
{
   private String xsl;
   
   public CategoryTag()
   {
      this.setTagHelperFactory(new CategoryHelperFactory());
      this.setTagRequestHelperFactory(new CategoryRequestHelperFactory());
   }
   
   public void setXsl(String value)
   {
      this.xsl = value;
   }
   
   private String viewCategory() throws LicensingException
   {
      try
      {
         Object object =
         new CategoryRequestHelperFactory().getInstance(
         this.getPropertiesHashMap(), this.pageContext);
         
         Class helperClass = object.getClass();
         Method method = helperClass.getMethod("viewCategory",null);
         
         String result = (String) method.invoke(object,null);
         return result;
      }
      catch(LicensingException e)
      {
         throw e;
      }
      catch(Exception e)
      {
         String error = "Failed to view a Category";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGSERROR))
         {
            LogUtil.put(LogFactory.getInstance(error,this,"viewCategory()",e));
         }
         return error;
      }
   }
   
   private String viewCategories() throws LicensingException
   {
      try
      {
         Object object =
         new CategoryRequestHelperFactory().getInstance(
         this.getPropertiesHashMap(), this.pageContext);
         
         Class helperClass = object.getClass();
         Method method = helperClass.getMethod("viewCategories",null);
         
         String result = (String) method.invoke(object,null);
         return result;
      }
      catch(LicensingException e)
      {
         throw e;
      }
      catch(Exception e)
      {
         String error = "Failed to view a Categories";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGSERROR))
         {
            LogUtil.put(LogFactory.getInstance(error,this,"viewCategories()",e));
         }
         return error;
      }
   }
   
   public int doStartTag() throws JspTagException
   {
      try
      {
         if(this.isEnabled())
         {
            if(this.getCommand()!=null)
            {
               this.getPropertiesHashMap().put(TransformInfoData.getInstance().TEMPLATEFILENAME,this.xsl);
               
               if (this.getCommand().compareTo(CategoryData.getInstance().VIEW)==0)
               {
                  String output = this.viewCategory();
                  if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.JSPTAGEXTRAOUTPUT))
                  {
                     pageContext.getOut().print(output);
                  }
               }
               else
                  if (this.getCommand().compareTo(CategoryData.getInstance().VIEW)==0)
                  {
                     String output = this.viewCategories();
                     if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.JSPTAGEXTRAOUTPUT))
                     {
                        pageContext.getOut().print(output);
                     }
                  }
                  else
                  {
                     return super.doStartTag();
                  }
            }
         }
         return SKIP_BODY;
      }
      catch(LicensingException e)
      {
         AbResponseHandler.sendJspTagLicensingRedirect(this.pageContext, e);
         return SKIP_BODY;
      }
      catch(Exception e)
      {
         AbResponseHandler.sendJspTagRedirect(this.pageContext, e);
         return SKIP_BODY;
      }
   }
}

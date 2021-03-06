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
package admin.taghelpers;

import javax.servlet.jsp.PageContext;

import javax.servlet.http.HttpServletRequest;

import abcs.logic.basic.io.file.FileUtil;

import abcs.globals.URLGLOBALS;

import abcs.logic.basic.path.AbPath;
import abcs.logic.basic.path.AbPathData;
import abcs.logic.communication.log.LogFactory;

import abcs.logic.communication.log.LogUtil;

import views.admin.inventory.listings.ProductListingFactory;

import allbinary.logic.communication.smtp.info.EmailInfo;
import allbinary.logic.communication.smtp.info.BasicEmailInfo;
import allbinary.logic.communication.smtp.info.AdminEmailInfo;
import allbinary.logic.communication.smtp.info.StoreEmailInfo;

import allbinary.logic.communication.smtp.event.UserEmailEventNameData;

import allbinary.logic.communication.smtp.event.handler.UserEmailEventHandler;

import allbinary.logic.communication.smtp.event.handler.factory.AdminUserEmailEventHandlerSingletons;
import allbinary.logic.communication.smtp.event.handler.factory.StoreAdminUserEmailEventHandlerSingletons;

import allbinary.business.context.AbContext;

import allbinary.business.context.modules.storefront.StoreFrontInterface;
import allbinary.business.context.modules.storefront.StoreFrontData;
import allbinary.business.context.modules.storefront.StoreFrontFactory;


import allbinary.logic.control.search.SearchRequest;
import allbinary.logic.control.search.SearchParams;

import allbinary.logic.visual.transform.info.TransformInfoData;

import allbinary.logic.communication.http.AcceptableResponseGenerator;
import java.util.HashMap;

public class StaticPagesRequestHelper extends AbContext
    implements TagHelperInterface
{
   private HttpServletRequest request;
   
   private String storeName;
   private SearchParams searchParams;
   private String xslFile;

   public StaticPagesRequestHelper(HashMap propertiesHashMap, PageContext pageContext)
   {
      super(propertiesHashMap, pageContext);

      //LogUtil.put(LogFactory.getInstance("Start", this, "Constructor()"));

      this.request = (HttpServletRequest) pageContext.getRequest();
      this.xslFile = (String) propertiesHashMap.get(
          TransformInfoData.getInstance().TEMPLATEFILENAME);
      this.getFormData();
   }
      
   private void getFormData()
   {      
      this.storeName = request.getParameter(StoreFrontData.getInstance().NAME);
      this.searchParams = new SearchParams(this.request);
   }

   private void email() throws Exception
   {
      try
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.EMAILLOGGING))
         {
            LogUtil.put(LogFactory.getInstance("Generated Static Pages Notification Email", this, "email"));
         }

         StoreFrontInterface storeFrontInterface = 
            StoreFrontFactory.getInstance(this.storeName);
            
         String adminEmailSubject = "Generated Static Pages Email Notification";
         String adminEmailTextBody = "Generated static pages for store: " + this.storeName;

         BasicEmailInfo adminBasicEmailInfo = (BasicEmailInfo)
            new AdminEmailInfo(adminEmailSubject, adminEmailTextBody);
         
         BasicEmailInfo storeAdminBasicEmailInfo = (BasicEmailInfo)
            new StoreEmailInfo(storeFrontInterface, adminEmailSubject, adminEmailTextBody);
         
         EmailInfo storeAdminEmailInfo = new EmailInfo(storeAdminBasicEmailInfo);

         EmailInfo adminEmailInfo = new EmailInfo(adminBasicEmailInfo);
         
         //Send response to Admin(s)
         UserEmailEventHandler adminUserEmailEventHandler =
            AdminUserEmailEventHandlerSingletons.getInstance(
               UserEmailEventNameData.STOREGENERATINGSTATICPAGES);

         UserEmailEventHandler storeAdminUserEmailEventHandler =
            StoreAdminUserEmailEventHandlerSingletons.getInstance(
               UserEmailEventNameData.STOREGENERATINGSTATICPAGES, storeFrontInterface);

         storeAdminUserEmailEventHandler.receiveEmailInfo(
             UserEmailEventNameData.STOREGENERATINGSTATICPAGES, storeAdminEmailInfo);
         adminUserEmailEventHandler.receiveEmailInfo(
             UserEmailEventNameData.STOREGENERATINGSTATICPAGES, adminEmailInfo);
      }
      catch(Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.EMAILLOGGINGERROR))
         {
            LogUtil.put(LogFactory.getInstance("Command Failed", this, "email", e));
         }
         //throw e;
      }
   }

   public String generateStaticPages()
   {
      try
      {
         //LogUtil.put(LogFactory.getInstance("Start", this, "generateStaticPages()"));

         String contentType = AcceptableResponseGenerator.get(this.request); 
         
         SearchRequest searchRequest = 
            new SearchRequest(
            null, this.searchParams, xslFile, contentType, 
            this.getPropertiesHashMap(), this.getPageContext());
         
         String success = ProductListingFactory.getInstance(searchRequest).generateAll(storeName);

         this.email();
         
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGS))
         {
            LogUtil.put(LogFactory.getInstance(success, this, "generateStaticPages()"));
         }

         return success;
      }
      catch(Exception e)
      {
         String error = "Failed to generate staticpages table";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGSERROR))
         {
            LogUtil.put(LogFactory.getInstance(error,this,"generateStaticPages()",e));
         }
         return error;
      }
   }

   public String makePublic()
   {
      try
      {
         StoreFrontInterface storeFrontInterface = 
            StoreFrontFactory.getInstance(this.storeName);

         AbPath fromAbPath = new AbPath(storeFrontInterface.getTestHtmlPath() + 
            storeFrontInterface.getStaticPath());

         AbPath toAbPath = new AbPath(URLGLOBALS.getWebappPath() + 
            storeFrontInterface.getName() + 
            AbPathData.getInstance().SEPARATOR + storeFrontInterface.getStaticPath());

         FileUtil.getInstance().copy(fromAbPath, toAbPath);

         String success = "Made Public";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGS))
         {
            LogUtil.put(LogFactory.getInstance(success, this,"makePublic()"));
         }

         return success;
      }
      catch(Exception e)
      {
         String error = "Failed to makePublic";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGSERROR))
         {
            LogUtil.put(LogFactory.getInstance(error,this,"makePublic()",e));
         }
         return error;
      }
   }
   
}

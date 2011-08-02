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
package allbinary.business.user.commerce.money.tax.modules;

import abcs.data.tree.dom.document.DomDocumentHelper;
import abcs.data.tree.dom.DomNodeHelper;
import abcs.data.tree.dom.DomSearchHelper;
import abcs.globals.URLGLOBALS;
import abcs.logic.basic.path.AbPath;
import abcs.logic.basic.string.StringValidationUtil;
import abcs.logic.communication.log.LogFactory;
import abcs.logic.communication.log.LogUtil;
import allbinary.business.context.modules.storefront.StoreFrontInterface;
import allbinary.business.user.address.StreetAddress;
import allbinary.business.user.address.StreetAddressData;
import allbinary.business.user.commerce.money.tax.components.TaxData;
import allbinary.globals.FREEBLISKET_PATH_GLOBALS;
import allbinary.logic.control.crypt.file.CryptFileReader;
import allbinary.logic.visual.transform.info.objectConfig.TransformInfoObjectConfigData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SimpleStateTaxModule implements TaxModuleInterface
{
   private StreetAddress streetAddress;
   private StoreFrontInterface storeFrontInterface;

   private final String ALL = "*";
   private final String STATETAXPATH = "/generic/taxes/";
   private final String STATETAXFILE = "stateTaxation.xml";
   
   private Document document;
   
   public SimpleStateTaxModule() //throws Exception
   {
      
      //StreetAddress streetAddress;
      //StoreFrontInterface storeFrontInterface;

      //this.streetAddress = streetAddress;
      //this.storeFrontInterface = storeFrontInterface;
   }
   
   /*
   public Float getTax(Float taxableItem)
   {
    
   }
    */
   
   /*
   public Float getTaxRate()
   {
      String state = this.streetAddress.getState();
      String country = this.streetAddress.getCountry();
    
      state = state.toUpperCase();
      if(country.compareTo("USA")==0 &&
         (state.compareTo("TX")==0 || state.compareTo("TEXAS")==0))
      {
         return new Float(.0825);
      }
      else return new Float(0);
   }
    */
   
   public Float getTaxRate(StreetAddress streetAddress, StoreFrontInterface storeFrontInterface) throws Exception
   {
      this.streetAddress = streetAddress;
      this.storeFrontInterface = storeFrontInterface;
      
      AbPath fileAbPath = new AbPath(
         URLGLOBALS.getMainPath() +
         FREEBLISKET_PATH_GLOBALS.getInstance().XSLPATH +
         this.storeFrontInterface.getName() + this.STATETAXPATH, 
	 this.STATETAXFILE);
      
      String data = new CryptFileReader(
         TransformInfoObjectConfigData.getInstance().UNCRYPTED_EXTENSION,
         TransformInfoObjectConfigData.getInstance().ENCRYPTED_EXTENSION).get(fileAbPath);
      
      this.document = DomDocumentHelper.create(data);
      
      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.TAX))
      {
         LogUtil.put(LogFactory.getInstance("Tax Doc: " + DomDocumentHelper.toString(document), this,"getTaxRate"));
      }
      
      String state = this.streetAddress.getState();
      String country = this.streetAddress.getCountry();
      
      state = state.toUpperCase();
      
      NodeList nodeList = document.getElementsByTagName(TaxData.NAME);
      
      for(int index = 0; index < nodeList.getLength(); index++)
      {
         Node node = nodeList.item(index);
         NodeList taxNodeChildren = node.getChildNodes();
                  
         Node streetAddressNode =
            DomSearchHelper.getNode(StreetAddressData.NAME, taxNodeChildren);
         
         StreetAddress taxableStreetAddress = new StreetAddress(streetAddressNode);                  
         
         StringValidationUtil stringValidationUtil = StringValidationUtil.getInstance();
         
         if(!stringValidationUtil.isEmpty(country) && 
            !stringValidationUtil.isEmpty(taxableStreetAddress.getCountry()) && 
            country.compareTo(taxableStreetAddress.getCountry())==0)
         {
            if(state != null && taxableStreetAddress.getState() != null && 
               state.compareTo(taxableStreetAddress.getState())==0)
            {
               Node rateNode =
                  DomSearchHelper.getNode(TaxData.RATE, taxNodeChildren);
               String taxRate = DomNodeHelper.getTextNodeValue(rateNode);

               Float taxRateFloat = new Float(new Float(taxRate).floatValue() * .01);
               return taxRateFloat;
            }
         }
         
      }
      
      return new Float(0);
   }

   public Boolean isValid(StreetAddress streetAddress, StoreFrontInterface storeFrontInterface)
   {
      try
      {

      this.streetAddress = streetAddress;
      this.storeFrontInterface = storeFrontInterface;
      
      AbPath fileAbPath = new AbPath(
         URLGLOBALS.getMainPath() +
         FREEBLISKET_PATH_GLOBALS.getInstance().XSLPATH +
         this.storeFrontInterface.getName() + this.STATETAXPATH, 
	 this.STATETAXFILE);

      String data = new CryptFileReader(
         TransformInfoObjectConfigData.getInstance().UNCRYPTED_EXTENSION,
         TransformInfoObjectConfigData.getInstance().ENCRYPTED_EXTENSION).get(fileAbPath);

      this.document = DomDocumentHelper.create(data);

      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.TAX))
      {
         LogUtil.put(LogFactory.getInstance("Tax Doc: " + DomDocumentHelper.toString(document), this, "isValid"));
      }

      String country = this.streetAddress.getCountry();

      Node rootNode = this.document.getElementsByTagName(
    		  TransformInfoObjectConfigData.getInstance().NAME).item(0);
      
      //rootNode.getChildNodes();
      NodeList nodeList = document.getElementsByTagName(TaxData.NAME);

      /*
      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.TAX))
      {
         LogUtil.put("Number Of Tax Nodes: " + nodeList.getLength(), this,"isValid()");
      }
      */
      
      for(int index = 0; index < nodeList.getLength(); index++)
      {
         Node node = nodeList.item(index);

         /*
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.TAX))
         {
            LogUtil.put("Node: " + node.getNodeName(), this,"isValid()");
         }
         */
         
         NodeList taxNodeChildren = node.getChildNodes();

         /*
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.TAX))
         {
            LogUtil.put("Number Of Tax Child Nodes: " + taxNodeChildren.getLength(), this,"isValid()");
         }
         */
         
         Node streetAddressNode =
            DomSearchHelper.getNode(StreetAddressData.NAME, taxNodeChildren);

         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.TAX))
         {
             StringBuilder stringBuffer = new StringBuilder();

             stringBuffer.append("StreetAddress Node: ");
             stringBuffer.append(streetAddressNode.getNodeName());
             stringBuffer.append(" Value: ");
             stringBuffer.append(streetAddressNode.getNodeValue());
             stringBuffer.append(" Number Of Children: ");
             stringBuffer.append(streetAddressNode.getChildNodes().getLength());

            LogUtil.put(LogFactory.getInstance(stringBuffer.toString(), this,"isValid"));
         }

         StreetAddress taxableStreetAddress = new StreetAddress(streetAddressNode);                  

         StringValidationUtil stringValidationUtil = StringValidationUtil.getInstance();
         
         if(!stringValidationUtil.isEmpty(country) && 
            !stringValidationUtil.isEmpty(taxableStreetAddress.getCountry()) && 
            taxableStreetAddress.getCountry().compareTo(ALL) == 0)
         {
            return Boolean.TRUE;            
         }         

         if(!stringValidationUtil.isEmpty(country) && 
            !stringValidationUtil.isEmpty(taxableStreetAddress.getCountry()) && 
            country.compareTo(taxableStreetAddress.getCountry()) == 0)
         {
            String state = this.streetAddress.getState();
            if(state != null)
            {
               return Boolean.TRUE;
            }
         }         
      }
      return Boolean.FALSE;
      
      }
      catch(Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.VIEWERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failed to validate", this, "isValid()", e));
         }
         return Boolean.FALSE;
      }
   }
}

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
package allbinary.logic.communication.http.request;

import abcs.logic.basic.string.StringUtil;
import abcs.logic.basic.string.tokens.Tokenizer;
import abcs.logic.communication.log.LogFactory;
import abcs.logic.communication.log.LogUtil;
import allbinary.logic.visual.dhtml.html.name.HtmlNameMathData;

import java.util.HashMap;
import java.util.Vector;
import org.allbinary.util.BasicArrayList;

public class NameSpaceRequestParam
{
   private Vector nameSpaceVector;

   //A hashmap of hashmaps
   private HashMap nameSpacePropertiesHashMap;

   private String value;
   public NameSpaceRequestParam(String nameSpace, String value) throws Exception
   {
      this.nameSpaceVector = new Vector();
      this.nameSpacePropertiesHashMap = new HashMap();
      this.value = value;

      int beginIndex = nameSpace.indexOf(NameSpaceRequestParamData.NAME);
      if(beginIndex < 0)
      {
         throw new Exception("Not a NameSpaceRequest");
      }

      String packages = nameSpace.substring(beginIndex + 
         NameSpaceRequestParamData.NAME.length() + 
         NameSpaceRequestParamData.SEP.length());

      Tokenizer sepTokenizer = new Tokenizer(NameSpaceRequestParamData.SEP);
      BasicArrayList nameSpaceWithPropertiesVector = sepTokenizer.getTokens(packages, new BasicArrayList());

      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(
         abcs.logic.communication.log.config.type.LogConfigType.HTTPREQUEST))
      {
         LogUtil.put(LogFactory.getInstance(
                 "\nSep Tokens: " + nameSpaceWithPropertiesVector.toString(), this, "NameSpaceRequestParam()"));
      }
      
      Tokenizer nameSpaceAndPropertiesTokenizer = 
         new Tokenizer(NameSpaceRequestParamData.PROPERTIES);

      int packageIndex = 0;

      final BasicArrayList list = new BasicArrayList();
      
      int index = 0;
      
      int size = nameSpaceWithPropertiesVector.size();
      while(index < size)
      {
         String nameSpaceWithProperties = (String) nameSpaceWithPropertiesVector.get(index++);

         //A packageName and packageName properties vector
         // ["nameSpace", "properties"]
         list.clear();
         BasicArrayList nameSpaceAndPropertiesVector = 
            nameSpaceAndPropertiesTokenizer.getTokens(nameSpaceWithProperties, list);

         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(
            abcs.logic.communication.log.config.type.LogConfigType.HTTPREQUEST))
         {
            LogUtil.put(LogFactory.getInstance("\nPackageName and PackageName Properties Tokens: " +
               nameSpaceAndPropertiesVector.toString(),
               this, "NameSpaceRequestParam()"));
         }

         //add packageName and packageName properties
         if(nameSpaceAndPropertiesVector.size() > 0)
         {
            //add packageName
            String packageName = (String) nameSpaceAndPropertiesVector.get(0);
            
            if(packageName == null)
            {
               throw new Exception("Package Name Is Null");
            }

            this.nameSpaceVector.add(packageName);

            //packageName properties
            if(nameSpaceAndPropertiesVector.size() > 1)
            {
               String properties = (String) nameSpaceAndPropertiesVector.get(1);

               HashMap packagePropertiesHashMap = 
                  this.generatePackagePropertiesHashMap(properties);
               this.nameSpacePropertiesHashMap.put(
                  Integer.toString(packageIndex), packagePropertiesHashMap);
            }
            packageIndex++;
         }
      }

      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(
      abcs.logic.communication.log.config.type.LogConfigType.HTTPREQUEST))
      {
         LogUtil.put(LogFactory.getInstance("NameSpace: " + this.nameSpaceVector.toString(),
         this, "NameSpaceRequestParam()"));
      }
   }
   
   public Vector getPackages()
   {
      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(
      abcs.logic.communication.log.config.type.LogConfigType.HTTPREQUEST))
      {
         LogUtil.put(LogFactory.getInstance("NameSpace: " + this.nameSpaceVector.toString(),
         this, "getPackages()"));
      }
      return this.nameSpaceVector;
   }

   public HashMap getPackageProperties(int packageIndex)
   {
      HashMap packagePropertiesHashMap = 
         (HashMap) this.nameSpacePropertiesHashMap.get(
            Integer.toString(packageIndex));

      if(packagePropertiesHashMap == null)
      {
         packagePropertiesHashMap = new HashMap();
      }

      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(
      abcs.logic.communication.log.config.type.LogConfigType.HTTPREQUEST))
      {
         LogUtil.put(LogFactory.getInstance("Package Properties: " + packagePropertiesHashMap.toString(),
            this, "getPackagesProperties()"));
      }

      return packagePropertiesHashMap;
   }

   public String getValue()
   {
      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(
         abcs.logic.communication.log.config.type.LogConfigType.HTTPREQUEST))
      {
         LogUtil.put(LogFactory.getInstance("\nValue: " + this.value, this, "getValue()"));
      }
      
      return this.value;
   }

   private HashMap generatePackagePropertiesHashMap(String properties) throws Exception
   {
      Tokenizer propertiesTokenizer = new Tokenizer(NameSpaceRequestParamData.PROPERTIESSEPARATOR);

      HashMap packagePropertiesHashMap = new HashMap();

      BasicArrayList propertyVector = propertiesTokenizer.getTokens(properties, new BasicArrayList());

      //First Item is Always the key the rest are Properties
      
      final BasicArrayList list = new BasicArrayList();
      
      final Tokenizer propertyTokenizer = new Tokenizer(HtmlNameMathData.getInstance().EQUALS);
      
      int index = 0;
      int size = propertyVector.size();
      while(index < size)
      {
         String property = (String) propertyVector.get(index);

         list.clear();
         BasicArrayList propertyNameValueVector = propertyTokenizer.getTokens(property, list);

         int index2 = 0;
         while(index2 < propertyNameValueVector.size())
         {
            String propertyName = (String) propertyNameValueVector.get(index2++);

            if(propertyNameValueVector.size() > 1)
            {
               String propertyValue = (String) propertyNameValueVector.get(index2++);

               if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(
                  abcs.logic.communication.log.config.type.LogConfigType.HTTPREQUEST))
               {
                  LogUtil.put(LogFactory.getInstance(
                          "\nProperty Name: " + propertyName + "\nProperty Value: " + propertyValue,
                     this, "generatePackagePropertiesHashMap()"));
               }
               packagePropertiesHashMap.put(propertyName, propertyValue);
            }
            else
            {
               packagePropertiesHashMap.put(propertyName, StringUtil.getInstance().EMPTY_STRING);
            }
         }
      }
      return packagePropertiesHashMap;
   }

   /*
   public String toString()
   {
      StringBuffer stringBuffer = new StringBuffer();
      
      Iterator iter = this.nameSpaceVector.iterator();
      while(iter.hasNext())
      {
         String packageName = (String) iter.next();
         stringBuffer.append(packageName);
         if(iter.hasNext())
            stringBuffer.append(NameSpaceRequestParamData.SEP);
      }

      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(
         abcs.logic.communication.log.config.type.LogConfigType.VIEW))
      {
         LogUtil.put("\nNameSpace Param: " + stringBuffer.toString(),
            this, "toString()");
      }
      
      return stringBuffer.toString();
   }
    **/
}

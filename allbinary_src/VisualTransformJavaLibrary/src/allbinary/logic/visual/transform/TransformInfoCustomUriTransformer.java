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
package allbinary.logic.visual.transform;

import allbinary.data.tree.dom.BasicUriResolver;
import allbinary.data.tree.dom.CustomUriResolver;
import allbinary.logic.visual.transform.info.TransformInfoInterface;
import allbinary.logic.visual.transform.info.objectConfig.TransformInfoObjectConfigAndManipulatorFactory;
import allbinary.logic.visual.transform.info.objectConfig.TransformInfoObjectConfigInterface;

import javax.xml.transform.URIResolver;

public class TransformInfoCustomUriTransformer extends BasicTransformer
{
   public TransformInfoCustomUriTransformer( 
      TransformInfoInterface transformInfoInterface) throws Exception
   {
      super(transformInfoInterface);

      TransformInfoObjectConfigInterface transformInfoObjectConfigInterface =
         (TransformInfoObjectConfigInterface)
         TransformInfoObjectConfigAndManipulatorFactory.getInstance().getInstance(transformInfoInterface);
      
      this.setURIResolver(
         (URIResolver) new CustomUriResolver(
         transformInfoObjectConfigInterface.getImportUriPath(), 
         (BasicUriResolver) this.getURIResolver()));
   }
}
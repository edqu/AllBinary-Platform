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
package tags;

import abcs.logic.system.security.licensing.LicensingException;

import abcs.logic.communication.http.request.AbResponseHandler;
import abcs.logic.communication.log.LogFactory;

import admin.taghelpers.TagHelperFactoryInterface;

import abcs.logic.communication.log.LogUtil;
import javax.servlet.jsp.JspTagException;

import taghelpers.ViewHelperFactory;

import tags.transform.info.TransformInfoTag;

public class TransformTag extends TransformInfoTag
{
    //The View HelperObject must contain a
    //view object that implements the view interface

    public TransformTag()
    {
        super(new ViewHelperFactory());
        //this.helperObject = null;
        if (abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.JSPTAG))
        {
            LogUtil.put(LogFactory.getInstance("Tag Constructed", this, "Constructor"));
        }
    }

    public TransformTag(TagHelperFactoryInterface tagHelperFactoryInterface)
    {
        super(tagHelperFactoryInterface);
        if (abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.JSPTAG))
        {
            LogUtil.put(LogFactory.getInstance("Tag Constructed", this, "Constructor(Factory)"));
        }
    }

    protected String view() throws Exception
    {
        try
        {
            String result = (String) this.getHelper().getClass().getMethod(
                "view", null).invoke(this.getHelper(), null);
            return result;
        } catch (Exception e)
        {
            String error = "Failed to View";

            if (abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.JSPTAGERROR))
            {
                LogUtil.put(LogFactory.getInstance(error, this, "view()", e));
            }
            throw e;
        }
    }

    public int doStartTag() throws JspTagException
    {
        try
        {
            if (abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.JSPTAG))
            {
                LogUtil.put(LogFactory.getInstance(
                    "Creating with: \n" + this.getPropertiesHashMap().toString(), this, "doStartTag"));
            }

            if (abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.JSPTAG))
            {
                StringBuffer stringBuffer = new StringBuffer();

                stringBuffer.append("ViewTag Start For: ");
                stringBuffer.append(this.getName());
                stringBuffer.append("\nViewFile: ");
                stringBuffer.append(this.getObjectFile());

                LogUtil.put(LogFactory.getInstance(stringBuffer.toString(), this, "doStartTag"));
            }

            this.setHelper();

            pageContext.getOut().print(this.view());

            if (abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.JSPTAG))
            {
                LogUtil.put(LogFactory.getInstance("Tag End", this, "doStartTag"));
            }

            return SKIP_BODY;
        } catch (LicensingException e)
        {
            AbResponseHandler.sendJspTagLicensingRedirect(this.pageContext, e);
            return SKIP_BODY;
        } catch (Exception e)
        {
            AbResponseHandler.sendJspTagRedirect(this.pageContext, e);
            return SKIP_BODY;
        }
    }
}

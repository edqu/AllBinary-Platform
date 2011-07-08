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

import abcs.logic.basic.string.StringValidationUtil;
import abcs.logic.communication.log.LogFactory;

import abcs.logic.communication.log.LogUtil;

import allbinary.business.context.modules.storefront.StoreFrontData;
import allbinary.business.context.modules.storefront.StoreFrontFactory;
import allbinary.business.context.modules.storefront.StoreFrontInterface;
import allbinary.business.user.commerce.inventory.order.Order;

import allbinary.business.user.commerce.inventory.order.OrderInterface;
import allbinary.business.user.commerce.inventory.order.OrderProcessorUtil;

import allbinary.business.user.commerce.money.payment.gateway.PaymentGatewayData;
import allbinary.business.user.commerce.money.payment.gateway.PaymentGatewayInterface;
import allbinary.business.user.commerce.money.payment.types.BasicPaymentType;


import allbinary.data.tables.user.commerce.money.payment.gateway.PaymentGatewayEntity;
import allbinary.data.tables.user.commerce.money.payment.gateway.PaymentGatewayEntityFactory;

import allbinary.logic.communication.http.request.session.WeblisketSession;
import java.util.HashMap;
import java.util.Vector;

public class OrderHelper
    implements TagHelperInterface
{
    private WeblisketSession weblisketSession;
    private StoreFrontInterface storeFrontInterface;
    private HashMap propertiesHashMap;
    private PageContext pageContext;
    private HttpServletRequest request;

    public OrderHelper(HashMap propertiesHashMap, PageContext pageContext)
    {
        this.propertiesHashMap = propertiesHashMap;
        this.pageContext = pageContext;

        this.request = (HttpServletRequest) pageContext.getRequest();

        String storeName = (String) propertiesHashMap.get(StoreFrontData.getInstance().NAME);

        if (!StringValidationUtil.getInstance().isEmpty(storeName))
        {
            this.storeFrontInterface = StoreFrontFactory.getInstance(storeName);
        }

        this.weblisketSession =
            new WeblisketSession(this.propertiesHashMap, this.pageContext);
    }

    public Boolean setPaymentGateway()
    {
        try
        {
            Boolean paymentGatewayBoolean = Boolean.FALSE;

            OrderInterface orderInterface = this.weblisketSession.getOrder();

            //Set Payment Method to Request Param
            String requestPaymentGateway = (String) request.getParameter(PaymentGatewayData.NAME.toString());
            if (!StringValidationUtil.getInstance().isEmpty(requestPaymentGateway))
            {
                this.weblisketSession.setPaymentMethod(requestPaymentGateway);
                orderInterface.setPaymentMethod(requestPaymentGateway);
                paymentGatewayBoolean = Boolean.TRUE;
            }

            //Otherwise set if only one payment gateway is available
            PaymentGatewayEntity paymentGatewayEntityInterface = (PaymentGatewayEntity) PaymentGatewayEntityFactory.getInstance();
            Vector paymentTypeVector =
                paymentGatewayEntityInterface.findPaymentTypeVectorByStore(
                this.weblisketSession.getStoreName());

            if (paymentTypeVector.size() == 1)
            {
                BasicPaymentType paymentType = (BasicPaymentType) paymentTypeVector.get(0);
                PaymentGatewayInterface paymentGatewayInterface =
                    (PaymentGatewayInterface) paymentGatewayEntityInterface.getPaymentGatewayInterface(
                    this.weblisketSession.getStoreName(), paymentType);

                String paymentGateway = paymentGatewayInterface.getName();
                this.weblisketSession.setPaymentMethod(paymentGateway);
                orderInterface.setPaymentMethod(paymentGateway);
                paymentGatewayBoolean = Boolean.TRUE;
            }

            if (abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGS))
            {
                StringBuffer stringBuffer = new StringBuffer();

                stringBuffer.append("Successfully set PaymentGateway Order: ");
                stringBuffer.append(orderInterface.getId());
                stringBuffer.append(" to: ");
                stringBuffer.append(orderInterface.getPaymentMethod());

                LogUtil.put(LogFactory.getInstance(stringBuffer.toString(), this, "setPaymentGateway()"));
            }
            return paymentGatewayBoolean;
        } catch (Exception e)
        {
            if (abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGSERROR))
            {
                StringBuffer stringBuffer = new StringBuffer();

                stringBuffer.append("Failed to set PaymentGateway for Order: ");

                try
                {
                    OrderInterface orderInterface = this.weblisketSession.getOrder();
                    if (orderInterface != null)
                    {
                        stringBuffer.append(orderInterface.getId());
                        stringBuffer.append(" to: ");
                        stringBuffer.append(orderInterface.getPaymentMethod());
                    }
                } catch (Exception ex)
                {
                    stringBuffer.append(" Exception Getting");
                }

                LogUtil.put(LogFactory.getInstance(stringBuffer.toString(), this, "setPaymentGateway()", e));
            }
            return Boolean.FALSE;
        }
    }

    public String process()
    {
        try
        {
            OrderInterface order = this.weblisketSession.getOrder();
            order.setStoreName(this.storeFrontInterface.getName());
            String result = OrderProcessorUtil.getInstance().process(
                this.weblisketSession.getUserName(), (Order) order);

            //OrderHistoryEntityFactory.getInstance().setPaymentMethod(order.getId(),this.paymentMethod);

            if (abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGS))
            {
                LogUtil.put(LogFactory.getInstance("Successfully Processed Order: " + result, this, "processOrder()"));
            }
            return result;

        } catch (Exception e)
        {
            final StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append("Failed to Process Order: ");

            try
            {
                stringBuffer.append(this.weblisketSession.getOrder().getId());

            } catch (Exception ex)
            {
                stringBuffer.append(" Exception Getting Id");
            }

            final String error = stringBuffer.toString();

            if (abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGSERROR))
            {
                LogUtil.put(LogFactory.getInstance(error, this, "process()", e));
            }
            return error;
        }
    }

    /*
    public Boolean authorize()
    {
    try
    {
    OrderInterface order = getOrder();
    OrderHistory orderReview = OrderHistoryEntityFactory.getInstance().getOrder(order.getId());
    Boolean result = orderReview.authorizeDelayedCaptureCreditCard();

    if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGS))
    {
    LogUtil.put(LogFactory.getInstance("Successfully Authorized Order: " + result,this,"authorizeOrder()");
    }
    return result;
    }
    catch(Exception e)
    {
    String error = "Failed to Authorized Order: ";

    if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.SQLTAGSERROR))
    {
    LogUtil.put(LogFactory.getInstance(error,this,"authorizeOrder()",e);
    }
    return Boolean.FALSE;
    }
    }
     */
}

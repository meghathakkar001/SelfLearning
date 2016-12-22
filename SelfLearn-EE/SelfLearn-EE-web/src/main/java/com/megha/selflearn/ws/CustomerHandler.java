/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.megha.selflearn.ws;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.w3c.dom.Element;

/**
 *
 * @author MEGHAT
 */
public class CustomerHandler implements SOAPHandler<SOAPMessageContext>{
    private static final String WSSE_NS_URI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    private static final QName QNAME_WSSE_USERNAMETOKEN = new QName(WSSE_NS_URI, "UsernameToken");
    private static final QName QNAME_WSSE_USERNAME = new QName(WSSE_NS_URI, "Username");
    private static final QName QNAME_WSSE_PASSWORD = new QName(WSSE_NS_URI, "Password");


    @Override
    public Set getHeaders() {
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outbound = (Boolean) context
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if ((outbound != null) && (!outbound.booleanValue())) {
            handleInboundMessage(context);
        }
        
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close(MessageContext context) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void handleInboundMessage(SOAPMessageContext context) {
        SOAPHeader header;
        String wsseUsername=null;
        String wssePassword=null;
        try {
            header = context.getMessage().getSOAPHeader();
            Iterator<?> headerElements = header.examineAllHeaderElements();
            while (headerElements.hasNext()){
                SOAPElement headerElement= (SOAPElement)headerElements.next();
                System.out.println("node is"+ headerElement.getNodeName());
                if(headerElement.getLocalName().equals("Security")){
                    
                    Iterator<?> it2 = headerElement.getChildElements();
                    while (it2.hasNext()) {
                        Node soapNode = (Node) it2.next();
                        if (soapNode instanceof SOAPElement) {
                            SOAPElement element = (SOAPElement) soapNode;
                            QName elementQname = element.getElementQName();
                            if (QNAME_WSSE_USERNAMETOKEN.equals(elementQname)) {
                                SOAPElement usernameTokenElement = element;
                                wsseUsername = getFirstChildElementValue(usernameTokenElement, QNAME_WSSE_USERNAME);
                                wssePassword = getFirstChildElementValue(usernameTokenElement, QNAME_WSSE_PASSWORD);
                                break;
                            }
                        }

                    }
                }
                
                
            }
            
            if( wsseUsername!=null && wssePassword!=null ){
                context.put("USERNAME", wsseUsername);
                context.setScope("USERNAME", Scope.APPLICATION);
                context.put("PASSWORD", wssePassword);
                context.setScope("PASSWORD", Scope.APPLICATION);
            }
            
            //context.setMessage(MessageFactory.newInstance().createMessage());
        } catch (SOAPException ex) {
            Logger.getLogger(CustomerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private String getFirstChildElementValue(SOAPElement soapElement, QName qNameToFind) {
        String value = null;
        Iterator<?> it = soapElement.getChildElements(qNameToFind);
        while (it.hasNext()) {
            SOAPElement element = (SOAPElement) it.next(); //use first
            value = element.getValue();
        }
        return value;
    }

    
    
}

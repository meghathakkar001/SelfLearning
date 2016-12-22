/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.megha.selflearn.ws;

import com.megha.selflearn.rs.CustomerService;
import com.megha.selflearn.model.Customer;
import com.sun.security.auth.callback.TextCallbackHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.xml.ws.WebServiceContext;

/**
 *
 * @author MEGHAT
 */
@WebService(serviceName = "CustomerSoapService")
@HandlerChain(file="handlers.xml")
public class CustomerSoapService {
    @Resource
    private WebServiceContext ctx;
    /**
     * This is a sample web service operation
     * @param cust
     * @return 
     * @throws java.io.IOException 
     */
    public String create(@WebParam Customer cust) throws IOException{
        
     
       String username=(String) ctx.getMessageContext().get("USERNAME");
       String password=(String) ctx.getMessageContext().get("PASSWORD");
       
        try {
            LoginContext lc = new LoginContext("fileRealm",
                    new TextCallbackHandler());
            lc.login();
        } catch (LoginException ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException("could not add customer to DB");
        }
      
        if (cust!=null && cust.getId() <=5){
            System.out.println(cust.getId());
            System.out.println(cust.getName());
            return "OK"+username+password;
        }
        throw new IOException("could not add customer to DB");
    }
}

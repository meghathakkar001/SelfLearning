/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.megha.selflearn.rs;


import com.megha.selflearn.model.Customer;
import com.sun.security.auth.callback.TextCallbackHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.HandlerChain;
import javax.naming.InitialContext;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;


/**
 *
 * @author MEGHAT
 */
@Stateless
@ApplicationPath("/restful")
@Path("/cust")

public class CustomerService extends Application{
    Map<Long, Customer> customers= new HashMap<Long, Customer>();
    
    
    
    
     @Resource(lookup="jms/__defaultConnectionFactory")
    ConnectionFactory factory;
    
    @Resource (lookup="jms/testQueue")
    Queue myQueue;
    
    
    
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    
    public Customer put(Customer cust){
        
        customers.put(cust.getId(), cust);
        return cust;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    
    public Customer post(Customer cust){
        
        customers.put(cust.getId(), cust);
        return cust;
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Customer delete(Customer cust){
        
        customers.remove(cust.getId());
        return cust;
    }
    
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response get(@PathParam("id") long id) throws JMSException{
        
        Customer cust= customers.get(id);
        if(cust!=null){
            return Response.ok(cust).build();
        }
        return Response.ok().status(Response.Status.NO_CONTENT).build();
        /*Session session=factory.createConnection().createSession();
        MessageProducer producer=session.createProducer(myQueue);
        producer.send(session.createTextMessage(cust.getName()));
        System.out.println("-----------------------DONE----------------------------");
         */
        
        
     }
}

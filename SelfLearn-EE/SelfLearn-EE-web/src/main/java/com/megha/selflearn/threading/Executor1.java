/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.megha.selflearn.threading;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MEGHAT
 */
public class Executor1 {

    public static void main(String args[]) throws InterruptedException, ExecutionException{
        ExecutorService pool=Executors.newFixedThreadPool(1);
        
        
        
        //pool.execute(new myRunnable());
        String str = null;
        pool.submit(new myRunnable(), str);
        
        while(null==str){
            str.wait();
        }
        Future<String> future=pool.submit((Callable) new myRunnable());
        while(!future.isDone()){
            
        }
        System.out.println("str is: "+future.get());
        
        
    }
    
    static class myRunnable<String> implements Runnable, Callable{
        
        //@Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" started, run");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Executor1.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(Thread.currentThread().getName()+" finished, run");
            
        }

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()+" started");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Executor1.class.getName()).log(Level.SEVERE, null, ex);
            }
            //String tobereturned;
            //tobereturned = (String) "random";
            System.out.println(Thread.currentThread().getName()+" finished, I am going to return");
            return (String) "mystr";
        }
    }
    
}
    


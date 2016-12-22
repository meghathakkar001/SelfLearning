/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.megha.selflearn.threading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author meghat
 */
public class LiveLock {
    Lock mylock= new ReentrantLock();
    
    public  void method1(LiveLock b) throws InterruptedException{
        System.out.println(Thread.currentThread().getName()+"method1: start");
        boolean gotmylock=false;
        boolean gototherlock=false;
        while(!(gotmylock && gototherlock)){
         gotmylock=false;
         gototherlock=false;
        if(mylock.tryLock()){
            
            gotmylock=true;
            System.out.println(Thread.currentThread().getName()+"method1: gotmylock");
            
            if(b.mylock.tryLock()){
                gototherlock=true;
                
            }else{
                Thread.sleep(1000);
                mylock.unlock();
                System.out.println(Thread.currentThread().getName()+"method1: did not get other lock :( releasing mylock");
            }
        }
        }
        mylock.unlock();
        b.mylock.unlock();
        
        System.out.println(Thread.currentThread().getName()+"method1: end");
    }
        

    
    
    public static void main(String args[]){
        LiveLock a=new LiveLock();
        LiveLock b=new LiveLock();
        new Thread(new MyRunnable2(a,b)).start();
        new Thread(new MyRunnable2(b,a)).start();
        
    }
   public static class MyRunnable2 implements Runnable{
       LiveLock a;
       LiveLock b;
       
       MyRunnable2(LiveLock a, LiveLock b){
           this.a=a;
           this.b=b;
       }

        @Override
        public void run() {
            
           try {
               a.method1(b);
           } catch (InterruptedException ex) {
               Logger.getLogger(Deadlock.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
       
       
   }
    
}

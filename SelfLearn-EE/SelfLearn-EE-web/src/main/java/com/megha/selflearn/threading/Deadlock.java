/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.megha.selflearn.threading;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author meghat
 */
public class Deadlock {
    
    public  synchronized void method1(Deadlock b) throws InterruptedException{
        System.out.println(Thread.currentThread().getName()+"method1: start");
        b.method2(this);
        System.out.println(Thread.currentThread().getName()+"method1: end");
    }

    private synchronized void method2(Deadlock a) throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"method2: start");
        //a.method1(this);
        Thread.sleep(1000);
        a.method1(this);
        System.out.println(Thread.currentThread().getName()+"method2: end");
    }
    
    public static void main(String args[]){
        Deadlock a=new Deadlock();
        Deadlock b=new Deadlock();
        new Thread(new MyRunnable(a,b)).start();
        new Thread(new MyRunnable2(a,b)).start();
        
    }
   public static class MyRunnable implements Runnable{
       Deadlock a;
       Deadlock b;
       
       MyRunnable(Deadlock a, Deadlock b){
           this.a=a;
           this.b=b;
       }

        @Override
        public void run() {
            
           try {
               b.method2(a);
           } catch (InterruptedException ex) {
               Logger.getLogger(Deadlock.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
       
       
   }
    
   public static class MyRunnable2 implements Runnable{
       Deadlock a;
       Deadlock b;
       
       MyRunnable2(Deadlock a, Deadlock b){
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.megha.selflearn.threading;

/**
 *
 * @author meghat
 */
public class BasicThread {
    public static void main(String args[]) throws InterruptedException{
        
        Thread thread;
        thread = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                System.out.println("Hey, I am running");
                Thread.sleep(5000);
                System.out.println("And now I end");
                }catch( InterruptedException ex){
                    System.out.println("Sorry, got interrupted");
                }
            }
        });
        thread.start();
        //thread.interrupt();
        thread.join(1000);
        System.out.println("Now main ends");
    }
}

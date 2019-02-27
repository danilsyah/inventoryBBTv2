/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

/**
 *
 * @author danil
 */
public class Start {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      FrmLogin login = new FrmLogin();
      splashScreen splash = new splashScreen();
      splash.setVisible(true);
          try {
              for(int i=0; i<=100; i++)
              {
                  Thread.sleep(50);
                    splash.jProgressBar1.setValue(i);
                    if(i==100){
                        splash.setVisible(false);
                        login.setVisible(true);
                    }
              }
              
          } catch (InterruptedException e) {
          }
    }
    
    //end program hhhhh
}

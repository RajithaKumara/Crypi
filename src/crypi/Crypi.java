/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypi;

import view.Welcome;

/**
 *
 * @author rajitha
 */
public class Crypi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome Crypi");
        Welcome welcome = new Welcome();
        welcome.setVisible(true);
    }

}

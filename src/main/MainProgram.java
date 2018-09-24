package main;

import graphics.Display;

import java.awt.*;

public class MainProgram {

                //Attribute

    //Test

                //Referenzen
            private Display display;


        public MainProgram() {

            display = new Display();

            Serverpanel pannel = new Serverpanel(display);
            display.getActivePanel().drawObjectOnPanel(pannel);
        }

        public static void main(String[] args) {

            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {

                    new MainProgram();
                }
            });
        }
    }

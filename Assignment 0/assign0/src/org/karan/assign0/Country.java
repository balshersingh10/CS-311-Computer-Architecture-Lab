package org.karan.assign0;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Country {
    public static void main(String[] args) throws IOException {

        File myObj = new File("data.txt");
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
        } else {
            System.out.println("File already exists.");
        }
        FileWriter myWriter = new FileWriter("data.txt");
        for(double a=0;a<=0.95;a+=0.05){
            for(int b=5;b<=100;b+=5){
                Border border = new Border(b,1000);
                Sensor sensor = new Sensor(a);
                Infiltrator infi = new Infiltrator(1);
                Clock clock = new Clock(0);
                while ((infi.getVertical_step()!= border.getWidth()+1)){
                    if (clock.getTime()%10==0) {
                        sensor.takeDecision();
                        if (sensor.getSensorA() == 0) {
                            if(infi.getVertical_step()== border.getWidth()){
                                infi.setVertical_step(infi.getVertical_step() + 1);
                                clock.setTime(clock.getTime()+10);
                                break;
                            }
                            if (sensor.getSensorB() == 0 || sensor.getSensorC() == 0 || sensor.getSensorD() == 0) {
                                infi.setVertical_step(infi.getVertical_step() + 1);
                            }
                        }
                    }
                    clock.setTime(clock.getTime()+1);
                }
                System.out.println(clock.getTime());
                myWriter.write(String.format("%d %f %d \n",border.getWidth(),sensor.getP(),clock.getTime()));

            }
        }

        myWriter.close();
    }
}

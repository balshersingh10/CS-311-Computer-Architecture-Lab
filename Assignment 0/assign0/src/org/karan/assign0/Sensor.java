package org.karan.assign0;

import java.util.Random;

public class Sensor {
    private double p;

    public Sensor(double p) {
        this.p = p;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    private int sensorA;
    private int sensorB;
    private int sensorC;
    private int sensorD;
    public void takeDecision(){
        double resultA =  Math.random();
        double resultB =  Math.random();
        double resultC =  Math.random();
        double resultD =  Math.random();
        if (resultA<p){
            setSensorA(1);
        }
        else {
            setSensorA(0);
        }
        if (resultB<p){
            setSensorB(1);
        }
        else {
            setSensorB(0);
        }
        if (resultC<p){
            setSensorC(1);
        }
        else {
            setSensorC(0);
        }
        if (resultD<p){
            setSensorD(1);
        }
        else {
            setSensorD(0);
        }
    }

    public int getSensorA() {
        return sensorA;
    }

    public void setSensorA(int sensorA) {
        this.sensorA = sensorA;
    }

    public int getSensorB() {
        return sensorB;
    }

    public void setSensorB(int sensorB) {
        this.sensorB = sensorB;
    }

    public int getSensorC() {
        return sensorC;
    }

    public void setSensorC(int sensorC) {
        this.sensorC = sensorC;
    }

    public int getSensorD() {
        return sensorD;
    }

    public void setSensorD(int sensorD) {
        this.sensorD = sensorD;
    }


}

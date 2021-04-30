package processor.memorysystem;

import generic.*;
import processor.*;
import configuration.Configuration;

public class Cache implements Element {
    Processor containingProcessor;
    public int latency ;
    int cache_size;
    int miss_address;
    CacheLine[] Cache_Line;
    boolean isPresent = true;
    int calc;

    public Cache(Processor containingProcessor, int latency, int cache_size) {
        this.containingProcessor = containingProcessor;
        this.latency = latency;
        this.cache_size = cache_size;
        calc = (int)(Math.log(this.cache_size /8)/Math.log(2));
        Cache_Line =new CacheLine[cache_size /8];
        for(int i = 0; i< cache_size /8; i++) {
            Cache_Line[i]=new CacheLine();
        }
    }

    public void handleCacheMiss(int addr ) {
        Simulator.getEventQueue().addEvent(
                new MemoryReadEvent(Clock.getCurrentTime()+Configuration.mainMemoryLatency,
                        this,
                        containingProcessor.getMainMemory(),
                        addr));
    }

    public int cacheRead(int address){
        String binary_address = Integer.toBinaryString(address);
        String ind ="";
        int temp_ind;
        for(int i =0;i<32-binary_address.length();i++){
            binary_address = "0" + binary_address;
        }
        for(int i = 0; i< calc; i++ ){
            ind = ind + "1";
        }
        if(calc ==0){
            temp_ind = 0;
        }
        else
        {
            temp_ind = address & Integer.parseInt(ind, 2);
        }
        int add_tag = Integer.parseInt(binary_address.substring(0, binary_address.length()- calc),2);
        if(add_tag == Cache_Line[temp_ind].tag[0]){
            Cache_Line[temp_ind].lru = 1;
            isPresent = true;
            return Cache_Line[temp_ind].data[0];
        }
        else if(add_tag == Cache_Line[temp_ind].tag[1]){
            Cache_Line[temp_ind].lru = 0;
            isPresent = true;
            return Cache_Line[temp_ind].data[1];
        }
        else {
            isPresent = false;
            return -1;
        }
    }

    public void cacheWrite(int address, int value){
        String binary_address = Integer.toBinaryString(address);
        String ind ="";
        int temp_ind;
        for(int i =0;i<32-binary_address.length();i++){
            binary_address = "0" + binary_address;
        }
        for(int i = 0; i< calc; i++ ){
            ind = ind + "1";
        }
        if(calc ==0){
            temp_ind = 0;
        }
        else
        {
            temp_ind = address & Integer.parseInt(ind, 2);
        }
        int tag = Integer.parseInt(binary_address.substring(0, binary_address.length()- calc),2);
        Cache_Line[temp_ind].setValue(tag, value);
    }

    @Override
    public void handleEvent(Event e) {
        if(e.getEventType() == Event.EventType.MemoryRead){
            MemoryReadEvent ee = (MemoryReadEvent) e;
            int data = cacheRead(ee.getAddressToReadFrom());
            if(isPresent){
                Simulator.getEventQueue().addEvent(
                        new MemoryResponseEvent(
                                Clock.getCurrentTime() + this.latency,
                                this,
                                ee.getRequestingElement(),
                                data)
                );
            }
            else{
                this.miss_address = ee.getAddressToReadFrom();
                ee.setEventTime(Clock.getCurrentTime()+Configuration.mainMemoryLatency+1);
                Simulator.getEventQueue().addEvent(ee);
                handleCacheMiss(ee.getAddressToReadFrom());
            }
        }
        else if(e.getEventType() == Event.EventType.MemoryResponse){
            MemoryResponseEvent ee = (MemoryResponseEvent) e;
            cacheWrite(this.miss_address, ee.getValue());
        }

        else if(e.getEventType() == Event.EventType.MemoryWrite){
            MemoryWriteEvent ee = (MemoryWriteEvent) e;
            cacheWrite(ee.getAddressToWriteTo(), ee.getValue());
            containingProcessor.getMainMemory().setWord(ee.getAddressToWriteTo(), ee.getValue());
            Simulator.getEventQueue().addEvent(
                    new ExecutionCompleteEvent(
                            Clock.getCurrentTime()+Configuration.mainMemoryLatency,
                            containingProcessor.getMainMemory(),
                            ee.getRequestingElement())
            );
        }
    }
}
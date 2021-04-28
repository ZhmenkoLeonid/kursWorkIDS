package com.IP;

//import com.IP.User;

import com.Netflow.NetflowPacket;
import com.Netflow.Protocol;

import java.util.*;

public class ProtocolList {
   // private static HashMap<Protocol, List<NetflowPacket>> protocolListHashMap;
    //private static HashMap<String,HashMap<Protocol, List<NetflowPacket>>> userListHashMap;
   // private static HashMap<String,HashMap<Protocol, Boolean>> userBusyListHashMap;
    private HashMap<Protocol, List<NetflowPacket>> protocolListHashMap;
    private HashMap<Protocol, Boolean> busyListHashMap;
    public ProtocolList(){
       // userBusyListHashMap = new HashMap<>();
        //userListHashMap = new HashMap<>();
        //busy = false;

        protocolListHashMap = new HashMap<>();
        busyListHashMap = new HashMap<>();
        for (Protocol protocol:Protocol.values()) {
            protocolListHashMap.put(protocol, new LinkedList<>());
            busyListHashMap.put(protocol,false);
        }
    }
/*
    public static void addUser(String ipAddress){
        HashMap<Protocol, List<NetflowPacket>> protocolMap = new HashMap();
        HashMap<Protocol, Boolean> busyMap = new HashMap<>();
        for (Protocol protocol:Protocol.values()) {
            protocolMap.put(protocol, new LinkedList<>());
            busyMap.put(protocol,false);
        }
        waitQueue();
        userListHashMap.put(ipAddress, protocolMap);
        userBusyListHashMap.put(ipAddress,busyMap);
        swapBusy();
    }
public static Set<String> getUserList(){
        return userListHashMap.keySet();
    }*/

    public void addFlow(NetflowPacket netflowPacket){
        Protocol protocol = netflowPacket.getProtocol();
        String ipAddress = netflowPacket.getSrcIpAddress();

        waitQueue(protocol);
        protocolListHashMap.get(protocol).add(netflowPacket);
        //userListHashMap.get(netflowPacket.getSrcIpAddress())
        //        .get(netflowPacket.getProtocol()).add(netflowPacket);
        swapBusy(protocol);
    }

    public List<NetflowPacket> getUserProtocolList(Protocol protocol){
        return protocolListHashMap.get(protocol);
    }
   public List<List<NetflowPacket>>  getUserAllProtocolLists(){
        List<List<NetflowPacket>> allProtocolsFlows = new LinkedList<>();
        for (Protocol protocol: protocolListHashMap.keySet()){
            allProtocolsFlows.add(protocolListHashMap.get(protocol));
        }
        return allProtocolsFlows;
    }
    /*
    public static HashMap<String, HashMap<Protocol, List<NetflowPacket>>> getUserListHashMap() {
        return userListHashMap;
    }
*/
    public List<NetflowPacket>[] getUserAllProtocolListsArray(){
        List<NetflowPacket>[] resultList = new LinkedList[Protocol.values().length];
        int i = 0;
        for (Protocol protocol: Protocol.values()){
            resultList[i] = getUserProtocolList(protocol);
            i++;
        }
        return resultList;
    }
    public void waitQueue(Protocol protocol){
        Boolean busy = busyListHashMap.get(protocol);
        try {
            while (busy) {
                Thread.sleep(300);
            }
            swapBusy(protocol);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
/*
    private static void waitQueue(){
        try {
            while (busy) {
                Thread.sleep(300);
            }
            swapBusy();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private static void swapBusy(){
        busy = !busy;
    }
*/
    public void swapBusy(Protocol protocol) {
        busyListHashMap.replace(protocol,!busyListHashMap.get(protocol));
    }

}

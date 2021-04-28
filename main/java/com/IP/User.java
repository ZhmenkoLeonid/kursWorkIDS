
package com.IP;

import com.Netflow.NetflowPacket;
import com.Netflow.Protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String hostName;
    private String ipAddress;
    public ProtocolList protocolList;
    private static boolean busy = false;

    private static HashMap<String,User> userHashMap = new HashMap<>();

    public User(String hostName,String ipAddress){
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        protocolList = new ProtocolList();
        waitUserListQueue();
        userHashMap.put(ipAddress,this);
        swapUserListBusy();
    }
    public User(String ipAddress){
        this.hostName = "Host"+(userHashMap.size()+1);
        this.ipAddress = ipAddress;
        protocolList = new ProtocolList();
        waitUserListQueue();
        userHashMap.put(ipAddress,this);
        swapUserListBusy();
    }

    public static Boolean isExist(String ipAddress) {
        if (userHashMap.keySet().contains(ipAddress)) return true;
        return false;
    }
    /*
    public static void addFlow(NetflowPacket netflowPacket){
        userHashMap.get(netflowPacket.getSrcIpAddress()).protocolList.addFlow(netflowPacket);
    }
*/
    public void deleteUser(String ipAddress) {
        waitUserListQueue();
        if (userHashMap.remove(ipAddress) == null)
            System.err.println("User with ip address \"" + ipAddress + "\" not found!");
        swapUserListBusy();
    }

    public static User getUserByIpAddress(String ipAddress) {
        User result = userHashMap.get(ipAddress);
        if (result == null) {
            System.err.println("User with ip address \"" + ipAddress + "\" not found!");
            return null;
        }
        return result;
    }

    public static void swapUserListBusy(){
        busy = !busy;
    }
    public static void waitUserListQueue(){
        try {
            while (busy) {
                Thread.sleep(300);
            }
            swapUserListBusy();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    /*
    public static void incPacketsCount(String ipAddress, Protocol protocol) {
        waitQueue();
        User user;
        ListIterator<User> iterator = userList.listIterator();
        while (iterator.hasNext()) {
            user = iterator.next();
            if (user.ipAddress.equals(ipAddress)) {
                switch (protocol){
                    case TCP:
                        user.tcpPackets++;
                        break;
                    case UDP:
                        user.udpPackets++;
                        break;
                    case ICMP:
                        user.icmpPackets++;
                        break;
                    case IGMP:
                        user.igmpPackets++;
                        break;
                }
                swapBusy();
                return;
            }
        }
        System.err.println("User with ip address \""+ipAddress+"\" not found!");
        swapBusy();
    }*/

    public String getHostName() {
        return hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public static HashMap<String, User> getUserHashMap() {
        return userHashMap;
    }

    public static List<User> getUserList() {
        List<User> userList = new LinkedList<>();
        for (String ipAddress:userHashMap.keySet()){
            userList.add(userHashMap.get(ipAddress));
        }
        return userList;
    }
    public List<NetflowPacket> getProtocolList(Protocol protocol){
        return protocolList.getUserProtocolList(protocol);
    }
    public List<List<NetflowPacket>> getAllProtocolLists(){
        return protocolList.getUserAllProtocolLists();
    }

    public static boolean isBusy(){
        return busy;
    }

    @Override
    public String toString() {
        return "{" +
                "hostName=" + hostName +
                ", ipAddress=" + ipAddress +
                '}';
    }
}
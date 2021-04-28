package com.Netflow;

//import com.IP.User;

import com.IP.ProtocolList;
import com.IP.User;

import java.util.*;

public class NetflowPacketDeleteByTimeThread extends Thread {
    private static long lifetimeMillis;
    private long waitTimer;

    public NetflowPacketDeleteByTimeThread(long lifetimeMillis) {
        NetflowPacketDeleteByTimeThread.lifetimeMillis = lifetimeMillis;
        start();
    }

    public NetflowPacketDeleteByTimeThread() {
        // 30 sec - default value
        lifetimeMillis = 30 * 1000;
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                /*
                List<User> userList= new CopyOnWriteArrayList(ProtocolList.getUserListHashMap().values());
                for ( user:userList){
                    deleteUserOldFlows(lifetimeMillis,user);
                }

                 */
                //Iterator<Map.Entry<String,HashMap<Protocol, List<NetflowPacket>>>> it = new HashMap<>(ProtocolList.getUserListHashMap()).entrySet().iterator();
                ListIterator<User> it = User.getUserList().listIterator();
                while (it.hasNext()) {
                    deleteUserOldFlows(lifetimeMillis,it.next());
                }

                waitTimer = getOldestTimestamp()
                        - System.currentTimeMillis() + lifetimeMillis + 1;
                if (waitTimer < 0) {
                    Thread.sleep(2000);
                } else {
                    Thread.sleep(waitTimer);
                }
            }
        } catch ( InterruptedException | ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }
    private static void deleteUserOldFlows(long lifetimeMillis, User user){

        for (List<NetflowPacket> netflowPackets : user.getAllProtocolLists()) {
            if (netflowPackets.size() == 0) {continue;}
            Protocol protocol = netflowPackets.get(0).getProtocol();
            user.protocolList.waitQueue(protocol);
            long currentTime = System.currentTimeMillis();
            NetflowPacket flow;
            ListIterator<NetflowPacket> iterator = user.protocolList.
                    getUserProtocolList(netflowPackets.get(0).protocol).listIterator();
            while (iterator.hasNext()) {
                flow = iterator.next();
                if ((currentTime - flow.getTimestamp()) > lifetimeMillis) {
                    iterator.remove();
                    System.out.println("Removed flow: " + flow.toString());
                }
            }
            user.protocolList.swapBusy(protocol);
        }
    }

    private long getOldestTimestamp(){
        Iterator<Map.Entry<String,User>> it = new HashMap<>(User.getUserHashMap()).entrySet().iterator();
        long minTimestamp[] = new long[User.getUserHashMap().size()];
        int count = 0;
        while (it.hasNext()) {
            minTimestamp[count] = getUserOldestTimestamp(
                    it.next().getValue().protocolList.getUserAllProtocolListsArray());
            count++;
        }
        return count ==0 ? -1: Arrays.stream(minTimestamp).min().getAsLong();
    }

    private long getUserOldestTimestamp(List<NetflowPacket>[] list){
        long minUserTimestamp[] = new long[list.length];
        for (int i = 0;i< list.length;i++){
                minUserTimestamp[i] = list[i].size() > 0 ? list[i].get(0).timestamp : -1;
        }
        return Arrays.stream(minUserTimestamp).min().getAsLong();
    }
}


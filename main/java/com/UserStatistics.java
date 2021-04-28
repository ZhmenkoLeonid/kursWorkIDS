package com;

import com.IP.User;
import com.Netflow.NetflowPacket;
import com.Netflow.Protocol;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserStatistics {
 public static String getUserStats(User user){
     String result = "";
     for (List<NetflowPacket> protocolList:user.protocolList.getUserAllProtocolLists()){
         if (protocolList.size() == 0) {continue;}
         result+="Количество "+protocolList.get(0).getProtocol()+" пакетов: "+ protocolList.size()+"\n";
     }
     return result;
 }
 public static int getUserUniqueProtocolDstPortsCount(User user,Protocol protocol){
     Set<String> portSet = new HashSet<>();
     if (user.protocolList.getUserProtocolList(protocol).size() == 0) return 0;
     for (NetflowPacket flow:user.protocolList.getUserProtocolList(protocol)){
         portSet.add(flow.getDstPort());
     }
     return portSet.size();
 }
}

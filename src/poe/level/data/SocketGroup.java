/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poe.level.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Christos
 */
public class SocketGroup {
    ArrayList<Gem> gems;
    Gem active;
    //settings
    int fromGroupLevel;
    int untilGroupLevel;
    boolean replaceGroup;
    boolean replacesGroup;
    SocketGroup socketGroupReplace;
    SocketGroup socketGroupThatReplaces;
    //int indexOfReplaceGroup;
    transient HashMap<Integer,Integer> linkerToListIndex;
    public int id;
    // IDS FOR JSON CONVERTION
    public int id_replace;
    public int id_replaces;
    public int active_id;
    
    private String note;
    
    public void addNote(String note){
        this.note = note;
    }
    
    public String getNote(){
        return note;
    }
    
    public static int sign(HashSet<Integer> randomIDs){
        int ran;
        do{
            ran = ThreadLocalRandom.current().nextInt(1,999999);
        }while(randomIDs.contains(ran));
        randomIDs.add(ran);
        return ran;
    }
    
    public SocketGroup dupe(HashSet<Integer> s_id, HashSet<Integer> g_id){
        SocketGroup duped = new SocketGroup();
        Gem old_active = this.active;
        duped.id = sign(s_id);
        duped.fromGroupLevel = this.fromGroupLevel;
        duped.untilGroupLevel = this.untilGroupLevel;
        duped.addNote(this.note);
        for(Gem g : gems){
            Gem duped_gem = g.dupeGem();
            duped_gem.level_added = g.level_added;
            duped_gem.id = sign(g_id);
            duped.getGems().add(duped_gem);
            if(g.equals(old_active)){
                duped.active = duped_gem;
                duped.active_id = duped_gem.id;
            }
        }
        return duped;
    }
    
    public SocketGroup(){
        gems = new ArrayList<>();
        active = null;
        //gems.add(GemHolder.getInstance().tossDummie());
        //active.add(GemHolder.getInstance().tossDummie());
        fromGroupLevel = 2;
        untilGroupLevel = 36;
        replaceGroup = false;
        socketGroupReplace = null;
        replacesGroup = false;
        socketGroupThatReplaces = null;
        //indexOfReplaceGroup= -1;
        linkerToListIndex= new HashMap<>();
        id = -1;
        id_replace = -1;
        id_replaces= -1;
        active_id = -1;
    }
    int switche;
    public Gem putGem(Gem gem, int id){
        boolean switcher = false;
        switche = -1;
        Gem g = null;
        if(linkerToListIndex.containsKey(id)){
            if(gems.get(linkerToListIndex.get(id)).equals(active)){
               g = gems.get(linkerToListIndex.get(id));
            }
            gems.set(linkerToListIndex.get(id), gem);
            switche = linkerToListIndex.get(id);
        }else{
            gems.add(gem);
            linkerToListIndex.put(id, gems.size()-1);
        }
        return g;
    }
    //gets called on load from json
    public void linkGem(Gem gem , int id){
        linkerToListIndex.put(id, gems.indexOf(gem));
    }
    
    public int doubleCheck(){

        return switche;
    }
    
    public void removeGem(Gem gem, int id){
        
    }
    
    public Gem getActiveGem(){
        return active;
    }
    
    public void setActiveGem(Gem g){
        active = g;
    }
    
    public ArrayList<Gem> getGems(){
        return gems;
    }
    
    public int getFromGroupLevel(){
        return fromGroupLevel;
    }
    
    public void setFromGroupLevel(int a){
        fromGroupLevel = a;
    }
    
    public int getUntilGroupLevel(){
        return untilGroupLevel;
    }
    
    public void setUntilGroupLevel(int a){
        untilGroupLevel = a;
    }
    
    public boolean replaceGroup(){
        return replaceGroup;
    }
    
    public void setReplaceGroup(boolean a){
        replaceGroup = a;
        if(a==false){
            socketGroupReplace = null;
        }
    }
    
    public SocketGroup getGroupReplaced(){
        return socketGroupReplace;
    }
    
    public void setGroupReplaced(SocketGroup a){
        socketGroupReplace = a;
    }
    
    public boolean replacesGroup(){
        return replacesGroup;
    }
    
    public void setReplacesGroup(boolean a){
        replacesGroup = a;
        if(a==false){
            socketGroupThatReplaces = null;
        }
    }
    
    public SocketGroup getGroupThatReplaces(){
        return socketGroupThatReplaces;
    }
    
    public void setGroupThatReplaces(SocketGroup a){
        socketGroupThatReplaces = a;
    }

    
    
}

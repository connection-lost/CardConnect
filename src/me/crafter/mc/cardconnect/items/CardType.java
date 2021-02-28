package me.crafter.mc.cardconnect.items;

import java.util.HashMap;
import java.util.Map;

public enum CardType {
	SPIDER(1),
	ZOMBIE(2),
	CHICKEN(3),
	FUZHU(4);
	
    private final int id;
    CardType(int id) { this.id = id; }
    public int getValue() { return id; }
    
    private static final Map<Integer, CardType> typeMap = new HashMap<Integer, CardType>();
    static {
        for (CardType type : CardType.values()) {
        	typeMap.put(type.getValue(), type);
        }
    }
    public static CardType valueFrom(int id) { return typeMap.get(id); }   
}

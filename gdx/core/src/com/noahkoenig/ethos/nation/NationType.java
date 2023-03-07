package com.noahkoenig.ethos.nation;

public enum NationType {
    
    NONE("None", "None"),
    GERMANY ("Germany", "German"),
    FRANCE ("France", "French");

    public final String NAME;
    public final String ADJECTIVE;

    NationType(String name, String adjective) {
        this.NAME = name;
        this.ADJECTIVE = adjective;
    }
}
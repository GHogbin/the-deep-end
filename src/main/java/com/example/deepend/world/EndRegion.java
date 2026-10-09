package com.example.deepend.world;

public enum EndRegion {
    DRAGON_ISLAND("dragon_island"), OUTER_END("outer_end"), FRINGE("fringe"), DEEP_END("deep_end"), ABYSS("abyss");

    private final String id;
    EndRegion(String id) { this.id = id; }
    public String id() { return id; }
}


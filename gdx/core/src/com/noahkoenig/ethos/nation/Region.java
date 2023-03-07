package com.noahkoenig.ethos.nation;

import java.util.ArrayList;
import java.util.List;

import com.noahkoenig.ethos.gamemap.grid.Tile;

public class Region {

    private String name;
    private List<Settlement> settlements = new ArrayList<Settlement>();
    private List<Tile> tiles = new ArrayList<Tile>();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Settlement> getSettlements() { return settlements; }

    public List<Tile> getTiles() { return tiles; }
}

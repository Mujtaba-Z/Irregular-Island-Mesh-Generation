package ca.mcmaster.cas.se2aa4.a3.island.terrain;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Desert implements Biome{
    TileColor biomeColour;
    @Override
    public String getColour() {
        return "desert";
    }
}

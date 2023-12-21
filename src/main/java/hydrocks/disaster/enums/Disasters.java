package hydrocks.disaster.enums;

public enum Disasters {
    ANVIL_RAIN("&7Anvil Rain",120), // Self-explanatory (summons raining anvils that de-spawn when they hit the ground or a player)
    CREEPER_SWARM("&aCreeper Infestation", 120), // Self-explanatory (summons a bunch of creepers)
    CYCLONE("&fCyclone", 750); // Summons a tornado that follows the nearest player slowly sucking up anything in Its path, including blocks.

    private final int duration; // Duration of event in seconds.
    private final String disasterName;
    Disasters(String disasterName, int duration) {
        this.duration = duration;
        this.disasterName = disasterName;
    }
    public String getName() {
        return this.disasterName;
    }
    public int getDurationInSeconds() {
        return this.duration;
    }
}

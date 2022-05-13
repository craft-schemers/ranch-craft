
/**
 * Modded Villagers spawn in place of Villagers if Enhanced Economics is enabled. Their trading
 * behavior is enhanced in a manner that is more demanding of players but better-suited for their
 * game stage and item demand. They use machine learning to determine what to sell, and for how
 * much, based on their profession and the player's item consumption statistics.
 */
public class ModdedVillagerEntity extends VillagerEntity {
    public ModdedVillagerEntity(EntityType<? extends VillagerEntity> entityType, World world) {
        super(entityType, world);
    }
}

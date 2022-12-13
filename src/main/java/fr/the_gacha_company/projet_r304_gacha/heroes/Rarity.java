package fr.the_gacha_company.projet_r304_gacha.heroes;

public enum Rarity {

    COMMON("Commun", 1),
    UNCOMMON("Peu commun", 2),
    RARE("Rare", 3),
    EPIC("Epic", 4),
    LEGENDARY("Légendaire", 5);

    public final String name;
    public final int stars;
    public final double lootChance;

    Rarity(String name, int stars) {
        this.name = name;
        this.stars = stars;
        lootChance = 1.0/stars;
    }

}

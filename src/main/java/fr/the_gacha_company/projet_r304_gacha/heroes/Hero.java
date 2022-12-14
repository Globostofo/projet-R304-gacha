package fr.the_gacha_company.projet_r304_gacha.heroes;

import fr.the_gacha_company.projet_r304_gacha.Character;
import fr.the_gacha_company.projet_r304_gacha.Global;
import fr.the_gacha_company.projet_r304_gacha.Stat;
import fr.the_gacha_company.projet_r304_gacha.heroes.races.MagicalRace;
import fr.the_gacha_company.projet_r304_gacha.heroes.races.PhysicalRace;
import fr.the_gacha_company.projet_r304_gacha.heroes.races.Race;
import fr.the_gacha_company.projet_r304_gacha.heroes.roles.MagicalRole;
import fr.the_gacha_company.projet_r304_gacha.heroes.roles.PhysicalRole;
import fr.the_gacha_company.projet_r304_gacha.heroes.roles.Role;

import java.util.HashSet;
import java.util.Set;

public abstract class Hero extends Character {

    public static Set<Hero> heroes = new HashSet<>(){{
        // COMMON
        add(new PhysicalHero("Godric", PhysicalRace.HUMAN, PhysicalRole.SWORDSMAN, Gender.MALE, Rarity.COMMON,
                "le lore", new Stat(45, 4, 0.25, 15)));
        add(new PhysicalHero("Borld", PhysicalRace.ORC, PhysicalRole.WARRIOR, Gender.MALE, Rarity.COMMON,
                "le lore v2 xD", new Stat(35, 6, 0.2, 20)));

        add(new MagicalHero("Snanar", MagicalRace.ELF, MagicalRole.MAGICIAN, Gender.FEMALE, Rarity.COMMON,
                "todo", new Stat(20, 7, 0.24, 15)));

        // UNCOMMON
        add(new PhysicalHero("Thal", PhysicalRace.HUMAN, PhysicalRole.ARCHER, Gender.MALE, Rarity.UNCOMMON,
                "todo", new Stat(20, 12, 0.25, 40)));
    }};
    private static final double total;

    static {
        double temp = 0;
        for (Hero h: heroes) temp += h.rarity.lootChance;
        total = temp;
    }

    public static Hero getRandomHero() {
        double x = Global.rand.nextDouble(total);
        double y = 0;
        for (Hero h: heroes) {
            y += h.rarity.lootChance;
            if (x<y) return h;
        }
        throw new RuntimeException("It's dangerous to go alone, take this.");
    }

    private final String name;
    private final Race race;
    private final Role role;
    private final Gender gender;
    private final Rarity rarity;
    private final String lore;

    private int level = 1;
    private int xp = 0;

    public Hero(String name, Race race, Role role, Gender gender, Rarity rarity, String lore, Stat stat) {
        super(stat);
        this.getStat().boost(race.getBoost());
        this.name = name;
        this.race = race;
        this.role = role;
        this.gender = gender;
        this.rarity = rarity;
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public Race getRace() {
        return race;
    }

    public Role getRole() {
        return role;
    }

    public Gender getGender() {
        return gender;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getLore() {
        return lore;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public void levelUp() {
        ++level;
        xp = 0;
    }

    @Override
    public String minimalShow() {
        return name + " | " + race.getName() + " | " + role.getName() + " | " + gender.name + " | " + rarity.name +
                " | " + level + " | " + getStat().getRoundedHp() + '/' + getStat().getHpMax() + " | " +
                getStat().getAttack() + " | " + (int) (getStat().getDefense()*100) + "% | " + getStat().getSpeed();
    }

    @Override
    public String show() {
        String hp = getStat().getRoundedHp() + "/" + getStat().getHpMax();
        return String.format("""
                =========================
                |%s|
                | Race %16s |
                | Classe %14s |
                | Genre %15s |
                | Rareté %14s |
                | Lore %16s |
                | Level %15d |
                | PV %18s |
                | Attaque %13d |
                | Défense %12d%% |
                | Vitesse %13d |
                =========================""",
                Global.center(name,23), race.getName(), role.getName(), gender.name, rarity.name, lore, level,
                hp, getStat().getAttack(), (int) (getStat().getDefense()*100), getStat().getSpeed());
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", rarity=" + rarity +
                ", gender=" + gender +
                ", lore='" + lore + '\'' +
                '}';
    }

}
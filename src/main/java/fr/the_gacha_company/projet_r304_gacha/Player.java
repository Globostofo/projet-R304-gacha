package fr.the_gacha_company.projet_r304_gacha;

import fr.the_gacha_company.projet_r304_gacha.heroes.Hero;
import fr.the_gacha_company.projet_r304_gacha.threads.notifications.NotificationManager;

/**
 * Holds the methods to interact in the game.
 */
public final class Player {

    public static final int HERO_COST = 200;

    /**
     * Realize a fight between a Hero and a Monster.
     * If hero wins, he gets xp and player wins coins
     * @param hero a Hero
     * @param monster a Monster
     */
    private void fight(Hero hero, Monster monster) {
        if (monster.getStat().getSpeed()>hero.getStat().getSpeed())
            monster.attack(hero);
        while (true) {
            if (hero.getStat().isDead()) {
                System.out.println(hero.getName() + " a perdu... (+0 pièce, +0 exp)");
                return;
            }
            hero.attack(monster);
            if (monster.getStat().isDead()) {
                coins += monster.getCoinsValue();
                hero.gainXp(monster.getXpValue());
                System.out.println(hero.getName() + " a gagné ! (+" + monster.getCoinsValue() + " pièces, +" + monster.getXpValue() + " exp)");
                return;
            }
            monster.attack(hero);
        }
    }
    private final HeroesDeck deck = new HeroesDeck();
    private int coins = 600;

    public int getCoins() {
        return coins;
    }

    public void buyHero() {
        if (coins >= HERO_COST) {
            coins -= HERO_COST;
            Hero h = Hero.getRandomHero();
            deck.add(h);
            System.out.println(h.show());
        } else
            System.out.println("Tu n'as pas assez d'argent (" + coins + " pièces)");
    }

    public void viewHeroes() {
        int i = Integer.parseInt(Global.getInput("""
                        Trier par :
                        1. Nom
                        2. Rareté
                        3. Niveau
                        4. Points de vie
                        5. Attaque
                        6. Défense
                        7. Vitesse
                        
                        Attribut (1-7) :\s"""));
        boolean reversed = Integer.parseInt(Global.getInput("Tri décroissant ? (0-1) : ")) == 1;
        switch (i) {
            case 1 -> {
                deck.sortList(HeroesDeck.BY_NAME, reversed);
            }
            case 2 -> {
                deck.sortList(HeroesDeck.BY_RARITY, reversed);
            }
            case 3 -> {
                deck.sortList(HeroesDeck.BY_LEVEL, reversed);
            }
            case 4 -> {
                deck.sortList(HeroesDeck.BY_HP_MAX, reversed);
            }
            case 5 -> {
                deck.sortList(HeroesDeck.BY_ATTACK, reversed);
            }
            case 6 -> {
                deck.sortList(HeroesDeck.BY_DEFENSE, reversed);
            }
            case 7 -> {
                deck.sortList(HeroesDeck.BY_SPEED, reversed);
            }
        }
        System.out.println(deck.show());
    }

    public void startFight(NotificationManager notificationManager) {
        Monster m = Monster.createMonster();
        System.out.println(m.show() + "\n\n" + deck.show() + '\n');
        Hero h = deck.get(Integer.parseInt(Global.getInput("Choisissez un héros (N) : ")));
        fight(h, m);
        h.startRegenThread(notificationManager);
    }

}

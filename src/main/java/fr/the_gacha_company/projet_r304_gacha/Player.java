package fr.the_gacha_company.projet_r304_gacha;

import fr.the_gacha_company.projet_r304_gacha.heroes.Hero;
import fr.the_gacha_company.projet_r304_gacha.threads.notifications.NotificationManager;

/**
 * Holds the methods to interact in the game.
 */
public final class Player {

    public static final String INSTRUCTIONS = """
            1. Acheter un héros
            2. Mes héros
            3. Combattre
            4. Quitter le jeu""";
    public static final int HERO_COST = 200;
    public static final NotificationManager notificationManager = new NotificationManager();

    /**
     * Realize a fight between a Hero and a Monster.
     * If hero wins, he gets xp and player wins coins
     * @param hero a Hero
     * @param monster a Monster
     */
    private static void fight(Hero hero, Monster monster) {
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
    private static final HeroesDeck deck = new HeroesDeck();
    private static int coins = 600; // TODO : DONT FORGET TO RESET TO 0

    /**
     * Obtain the displayable main menu, with notifications (if any) and the instructions to show user's possibilities
     * @return a String representing the displayable main menu
     */
    public static String showMainMenu() {
        StringBuilder sb = new StringBuilder();
        if (!notificationManager.isEmpty()) sb.append(notificationManager.read()).append("\n\n");
        sb.append("Vous avez ").append(coins).append(" pièces\n").append(INSTRUCTIONS);
        return sb.toString();
    }
 
    /**
     * Execute user choice
     * @param choice an int representing user choice based on INSTRUCTIONS
     * @return an int: -1 if quitting else 0
     * @see #INSTRUCTIONS
     */
    public static int play(int choice) throws MyInputException {
        switch (choice) {
            case 1 -> {
                if (coins >= HERO_COST) {
                    // buy a hero
                    coins -= HERO_COST;
                    Hero h = Hero.getRandomHero();
                    deck.add(h);
                    System.out.println(h.show());
                } else
                    System.out.println("Tu n'as pas assez d'argent (" + coins + " pièces)");
                Global.pressEnter();
            }
            case 2 -> {
                // display player's heroes
                int i = Global.getInput("""
                        Trier par :
                        1. Nom
                        2. Rareté
                        3. Niveau
                        4. Points de vie
                        5. Attaque
                        6. Défense
                        7. Vitesse
                        
                        Attribut (1-7) :\s""");
                boolean reversed = Global.getInput("Tri décroissant ? (0-1) : ") == 1;
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
                    default -> throw new MyInputException();
                }
                System.out.println(deck.show());
                Global.pressEnter();
            }
            case 3 -> {
                Monster m = Monster.createMonster();
                System.out.println(m.show() + "\n\n" + deck.show() + '\n');
                Hero h = deck.get(Global.getInput("Choisissez un héros (N) : "));
                fight(h, m);
                h.startRegenThread(notificationManager);
                Global.pressEnter();
            }
            case 4 -> {
                return -1;
            }
            default -> throw new MyInputException();
        }
        return 0;
    }
}

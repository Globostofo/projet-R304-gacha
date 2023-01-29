package fr.the_gacha_company.projet_r304_gacha;

import fr.the_gacha_company.projet_r304_gacha.threads.notifications.NotificationManager;

public class Game implements Runnable {

    private static final String INSTRUCTIONS = """
            1. Acheter un héros
            2. Mes héros
            3. Combattre
            4. Quitter le jeu""";

    private final Player player = new Player();
    private final NotificationManager notificationManager = new NotificationManager();

    public void run() {
        String userInput;
        do {
            System.out.println(getMainMenu());
            userInput = Global.getInput("Instruction : ");
            if (userInput.equals("1")) player.buyHero();
            else if (userInput.equals("2")) player.viewHeroes();
            else if (userInput.equals("3")) player.startFight();
            Global.pressEnter();
        } while (!userInput.equals("4"));
    }

    private String getMainMenu() {
        StringBuilder sb = new StringBuilder();
        if (!notificationManager.isEmpty()) sb.append(notificationManager.read()).append("\n\n");
        sb.append("Vous avez ").append(player.getCoins()).append(" pièces\n").append(INSTRUCTIONS);
        return sb.toString();
    }

}

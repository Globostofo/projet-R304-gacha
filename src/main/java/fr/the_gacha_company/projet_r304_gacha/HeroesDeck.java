package fr.the_gacha_company.projet_r304_gacha;

import fr.the_gacha_company.projet_r304_gacha.heroes.Hero;

import java.util.*;

public class HeroesDeck extends ArrayList<Hero> implements Showable {

    public static final Comparator<Hero> BY_NAME = (Hero h1, Hero h2) -> h1.getName().compareToIgnoreCase(h2.getName());
    public static final Comparator<Hero> BY_RARITY = Comparator.comparingInt((Hero h) -> h.getRarity().stars);
    public static final Comparator<Hero> BY_LEVEL = Comparator.comparingInt(Hero::getLevel);
    public static final Comparator<Hero> BY_HP_MAX = Comparator.comparingInt((Hero h) -> h.getStat().getHpMax());
    public static final Comparator<Hero> BY_ATTACK = Comparator.comparingDouble((Hero h) -> h.getStat().getAttack());
    public static final Comparator<Hero> BY_DEFENSE = Comparator.comparingDouble((Hero h) -> h.getStat().getDefense());
    public static final Comparator<Hero> BY_SPEED = Comparator.comparingDouble((Hero h) -> h.getStat().getSpeed());

    /**
     * Sorts this object by the selected attribute
     * @param comparator a pre-defined Comparator to choose sorting attribute
     * @param reversed false to do ascending sort, true to do descending sort
     */
    public void sortList(Comparator<Hero> comparator, boolean reversed) {
        // bubble sort implementation
        if (size() < 2) return;
        int end = size();
        while (end >= 1) {
            int i = 0;
            while (i+1 < end) {
                if (comparator.compare(get(i), get(i+1)) * (reversed? -1: 1) > 0)
                    Collections.swap(this, i, i+1);
                ++i;
            }
            --end;
        }
    }

    /**
     * Unlocks a new Hero. Increments its level if already unlocked.
     * @param hero the hero to add to the deck
     * @return true
     */
    @Override
    public boolean add(Hero hero) {
        if (contains(hero)) hero.levelUp();     // if hero is already unlocked
        else super.add(hero);
        return true;
    }

    /**
     * Create the string of minimalShow
     * @return null
     */
    @Override
    public String minimalShow() {
        return null;
    }

    /**
     * Create the string of Show
     * @return a string to display in the terminal
     */
    @Override
    public String show() {
        int[] columnsSize = computeColumnsSize();
        StringBuilder sb = new StringBuilder("N | NOM | RACE | CLASSE | GENRE | RARETE | NIV | EXP | PV | ATQ | DEF | VIT");
        for (int i=0; i<size(); ++i) sb.append('\n').append(i).append(" | ").append(get(i).minimalShow());
        return sb.toString();
    }

    private int[] computeColumnsSize() {
        int[] columnsSize = {String.valueOf(size()-1).length(), 3, 4, 6, 5, 6, 3, 3, 2, 3, 3, 3};
        for (Hero h: this) {
            columnsSize[1] = Math.max(columnsSize[1], h.getName().length());
            columnsSize[2] = Math.max(columnsSize[2], h.getRace().getName().length());
            columnsSize[3] = Math.max(columnsSize[3], h.getRole().getName().length());
            columnsSize[4] = Math.max(columnsSize[4], h.getGender().name.length());
            columnsSize[5] = Math.max(columnsSize[5], h.getRarity().name.length());
            columnsSize[6] = Math.max(columnsSize[6], String.valueOf(h.getLevel()).length());
            columnsSize[7] = Math.max(columnsSize[7], String.valueOf(h.getXp()).length());
            columnsSize[8] = Math.max(columnsSize[8], h.getDisplayHp().length());
            columnsSize[9] = Math.max(columnsSize[9], String.valueOf(h.getStat().getAttack()).length());
            columnsSize[10] = Math.max(columnsSize[10], String.valueOf(h.getStat().getDefense()).length());
            columnsSize[11] = Math.max(columnsSize[11], String.valueOf(h.getStat().getSpeed()).length());
        }
        return columnsSize;
    }

}

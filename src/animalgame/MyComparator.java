package animalgame;

import java.util.Comparator;

public class MyComparator implements Comparator<Player> {


    @Override
    public int compare(Player o1, Player o2) {
        if (o1.getCoins() > o2.getCoins()) {
            return -1;
        }
        return 0;
    }
}
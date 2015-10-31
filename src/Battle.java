/**
 * Created by novoselov on 16.09.2015.
 */
public class Battle {
    // На данный момент игра завязана на два игрока, поле общее
    // В дальнейшем у каждого игрока будет свое поле, даже два одно со своими кораблями, второе - карта обстрела кораблей противника.
    private Player[] players;
    private Field field;
    private static Battle instance = null;

    private Battle() {
        field = Field.getInstance();
    }

    public static Battle getInstance() {
        if (instance == null)
            instance = new Battle();
        return instance;
    }

    // Настраиваем игроков
    public void createPlayers() {
        players = new Player[2];
        for (int i = 0; i < players.length; i++) {
            Player temp = new Player();
            temp.setName();
            players[i] = temp;
        }
    }

    // Настраиваем игровое поле и расставляем кораблики
    public boolean tuneField() {
        field.formFleet();
        return field.setShips();
    }

    // В БОЙ!
    public void startGame() {
        System.out.println("\nИгра началась:");
        Player player = players[0];
        boolean changePlayers = true;
        boolean cheat = false;
        while (field.isNotGameOver()) {
            if (changePlayers) {
                player = players[0];
            } else {
                player = players[1];
            }
            field.showField(cheat);
            cheat = false;
            int shootX = player.getShoot('X');
            if (shootX < 0) {
                cheat = true;
                System.out.println("Чит: ");
                continue;
            }
            int shootY = player.getShoot('Y');
            if (shootY < 0) {
                cheat = true;
                System.out.println("Чит: ");
                continue;
            }
            if (field.doShoot(player, new Point(shootX, shootY))) {
                changePlayers = !changePlayers;
            }
        }
        field.showField(false);
        if (players[0].getDestroyedShipsCount() == players[1].getDestroyedShipsCount()) {
            System.out.println("Игра закончена. Победила дружба, игроки подбили одинаковое количество кораблей (" + player.getDestroyedShipsCount() + ")");
        } else if (players[0].getDestroyedShipsCount() > players[1].getDestroyedShipsCount()) {
            System.out.println("Игра закончена. Победил игрок: " + players[0].getName() + ". Количество уничтоженных кораблей - " + players[0].getDestroyedShipsCount());
        } else {
            System.out.println("Игра закончена. Победил игрок: " + players[1].getName() + ". Количество уничтоженных кораблей - " + players[1].getDestroyedShipsCount());
        }
    }
}

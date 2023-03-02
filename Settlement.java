public class Settlement {

    SettlementType type;
    String name;
    int population;
    int health;

    public void upgrade() {
        if (!type.equals(SettlementType.CITY)) {
            type = type.getNext();
        } else {
            // TODO cannot upgrade a city
        }
    }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
}

public enum NationType {
    
    GERMANY ("Germany", "German"),
    FRANCE ("France", "French");

    String name;
    String adjective;

    NationType(String name, String adjective) {
        this.name = name;
        this.adjective = adjective;
    }

    public String getName() { return name; }

    public String getAdjective() { return adjective; }
}
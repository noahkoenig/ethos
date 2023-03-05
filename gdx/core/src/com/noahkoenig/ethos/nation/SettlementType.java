package com.noahkoenig.ethos.nation;

public enum SettlementType {
    
    /**
     * Hamlets are the smallest type of human settlement and may be little more than a handful of homes or a single extended family.
     * They are typically located in rural or remote areas, and may be focused on subsistence agriculture or other small-scale economic activities.
     */
    HAMLET,

    /**
     * Villages are smaller than towns and often have a more rural or agricultural character.
     * They may be centered around a single industry or resource, such as farming or fishing, and typically have a small, tight-knit community.
     */
    VILLAGE,

    /**
     * Towns are smaller than cities and typically have a more rural or suburban character.
     * They may serve as regional centers for commerce, education, or healthcare, but are generally less populous and less economically influential than cities.
     */
    TOWN,

    /**
     * Cities are typically the largest and most populous type of human settlement.
     * They often serve as centers of culture, commerce, and industry, and may be home to millions of people.
     * Cities may also be administrative centers for a region or country, and often have significant political and economic influence.
     */
    CITY;

    public SettlementType getNext() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}
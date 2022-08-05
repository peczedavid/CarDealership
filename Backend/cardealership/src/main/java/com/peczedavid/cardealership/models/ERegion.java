package com.peczedavid.cardealership.models;

public enum ERegion {
    Germany("Germany"),
    Japan("Japan"),
    America("America");

    private String text;

    ERegion(String text) {
        this.text = text;
    }

    public static ERegion fromString(String name) {
        switch (name.toUpperCase()) {
            case "GERMANY": return ERegion.Germany;
            case "JAPAN": return ERegion.Japan;
            case "AMERICA": return ERegion.America;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return text;
    }

}

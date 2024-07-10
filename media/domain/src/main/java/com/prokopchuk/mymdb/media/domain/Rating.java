package com.prokopchuk.mymdb.media.domain;

public enum Rating {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    Rating(int value) {
        this.value = value;
    }

    private final int value;

    public static Rating getInstance(int value) {
        return switch (value) {
            case 0 -> ZERO;
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            case 5 -> FIVE;
            case 6 -> SIX;
            case 7 -> SEVEN;
            case 8 -> EIGHT;
            case 9 -> NINE;
            case 10 -> TEN;
            default -> throw new IllegalArgumentException();
        };
    }

    public int getValue() {
        return value;
    }
}

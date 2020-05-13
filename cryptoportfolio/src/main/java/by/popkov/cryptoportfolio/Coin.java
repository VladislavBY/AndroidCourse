package by.popkov.cryptoportfolio;

public class Coin {
    private String symbol;
    private Double prise;
    private Double number;

    public Coin(String symbol, Double prise, Double number) {
        this.symbol = symbol;
        this.prise = prise;
        this.number = number;
    }
}

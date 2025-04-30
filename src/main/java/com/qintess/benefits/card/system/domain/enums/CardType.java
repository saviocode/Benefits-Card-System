package com.qintess.benefits.card.system.domain.enums;


import lombok.Getter;

@Getter
public enum CardType {
    ALIMENTACAO("Cartão Alimentação"),
    REFEICAO("Cartão Refeição");

    private String text;

    CardType( String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.getText();
    }


}

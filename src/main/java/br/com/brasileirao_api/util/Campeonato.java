package br.com.brasileirao_api.util;

public enum Campeonato {

    BRASILEIRAO("Brasileirao"),
    COPA_DO_MUNDO("Copa do Mundo"),
    LIBERTADORES("Libertadores"),
    COPA_DO_BRASIL("Copa do Brasil"),
    ESTADUAL("Estadual"),
    AMISTOSO("Amistoso");

    private final String descricao;

    Campeonato(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

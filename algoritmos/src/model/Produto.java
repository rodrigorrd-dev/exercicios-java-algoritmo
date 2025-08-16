package model;

public class Produto {
    private final String codigo;
    private final String nome;
    private final double preco;

    public Produto(String codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return nome + " (" + codigo + ") - R$ " + String.format("%.2f", preco);
    }
}

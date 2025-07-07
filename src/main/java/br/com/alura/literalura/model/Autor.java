package br.com.alura.literalura.model;

public class Autor {
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDataNascimetno() {
        return dataNascimetno;
    }

    public void setDataNascimetno(Integer dataNascimetno) {
        this.dataNascimetno = dataNascimetno;
    }

    public Integer getDataFalecimento() {
        return dataFalecimento;
    }

    public void setDataFalecimento(Integer dataFalecimento) {
        this.dataFalecimento = dataFalecimento;
    }

    private String nome;
    private Integer dataNascimetno;
    private Integer dataFalecimento;

    @Override
    public String toString() {
        return "Autor{" +
                "nome='" + nome + '\'' +
                ", dataNascimetno=" + dataNascimetno +
                ", dataFalecimento=" + dataFalecimento +
                '}';
    }
}

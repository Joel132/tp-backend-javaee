package com.example.bolsa_puntos.extra;

public class UtilizarPuntos {
    private Integer id_cliente;
    private Integer id_concepto_uso;

    public UtilizarPuntos(Integer id_cliente, Integer id_concepto_uso){
        this.id_cliente = id_cliente;
        this.id_concepto_uso = id_concepto_uso;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_concepto_uso(Integer id_concepto_uso) {
        this.id_concepto_uso = id_concepto_uso;
    }

    public Integer getId_concepto_uso() {
        return id_concepto_uso;
    }
}

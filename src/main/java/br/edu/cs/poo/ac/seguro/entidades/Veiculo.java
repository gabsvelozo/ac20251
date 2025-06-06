package br.edu.cs.poo.ac.seguro.entidades;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Veiculo implements Registro{
    private String placa;
    private int ano;
    private Segurado proprietario;
    private CategoriaVeiculo categoria;

	@Override
    public String getIdUnico() {
        return placa;
    }
}

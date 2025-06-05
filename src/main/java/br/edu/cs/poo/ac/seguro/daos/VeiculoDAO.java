package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO extends DAOGenerico<Veiculo>{
    public Class<Veiculo> getClasseEntidade(){
        return Veiculo.class;
    }
}
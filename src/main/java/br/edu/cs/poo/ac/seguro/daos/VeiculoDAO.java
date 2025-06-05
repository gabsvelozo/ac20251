package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class VeiculoDAO extends DAOGenerico<Veiculo>{
    public Class<Veiculo> getClasseEntidade(){
        return Veiculo.class;
    }

    public Veiculo buscarPorPlaca(String placa) {
        for (Veiculo veiculo : buscarTodos()) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                return veiculo;
            }
        }
        return null;
    }

}
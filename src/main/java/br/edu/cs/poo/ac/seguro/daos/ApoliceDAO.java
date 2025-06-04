package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class ApoliceDAO extends DAOGenerico<Apolice> {
    public Class<Apolice> getClasseEntidade(){
        return Apolice.class;
    }
}

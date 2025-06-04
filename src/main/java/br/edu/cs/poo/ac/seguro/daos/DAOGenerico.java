package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Registro;

import java.io.Serializable;

public abstract class DAOGenerico <T extends Registro>{
    private CadastroObjetos cadastro;
    public abstract Class<T> getClasseEntidade();

    public DAOGenerico() {
        cadastro = new CadastroObjetos(getClasseEntidade());
    }

    // CRUD
    public T buscar(String id) {
        return (T)cadastro.buscar(id);
    }

    public boolean incluir(T registro) {
        if (buscar(registro.getIdUnico()) != null) {
            return false;
        } else {
            cadastro.incluir(registro, registro.getIdUnico());
            return true;
        }
    }

    public boolean alterar(T registro) {
        if (buscar(registro.getIdUnico()) == null) {
            return false;
        } else {
            cadastro.alterar(registro, registro.getIdUnico());
            return true;
        }
    }

    public boolean excluir(String id) {
        if (buscar(id) == null) {
            return false;
        } else {
            cadastro.excluir(id);
            return true;
        }
    }

    public Registro[] buscarTodos() {
        Serializable[] serializables = cadastro.buscarTodos();
        if (serializables == null) {
            return new Registro[0]; // Retorna array vazio em vez de null
        }
        Registro[] registros = new Registro[serializables.length];
        for (int i = 0; i < serializables.length; i++) {
            if (serializables[i] instanceof Registro) {
                registros[i] = (Registro) serializables[i];
            }
        }
        return registros;
    }
}
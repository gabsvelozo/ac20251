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

    public void salvar(T registro) {
        if (buscar(registro.getIdUnico()) == null) {
            cadastro.incluir(registro, registro.getIdUnico());
        } else {
            cadastro.alterar(registro, registro.getIdUnico());
        }
    }

    public T[] buscarTodos() {
        Serializable[] serializables = cadastro.buscarTodos();
        if (serializables == null) {
            return (T[]) java.lang.reflect.Array.newInstance(getClasseEntidade(), 0);
        }

        T[] registros = (T[]) java.lang.reflect.Array.newInstance(getClasseEntidade(), serializables.length);
        for (int i = 0; i < serializables.length; i++) {
            if (getClasseEntidade().isInstance(serializables[i])) {
                registros[i] = (T) serializables[i];
            }
        }
        return registros;
    }
}
package br.edu.cs.poo.ac.seguro.entidades;

import java.io.Serializable;

public interface Registro<T> extends Serializable {
    String getIdUnico();
}

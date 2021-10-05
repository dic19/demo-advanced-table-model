/*
 * Copyright (C) 2014 Delcio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.javadeepcafe.domain;

/**
 * @author Delcio Amarillo
 */
public class Procesador {
    
    private String fabricante;
    private String denominacion;
    private Integer numeroNucleos;
    private Double frecuenciaCpu;
    private Double cache;

    public Procesador() {
        super();
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Integer getNumeroNucleos() {
        return numeroNucleos;
    }

    public void setNumeroNucleos(Integer numeroNucleos) {
        this.numeroNucleos = numeroNucleos;
    }

    public Double getFrecuenciaCpu() {
        return frecuenciaCpu;
    }

    public void setFrecuenciaCpu(Double frecuenciaCpu) {
        this.frecuenciaCpu = frecuenciaCpu;
    }

    public Double getCache() {
        return cache;
    }

    public void setCache(Double cache) {
        this.cache = cache;
    }
}

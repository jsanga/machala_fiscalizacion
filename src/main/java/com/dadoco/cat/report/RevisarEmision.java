/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.report;

import com.dadoco.cat.model.ImpuestoPredial;

/**
 *
 * @author dairon
 */
public class RevisarEmision {
    
    private ImpuestoPredial impuestoActual;
    private ImpuestoPredial ImpuestoAnterior;

    public RevisarEmision() {
        impuestoActual = new ImpuestoPredial();
        ImpuestoAnterior = new ImpuestoPredial();
    }

    public ImpuestoPredial getImpuestoActual() {
        return impuestoActual;
    }

    public void setImpuestoActual(ImpuestoPredial impuestoActual) {
        this.impuestoActual = impuestoActual;
    }

    public ImpuestoPredial getImpuestoAnterior() {
        return ImpuestoAnterior;
    }

    public void setImpuestoAnterior(ImpuestoPredial ImpuestoAnterior) {
        this.ImpuestoAnterior = ImpuestoAnterior;
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.service;

import com.dadoco.auth.model.Departamento;
import com.dadoco.auth.model.Permiso;
import com.dadoco.auth.model.Rol;
import com.dadoco.auth.model.RolPermiso;
import com.dadoco.auth.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class SecurityService {
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

 public List<Usuario> listaUsuarios() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Usuario.class));
        return em.createQuery(cq).getResultList();
    }

    public List<Rol> listaRoles() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Rol.class));
        return em.createQuery(cq).getResultList();
    }

    public List<Permiso> listaPermisos() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Permiso.class));
        return em.createQuery(cq).getResultList();
    }

    @Transactional
    public Usuario crearUsuario(Usuario u) {
        em.persist(u);
        return u;
    }

    public Rol crearRol(Rol r) {
        em.persist(r);
        return r;
    }

    public List<Departamento> listaDepartamentos() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Departamento.class));
        return em.createQuery(cq).getResultList();
    }

    @Transactional
    public void crearRol(Rol rol, List<Permiso> permisos) {

        em.persist(rol);
        em.flush();

        for (Permiso p : permisos) {
            RolPermiso pr = new RolPermiso();
            pr.setRol(rol);
            pr.setPermiso(p);

            em.persist(pr);
        }
    }

    @Transactional
    public void editRol(Rol r, List<Permiso> eliminar) {

        for (Permiso p : eliminar) {
            if (!r.getPermisos().contains(p)) {
                Query q = em.createNativeQuery("DELETE FROM auth_rol_permiso WHERE id_rol = " + r.getId() + " AND" + " id_permiso=" + p.getId());
                q.executeUpdate();
            }
        }
        em.merge(r);
        em.flush();

    }
    
     @Transactional
    public void editUsuario(Usuario u, List<Rol> eliminar) {

        for (Rol r : eliminar) {
            if (!u.getRoles().contains(r)) {
                Query q = em.createNativeQuery("DELETE FROM auth_usuario_rol WHERE id_rol = " + r.getId() + " AND" + " id_usuario=" + u.getId());
                q.executeUpdate();
            }
        }
        em.merge(u);
        em.flush();

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.service;

import com.dadoco.auth.model.Permiso;
import com.dadoco.auth.model.Usuario;
import com.dadoco.common.service.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Dairon
 */
@Stateless
public class UsuarioService extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    //Fsan
    public Usuario serchUser(String username)
    {
      Query q1=em.createQuery("Select user from Usuario user where user.username=:usuario", Usuario.class)
              .setParameter("usuario", username);
      return(Usuario) q1.getSingleResult();
    }
    
    public List<Usuario> userByRol(int id)
    {
      Query q1=em.createNativeQuery("SELECT id, nombre, tipo_identificacion, identificacion, tipo, apellidos, \n" +
"       sexo, telefono, email, id_departamento, password, username, habilitado, \n" +
"       salt\n" +
"  FROM public.auth_usuario usu\n" +
"       left JOIN auth_usuario_rol usurol  on usurol.id_usuario=usu.id\n" +
"  where usurol.id_rol="+id+" and usu.habilitado=true \n" +
"  Order By usu.username ASC", Usuario.class);
      return q1.getResultList();
    }
    ///
    public UsuarioService() {
        super(Usuario.class);
    }
    
}

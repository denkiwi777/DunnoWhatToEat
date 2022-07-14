package com.dunnoWhatToEat.snapshot.repository;

import com.dunnoWhatToEat.snapshot.Entity.Ingrediente;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class IngredienteRepositoryCustomImpl implements  IngredienteRepositoryCustom{
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional

    public Ingrediente getByName(String name) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Ingrediente> criteria = criteriaBuilder.createQuery(Ingrediente.class);
        Root<Ingrediente> root = criteria.from(Ingrediente.class);
        CriteriaQuery<Ingrediente> res = criteria.select(root).where(criteriaBuilder.equal(root.get("nome"), name));
        TypedQuery<Ingrediente> query = em.createQuery(res);
        List<Ingrediente> result = query.getResultList();
        if(result.size()==0){
            return null;

        }
        else if(result.size()>1){
            throw new RuntimeException("TROVATO DUPLICATO");
        }
        return result.get(0);


    }
    public Ingrediente getByID(Long id) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Ingrediente> criteria = criteriaBuilder.createQuery(Ingrediente.class);
        Root<Ingrediente> root = criteria.from(Ingrediente.class);
        CriteriaQuery<Ingrediente> res = criteria.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<Ingrediente> query = em.createQuery(res);
        List<Ingrediente> result = query.getResultList();
        if(result.size()==0){
            return null;

        }
        else if(result.size()>1){
            throw new RuntimeException("TROVATO DUPLICATO");
        }
        return result.get(0);


    }
}

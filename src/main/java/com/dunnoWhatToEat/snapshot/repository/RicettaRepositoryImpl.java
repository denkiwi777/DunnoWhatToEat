package com.dunnoWhatToEat.snapshot.repository;

import com.dunnoWhatToEat.snapshot.Entity.*;
import com.dunnoWhatToEat.snapshot.utility.SesionCreator;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class RicettaRepositoryImpl implements RicettaRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private IngredienteRepository ingredienteRepository;
    @Override
    public List getRandomRecipes(int numberOfRecipes) {

        List<RicettaResponse> ricette = new ArrayList<>();
        while (ricette.size() <= numberOfRecipes) {
            double randomNum = (Math.random() / Math.random()) * (Math.floor((Math.random() + 10))) * (Math.random() / Math.random()) * (Math.random() / Math.random()) * (Math.floor((Math.random() + 10))) * (Math.random() / Math.random());
            if (randomNum >= 1 && randomNum <= 28198) {
                Long randomId = Math.round(randomNum);
                RicettaResponse extracted = getRecipeObjectFromId(randomId);
                if (extracted != null) {
                    ricette.add(extracted);
                }
            }
        }
        return ricette;
    }

    private RicettaResponse getRecipeObjectFromId(Long randomId) {

        Ricetta ricetta = em.find(Ricetta.class, randomId);

        if (ricetta != null) {
            RicettaResponse response = new RicettaResponse();
            response.setRicetta(ricetta);
            List<IngredienteRicetta> ingredienti = getIngredientsFromRicetta(ricetta);
            for (IngredienteRicetta ingrRic: ingredienti
                 ) {
                Ingrediente ingredienteDescrp =  em.find(Ingrediente.class, ingrRic.getIngrediente().getId());
                IngredienteResponse ingredienteResp = new IngredienteResponse();
                ingredienteResp.setIngredienteId((ingredienteDescrp.getId()));
                ingredienteResp.setNome(ingredienteDescrp.getNome());
                ingredienteResp.setQuantita(ingrRic.getQuantita());
                ingredienteResp.setUnitaMisura(ingrRic.getUnitaMisura());
                response.addIngrediente(ingredienteResp);

            }
            response.setIngredientePrincipale(ricetta.getIngrediente_princ());

            return response;
        }
        return null;
    }

   private List<IngredienteRicetta> getIngredientsFromRicetta(Ricetta ricetta) {


        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<IngredienteRicetta> criteria = criteriaBuilder.createQuery(IngredienteRicetta.class);
        Root<IngredienteRicetta> root = criteria.from(IngredienteRicetta.class);
        CriteriaQuery<IngredienteRicetta> res = criteria.select(root).where(criteriaBuilder.equal(root.get("ricetta"), ricetta.getRicetta_id()));
        TypedQuery<IngredienteRicetta> query = em.createQuery(res);
        List<IngredienteRicetta> result = query.getResultList();

        return result;


    }

    @Override
    public List search(String[] ingredients) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Ingrediente> criteriaIngrediente = criteriaBuilder.createQuery(Ingrediente.class);
        CriteriaQuery<IngredienteRicetta> criteriaIngredienteRicetta = criteriaBuilder.createQuery(IngredienteRicetta.class);
        Root<Ingrediente> rootIngrediente = criteriaIngrediente.from(Ingrediente.class);
        Root<IngredienteRicetta> rootIngredienteRicetta = criteriaIngredienteRicetta.from(IngredienteRicetta.class);


        CriteriaBuilder.In<String> inClauseIng = criteriaBuilder.in(rootIngrediente.get("nome"));
        CriteriaBuilder.In<Long> inClauseIngRicc = criteriaBuilder.in(rootIngredienteRicetta.get("ingrediente"));

        Iterator<String> iter = Arrays.stream(ingredients).iterator();
        while (iter.hasNext()) {
            String ingredient = iter.next();
            inClauseIng.value(ingredient);
        }

        CriteriaQuery<Ingrediente> resultIngrediente = criteriaIngrediente.select(rootIngrediente).where(inClauseIng);
        TypedQuery<Ingrediente> queryIng = em.createQuery(resultIngrediente);
        Iterator<Ingrediente> ingredientiEstratti = queryIng.getResultList().iterator();
        HashMap ingredientSet = new HashMap();
        while (ingredientiEstratti.hasNext()) {
            Ingrediente ingredient = ingredientiEstratti.next();
            ingredientSet.put(ingredient.getId(), true);
            inClauseIngRicc.value(ingredient.getId());

        }
        CriteriaQuery<IngredienteRicetta> resultIngredienteRicc = criteriaIngredienteRicetta.select(rootIngredienteRicetta).where(inClauseIngRicc);
        TypedQuery<IngredienteRicetta> queryIngRicc = em.createQuery(resultIngredienteRicc);
        Iterator<IngredienteRicetta> ingredientiRiccEstratti = queryIngRicc.getResultList().iterator();


        HashMap<Long, RicettaResponse> result = new HashMap<>();
        while (ingredientiRiccEstratti.hasNext()) {
            IngredienteRicetta ingRicetta = ingredientiRiccEstratti.next();
            Long ricettaId = ingRicetta.getRicetta().getRicetta_id();
            RicettaResponse extracted = getRecipeObjectFromId(ricettaId);
            if (extracted != null) {

                result.put(ricettaId, extracted);
            }
        }

        return Arrays.asList(result.values().toArray());
    }

    @Override
    public List searchLike(String[] ingredients) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<IngredienteRicetta> criteriaIngredienteRicetta = criteriaBuilder.createQuery(IngredienteRicetta.class);

        Root<IngredienteRicetta> rootIngredienteRicetta = criteriaIngredienteRicetta.from(IngredienteRicetta.class);



        CriteriaBuilder.In<Long> inClauseIngRicc = criteriaBuilder.in(rootIngredienteRicetta.get("ingrediente"));
        Iterator<String> iter = Arrays.stream(ingredients).iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("select ingrediente_id from ingredienti where nome_ingrediente like '%");
        while (iter.hasNext()) {
            String ingredient = iter.next();
            sb.append(ingredient);
            sb.append("%'");
            if(iter.hasNext()){
                sb.append(" or nome_ingrediente like '%");
            }
            else{
                sb.append(" ;");
            }
        }
        NativeQuery quer = SesionCreator.getSessionFactory().openSession().createNativeQuery(sb.toString());
        Iterator ingredientiEstratti = quer.list().iterator();
        HashMap ingredientSet = new HashMap();
        while (ingredientiEstratti.hasNext()) {
            BigInteger ingredientID = (BigInteger) ingredientiEstratti.next();

            Ingrediente ingredient = ingredienteRepository.getByID(ingredientID.longValue());

            ingredientSet.put(ingredient.getId(), true);
            inClauseIngRicc.value(ingredient.getId());

        }
        CriteriaQuery<IngredienteRicetta> resultIngredienteRicc = criteriaIngredienteRicetta.select(rootIngredienteRicetta).where(inClauseIngRicc);
        TypedQuery<IngredienteRicetta> queryIngRicc = em.createQuery(resultIngredienteRicc);
        Iterator<IngredienteRicetta> ingredientiRiccEstratti = queryIngRicc.getResultList().iterator();


        HashMap<Long, RicettaResponse> result = new HashMap<>();
        while (ingredientiRiccEstratti.hasNext()) {
            IngredienteRicetta ingRicetta = ingredientiRiccEstratti.next();
            Long ricettaId = ingRicetta.getRicetta().getRicetta_id();
            RicettaResponse extracted = getRecipeObjectFromId(ricettaId);
            if (extracted != null) {
                List<IngredienteResponse> ingredRicetta = extracted.getIngredientiResponse();
                boolean hasAll = true;
                for (IngredienteResponse in: ingredRicetta
                ) {
                    if( ingredientSet.get(in.getIngredienteId())==null){
                        hasAll = false;
                        break;
                    }
                }
                if(hasAll && result.get(ricettaId)==null) {
                    result.put(ricettaId, extracted);
                }
            }
        }

        return Arrays.asList(result.values().toArray());
    }


    @Override
    public List searchFewIngredients(String[] ingredients) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<IngredienteRicetta> criteriaIngredienteRicetta = criteriaBuilder.createQuery(IngredienteRicetta.class);

        Root<IngredienteRicetta> rootIngredienteRicetta = criteriaIngredienteRicetta.from(IngredienteRicetta.class);



        CriteriaBuilder.In<Long> inClauseIngRicc = criteriaBuilder.in(rootIngredienteRicetta.get("ingrediente"));
        Iterator<String> iter = Arrays.stream(ingredients).iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("select ingrediente_id from ingredienti where nome_ingrediente like '%");
        while (iter.hasNext()) {
            String ingredient = iter.next();
            sb.append(ingredient);
            sb.append("%'");
            if(iter.hasNext()){
                sb.append(" or nome_ingrediente like '%");
            }
            else{
                sb.append(" ;");
            }
        }
        NativeQuery quer = SesionCreator.getSessionFactory().openSession().createNativeQuery(sb.toString());
        Iterator ingredientiEstratti = quer.list().iterator();

        HashMap ingredientSet = new HashMap();
        while (ingredientiEstratti.hasNext()) {

                BigInteger ingredientID = (BigInteger) ingredientiEstratti.next();

                Ingrediente ingredient = ingredienteRepository.getByID(ingredientID.longValue());
            ingredientSet.put(ingredient.getNome(), true);
            inClauseIngRicc.value(ingredient.getId());

        }
        CriteriaQuery<IngredienteRicetta> resultIngredienteRicc = criteriaIngredienteRicetta.select(rootIngredienteRicetta).where(inClauseIngRicc);
        TypedQuery<IngredienteRicetta> queryIngRicc = em.createQuery(resultIngredienteRicc);
        Iterator<IngredienteRicetta> ingredientiRiccEstratti = queryIngRicc.getResultList().iterator();


        HashMap<Long, RicettaResponse> result = new HashMap<>();
        while (ingredientiRiccEstratti.hasNext()) {
            IngredienteRicetta ingRicetta = ingredientiRiccEstratti.next();
            Long ricettaId = ingRicetta.getRicetta().getRicetta_id();
            RicettaResponse extracted = getRecipeObjectFromId(ricettaId);
            if (extracted != null) {
                ArrayList<String> idIngRicetta = new ArrayList();
                List<IngredienteResponse> ingredRicetta = extracted.getIngredientiResponse();
                for (IngredienteResponse ingr: ingredRicetta
                     ) {
                    idIngRicetta.add(ingr.getNome());
                }
                boolean hasAll = true;
                Iterator iterKeysIng = ingredientSet.keySet().iterator();

                for (int i=0; i<ingredients.length;i++){
                    String in = ingredients[i];
                    List<String> possibileMatch = idIngRicetta.stream().filter(nomeRicetta -> nomeRicetta.contains(in)).collect(Collectors.toList());
                    if (possibileMatch.size()==0) {
                        hasAll = false;
                        break;
                    }
                }
                if (hasAll && result.get(ricettaId) == null) {
                    result.put(ricettaId, extracted);
                }
            }
        }

        return Arrays.asList(result.values().toArray());
    }
    public List searchCriminal(String[] ingredients) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Ingrediente> criteriaIngrediente = criteriaBuilder.createQuery(Ingrediente.class);
        CriteriaQuery<IngredienteRicetta> criteriaIngredienteRicetta = criteriaBuilder.createQuery(IngredienteRicetta.class);
        Root<Ingrediente> rootIngrediente = criteriaIngrediente.from(Ingrediente.class);
        Root<IngredienteRicetta> rootIngredienteRicetta = criteriaIngredienteRicetta.from(IngredienteRicetta.class);


        CriteriaBuilder.In<String> inClauseIng = criteriaBuilder.in(rootIngrediente.get("nome"));
        CriteriaBuilder.In<Long> inClauseIngRicc = criteriaBuilder.in(rootIngredienteRicetta.get("ingrediente"));

        Iterator<String> iter = Arrays.stream(ingredients).iterator();
        while (iter.hasNext()) {
            String ingredient = iter.next();
            inClauseIng.value(ingredient);
        }

        CriteriaQuery<Ingrediente> resultIngrediente = criteriaIngrediente.select(rootIngrediente).where(inClauseIng);
        TypedQuery<Ingrediente> queryIng = em.createQuery(resultIngrediente);
        Iterator<Ingrediente> ingredientiEstratti = queryIng.getResultList().iterator();
        HashMap ingredientSet = new HashMap();
        while (ingredientiEstratti.hasNext()) {
            Ingrediente ingredient = ingredientiEstratti.next();
            ingredientSet.put(ingredient.getId(), true);
            inClauseIngRicc.value(ingredient.getId());

        }
        CriteriaQuery<IngredienteRicetta> resultIngredienteRicc = criteriaIngredienteRicetta.select(rootIngredienteRicetta).where(inClauseIngRicc);
        TypedQuery<IngredienteRicetta> queryIngRicc = em.createQuery(resultIngredienteRicc);
        Iterator<IngredienteRicetta> ingredientiRiccEstratti = queryIngRicc.getResultList().iterator();


        HashMap<Long, RicettaResponse> result = new HashMap<>();
        while (ingredientiRiccEstratti.hasNext()) {
            IngredienteRicetta ingRicetta = ingredientiRiccEstratti.next();
            Long ricettaId = ingRicetta.getRicetta().getRicetta_id();
            RicettaResponse extracted = getRecipeObjectFromId(ricettaId);
            if (extracted != null) {
                List<IngredienteResponse> ingredRicetta = extracted.getIngredientiResponse();
                boolean hasAll = true;
                for (IngredienteResponse in: ingredRicetta
                ) {
                    if( ingredientSet.get(in.getIngredienteId())==null){
                        hasAll = false;
                        break;
                    }
                }
                if(hasAll && result.get(ricettaId)==null) {
                    result.put(ricettaId, extracted);
                }
            }
        }
        return null;
    }

}

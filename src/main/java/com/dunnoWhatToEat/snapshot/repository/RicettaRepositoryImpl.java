package com.dunnoWhatToEat.snapshot.repository;

import com.dunnoWhatToEat.snapshot.Entity.Ingrediente;
import com.dunnoWhatToEat.snapshot.Entity.Ricetta;
import com.dunnoWhatToEat.snapshot.Entity.RicettaResponse;
import com.dunnoWhatToEat.snapshot.utility.SesionCreator;
import org.hibernate.Criteria;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RicettaRepositoryImpl implements RicettaRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List getRandomRecipes(int numberOfRecipes) {

        List<RicettaResponse> ricette = new ArrayList<>();
        while(ricette.size()<=numberOfRecipes){
            double randomNum =(Math.random() /Math.random())*  (Math.floor((Math.random()+10)))*(Math.random() /Math.random())*(Math.random() /Math.random())*(Math.floor((Math.random()+10)))*(Math.random() /Math.random());
            if(randomNum>=1 && randomNum<=28198) {
                Long randomId = Math.round(randomNum);
                RicettaResponse extracted =  getRecipeObjectFromId( randomId);
                if(extracted!=null) {
                    ricette.add(extracted);
                }            }
        }
        return ricette;
    }

    private RicettaResponse getRecipeObjectFromId( Long randomId) {

        return null;
    }


    @Override
    public List search(String[] ingredients) {
        ArrayList<Object> result = new ArrayList<Object>();
        System.out.println(ingredients);
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
        Iterator ingredienti = quer.list().iterator();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("select ricetta_id from ingredienti_ricette ir where ingrediente_id in(");
        while (ingredienti.hasNext())  {
            int ingredient= (int) ingredienti.next();
            sb2.append(ingredient);
            if(ingredienti.hasNext()){
                sb2.append(",");

            }
            else{
                sb2.append(")");
            }
        }
        NativeQuery quer2 = SesionCreator.getSessionFactory().openSession().createNativeQuery(sb2.toString());

        Iterator recIng = quer2.list().iterator();

        while (recIng.hasNext())  {
            Long ricettaID= new Long((int) recIng.next());
            RicettaResponse extracted = getRecipeObjectFromId(ricettaID);
            if(extracted!=null) {
                result.add(extracted);
            }
        }

        return result;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekzone.feeder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geekzone.model.user.User;
import com.geekzone.model.user.UserRepository;
import com.geekzone.search.tools.Searcher;
import com.geekzone.search.tools.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author michael
 */
@Component
public class Feeder {

    private Searcher elasticSearch;
    private Map<Long, String> userHash;
    @Autowired
    UserRepository userRepo;

    public Feeder() {
        this.elasticSearch = Searcher.getInstance();
        userHash = new HashMap();

       
    }

    public void feed() {
        
//        List<User> users=(List<User>) this.userRepo.findAll();
//        for (User user : users) {
//            this.elasticSearch.index(user, "user");
//        }
        
        
        /**
         * Récupération des empreintes numériques depuis l'index
         */
        this.userHash = this.elasticSearch.getUserHash();
        //this.elasticSearch.close();

        /**
         * Récupération de la liste des users
         */
        List<User> users = (List<User>) userRepo.findAll();

        for (User user : users) {
            String hash = (String) this.userHash.get(user.getUserId());
            if (hash == null) {
                System.out.println("La hash code est nulle ");
                //Element dans la BD et non indexé 
            } else {
                String uHash = "";
                try {
                    uHash = Utils.getHashCode(user);
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(Feeder.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!hash.equals(uHash)) {
                    this.elasticSearch.index(user, "user");
                } else {
                    System.out.println("Rien à indexer");
                }
            }

        }

        Iterator it = this.userHash.entrySet().iterator();
        String value = "";
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry<Integer, String>) it.next();
            value = (String) pair.getValue();
            this.elasticSearch.remove(value);
            it.remove();
        }

    }

}

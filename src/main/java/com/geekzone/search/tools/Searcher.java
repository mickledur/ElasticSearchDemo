/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekzone.search.tools;

import com.geekzone.model.user.User;
import com.geekzone.model.user.UserRepository;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author michael
 */

public class Searcher {

    private TransportClient client;
   
    /**
     * Constructeur privé pour empêcher l'utilisation e l'opérateur new
     */
    private Searcher() {
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", "elasticSearchDemo").build();
            this.client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Searcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public Map getUserHash() {
        Map<Integer, String> hash = new HashMap();
        SearchResponse responses = client.prepareSearch("twitter")
                .setFetchSource(new String[]{"userId", "hashCode"}, null)
                .setScroll(new TimeValue(60000))
                .setSize(100)
                .get();
        do {
            for (SearchHit hit : responses.getHits().getHits()) {
                Map map = hit.getSource();
                //System.out.println("Map To String " + map.toString());
                Integer id = (Integer) map.get("userId");
                String hashCode = (String) map.get("hashCode");
                hash.put(id, hashCode);
            }

            responses = client.prepareSearchScroll(responses.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        } while (responses.getHits().getHits().length != 0);
        System.out.println("Hash To String " + hash.toString());
        return hash;
    }

    public void index(Object obj, String type) {
        try {
            byte[] document = Utils.getDocument(obj);
            IndexResponse response = client.prepareIndex("twitter", type)
                    .setSource(document)
                    .get();
            //System.out.println("Objets indexés: "+response.toString());
        } catch (IOException ex) {
            Logger.getLogger(Searcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        //close();
    }

    public void remove(String value) {
        BulkIndexByScrollResponse response
                = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                        .filter(QueryBuilders.matchQuery("hashCode", value))
                        .source("twitter")
                        .get();
        close();
    }

    public void close() {
        this.client.close();
    }

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static Searcher getInstance() {
        return SearcherHolder.instance;
    }

    /**
     * Holder
     */
    private static class SearcherHolder {

        /**
         * Instance unique non pré-initialisée
         */
        private final static Searcher instance = new Searcher();
    }
}

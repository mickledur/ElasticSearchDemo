/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekzone.search.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static javassist.CtMethod.ConstParameter.string;
import org.apache.commons.codec.digest.DigestUtils;

public class Utils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * @description Cette fonction permet de calculer le hashCode de l'objet
     * (entité de la BD) en utilisant la chaine de caratère au format JSON
     * représentant l'objet.
     * @param obj
     * @return
     * @throws JsonProcessingException 
     */
    public static int getHash(Object obj) throws JsonProcessingException {

        return getDocumentAsString(obj).hashCode();
    }
    /**
     * @description Cette fonction permet de calculer le hashCode de l'objet
     * (entité de la BD) en utilisant la chaine de caratère au format JSON
     * représentant l'objet.
     * @param obj
     * @return
     * @throws JsonProcessingException 
     */
    public static String getHashCode(Object obj) throws JsonProcessingException {
            String hashCode="";
            hashCode=DigestUtils.sha1Hex(getDocumentAsString(obj));
        return hashCode;
    }

    /**
     * @description Cette fonction permet de récupérer une entité (table de la base de donnée)
     *  sous la forme d'une chaine de caractère au format JSON. Elle servira a calculer le
     *  hashCode de l'objet (entitité de la base de donnée) et a créer un champ supplémentaire
     *  pour ajouter le hashCode avant de le convertir en byte[].
     * @param obj
     * @return
     * @throws JsonProcessingException 
     * 
     */
    public static String getDocumentAsString(Object obj) throws JsonProcessingException {

        String document = mapper.writeValueAsString(obj);
        return document;
    }

    /**
     * @description Cette fonction permet d'ajouter le champ "hashCode"
     * au document de l'objet (entité de la BD) avant de l'indexer
     * @param obj
     * @return
     * @throws IOException 
     */
    public static String addHashCode(Object obj) throws IOException {
        JsonNode node = mapper.readTree(getDocumentAsString(obj));
        ObjectNode on = (ObjectNode) node;
        on.put("hashCode", getHashCode(obj));
        String finalObject = node.toString();
        return finalObject;
    }

    /**
     * @description Cette fonction permet de récupérer le document complet
     * de l'objet à indexer sous forme de tableau de byte (type accepté 
     * par elasticSearch pour indexer un document JSON obtenu par conversion
     * d'un objet grâce une librairie commme Jackson)
     * @param obj
     * @return
     * @throws JsonProcessingException
     * @throws IOException 
     */
    public static byte[] getDocument(Object obj) throws JsonProcessingException, IOException {
        byte[] document = addHashCode(obj).getBytes(StandardCharsets.UTF_8);
        return document;
    }

}

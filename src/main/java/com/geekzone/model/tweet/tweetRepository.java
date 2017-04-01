/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekzone.model.tweet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author michael
 */

@Repository
public interface tweetRepository extends CrudRepository<Tweet, Long> {

    @Override
    public Tweet findOne(Long id);

    @Override
    public Tweet save(Tweet s);
    
    public Tweet findByTweetTitle(String str);
    
    
    
}

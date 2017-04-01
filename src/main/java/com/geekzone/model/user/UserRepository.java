/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekzone.model.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author michael
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    public User findOne(Long id);
    @Override
    public User save(User user);
    
    public User findByUserFirstName(String str);
    
}

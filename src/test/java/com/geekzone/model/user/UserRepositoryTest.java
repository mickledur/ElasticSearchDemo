/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekzone.model.user;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author michael
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository ur;

    public UserRepositoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of findOne method, of class UserRepository.
     */
    @Test
    public void testFindOne() {
        User u = new User("Albert", "Renaud");
        this.em.persist(u);
        User getU=ur.findByUserFirstName("Albert");
        User Finded=ur.findOne(getU.getUserId());
        assertThat(getU.getUserLastName()).isEqualTo(Finded.getUserLastName());
    }

    /**
     * Test of save method, of class UserRepository.
     */
    @Test
    public void testSave() {
        Long number = this.ur.count();
        User u = new User("Albert", "Renaud");
        this.em.persist(u);
        assertThat(number + 1).isEqualTo(this.ur.count());
    }

    /**
     * Test of findByUserFirstName method, of class UserRepository.
     */
    @Test
    public void testFindByUserFirstName() {
        User u = new User("Albert", "Renaud");
        //this.em.persist(u);
       this.ur.save(u);
        User getU=ur.findByUserFirstName("Albert");
        String name=getU.getUserFirstName();
        assertThat("Albert").isEqualTo(name);
    }
}

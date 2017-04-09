package com.geekzone;

import com.geekzone.feeder.Feeder;
import com.geekzone.model.user.User;
import com.geekzone.model.user.UserRepository;
import com.geekzone.search.tools.Searcher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticSearchDemoApplication implements CommandLineRunner {

    @Autowired
    Feeder feeder;

//    @Autowired
//    UserRepository uRepo;

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchDemoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        //feeder=new Feeder();
        feeder.feed();
        //List <User> users = (List<User>) repo.findAll();
//        System.out.println("Bien");
//        Searcher elasticSearch = Searcher.getInstance();
//        List<User> users = (List<User>) uRepo.findAll();
//        System.out.println("Hello");
//        for (User u : users) {
//            elasticSearch.index(u, "user");
//        }
//        elasticSearch.close();
//        Map<Integer, String> hash = new HashMap();
//        hash=s.getUserHash();
//       String code=hash.get((2));
//////        Feeder feeder=new Feeder();
//        System.out.println("code: "+code);
////        List<User> users = (List<User>) repo.findAll();
////        for (User u : users) {
////            feeder.feed(u);
////        }
////            repo.save(new User("Gilbert","DANTE"));
////            repo.save(new User("Shanon","YORK"));
////            repo.save(new User("Robert","MILKOWYSCHI"));

    }
}

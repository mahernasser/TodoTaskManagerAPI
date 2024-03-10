package com.maher.repositories;

import com.maher.enitites.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<AppUser, String> {



    AppUser findByName(String name);
    AppUser findAppUserByEmail(String email);


}

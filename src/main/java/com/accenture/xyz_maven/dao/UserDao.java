package com.accenture.xyz_maven.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.accenture.xyz_maven.entity.UserEntity;


@Repository
public interface UserDao extends MongoRepository<UserEntity,String>
{
    //searches the database for a user with the given username and returns that document
    UserEntity findFirstByUsername(String username);
}
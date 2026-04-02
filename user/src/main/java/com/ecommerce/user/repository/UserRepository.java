package com.ecommerce.user.repository;

import com.ecommerce.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
}

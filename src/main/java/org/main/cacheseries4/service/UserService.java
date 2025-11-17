package org.main.cacheseries4.service;

import org.main.cacheseries4.entity.User;
import org.main.cacheseries4.repo.UserRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Cacheable(value="users", key="#email")
    public User findByEmail(String email) {
        System.out.println("*************FindByEmail Method Executed***************");
        return userRepo.findByEmail(email);
    }

    @CacheEvict(value="users", key="#oldEmail")
    public void updateEmail(String oldEmail, String newEmail) {
        System.out.println("*************UpdateEmail Method Executed**************");
        User  user = userRepo.findByEmail(oldEmail);
        if(user != null)
        {
            user.setEmail(newEmail);
            userRepo.save(user);
        }
    }
}

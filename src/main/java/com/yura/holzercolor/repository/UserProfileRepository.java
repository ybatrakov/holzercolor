package com.yura.holzercolor.repository;

import com.yura.holzercolor.model.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {
}

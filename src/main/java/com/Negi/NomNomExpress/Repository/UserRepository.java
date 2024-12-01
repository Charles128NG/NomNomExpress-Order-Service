package com.Negi.NomNomExpress.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Negi.NomNomExpress.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{

	//find if user exists
		public Optional<UserEntity> findByUserName(String userName);

}

package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.controller.model.PetStoreData;
import pet.store.entity.PetStore;
import pet.store.controller.model.PetStoreData;

public interface PetStoreDao extends JpaRepository<PetStore, Long> {
	
}

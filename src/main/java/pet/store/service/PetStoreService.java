package pet.store.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;
	
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		PetStore newPetStore= findOrCreatePetStore(petStoreData.getPetStoreId());
		copyPetStoreFields(newPetStore, petStoreData);
		return new PetStoreData(petStoreDao.save(newPetStore));
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if(petStoreId == null) {
			return new PetStore(); 
		}
		return findPetStoreId(petStoreId);
	}
	private PetStore findPetStoreId(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow (() -> new NoSuchElementException (
						"Pet Store with ID=" + petStoreId + " does not exist."));
	}
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}
}

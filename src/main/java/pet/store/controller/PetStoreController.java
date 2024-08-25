package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomerData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	
	@Autowired
	PetStoreService petStoreService;
	
	@PostMapping("")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating Pet Store", petStoreData);
		
		return petStoreService.savePetStore(petStoreData);
		
	}
	
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee insertPetStoreEmployee(@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Adding Pet Store Employee to store ID " + petStoreId +" with parameters{}", petStoreEmployee);
		
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer insertPetStoreCustomer(@PathVariable Long petStoreId, @RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Adding Pet Store Customer to store ID " + petStoreId +" with parameters {}", petStoreCustomer);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating Pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@GetMapping("{petStoreId}")
	public PetStoreData getPetStore(@PathVariable Long petStoreId) {
		log.info("Retrieveing Pet Store ID " + petStoreId);
		return petStoreService.retrievePetStore(petStoreId);
	}
	
	@GetMapping("")
	public List<PetStoreData> getPetStores() {
		log.info("Retrieving all Pet Stores");
		return petStoreService.retrieveAllPetStores();
	}
	
	@DeleteMapping("{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
		log.info("Deleting Pet Store with ID " + petStoreId);
		return petStoreService.deletePetStoreById(petStoreId);
	}
	
	
}

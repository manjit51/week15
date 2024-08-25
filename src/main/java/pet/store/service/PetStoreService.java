package pet.store.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomerData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CustomerDao customerDao;
	
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
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore newPetStore = findPetStoreId(petStoreId);
		Employee newEmployee = findOrCreateEmployee(petStoreId, petStoreEmployee.getEmployeeId());
		copyEmployeeFields(newEmployee, petStoreEmployee);
		newEmployee.setPetStore(newPetStore);
		newPetStore.getEmployees().add(newEmployee);
		Employee saveEmployee = employeeDao.save(newEmployee);
		return new PetStoreEmployee(saveEmployee);
	}
	
	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee emp = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException (
						"Employee with ID=" + employeeId + " does not exist."));
		if(emp.getPetStore().getPetStoreId().equals(petStoreId)) {
			return emp;
		} else {
			throw new IllegalArgumentException("Employee/PetStore ID Mismatch");
		}
	}
	
	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		if(employeeId == null) {
			return new Employee();
		}
		return findEmployeeById(petStoreId, employeeId);
	}
	
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		
	}
	
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore newPetStore = findPetStoreId(petStoreId);
		Customer newCustomer = findOrCreateCustomer(petStoreId, petStoreCustomer.getCustomerId());
		copyCustomerFields(newCustomer, petStoreCustomer);
		newCustomer.getPetStores().add(newPetStore);
		newPetStore.getCustomers().add(newCustomer);
		customerDao.save(newCustomer);
		Customer saveCustomer = customerDao.save(newCustomer);
		return new PetStoreCustomer(saveCustomer);
	}
	
	private Customer findCustomerById(Long petStoreId, Long customerId) {
		Customer cus = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException (
						"Customer with ID = " + customerId + " does not exist."));
		Object check[] = cus.getPetStores().toArray();
		boolean checkFlag = false;
		for(int i = 0; i < check.length; i++) {
			if(check[i].equals(petStoreId)) {
				checkFlag = true;
			}
		}
		
		if(checkFlag) {
			return cus;
		} else {
			throw new IllegalArgumentException("Customer/PetStore Id Mismatch");
		}
	}
	
	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		if(customerId == null) {
			return new Customer();
		} else {
			return findCustomerById(petStoreId, customerId);
		}
	}
	
	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}
	
	@Transactional(readOnly = false)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> allPetStores = petStoreDao.findAll();
		
		List<PetStoreData> result = new LinkedList<>();
		for(PetStore petStore : allPetStores) {
			PetStoreData psd = new PetStoreData(petStore);
			
			psd.getCustomers().clear();
			psd.getEmployees().clear();
			
			result.add(psd);
		}
		return result;
		
	}
	
	@Transactional(readOnly = false)
	public PetStoreData retrievePetStore(Long petStoreId) {
		PetStore petStore = findPetStoreId(petStoreId);
		return new PetStoreData(petStore);
	}

	public Map<String, String> deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreId(petStoreId);
		petStoreDao.delete(petStore);
		Map<String, String> response = new HashMap<>();
		response.put("Message:", "Deletion Succesfull");
		return response;
	}
}

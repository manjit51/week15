package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreData {
	
	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private Long petStoreZip;
	private Long petStorePhone;
	private Set<PetStoreEmployee> employees = new HashSet<>();
	private Set<PetStoreCustomer> customers = new HashSet<PetStoreCustomer>();
	
	
	public PetStoreData(PetStore petStore) {
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreAddress();
		petStoreCity = petStore.getPetStoreCity();
		petStoreState = petStore.getPetStoreState();
		petStoreZip = petStore.getPetStoreZip();
		petStorePhone = petStore.getPetStorePhone();
		
		for(Employee employees1 : petStore.getEmployees()) {
			employees.add(new PetStoreEmployee(employees1));
		}
		
		for(Customer customer1 : petStore.getCustomers()) {
			customers.add(new PetStoreCustomer(customer1));
		}
	}
	
	@Data
	@NoArgsConstructor
	static class PetStoreEmployee {
		private Long employeeId;
		private String employeeJobTitle;
		private String employeeFirstName;
		private String employeeLastName;
		private Long employeePhone;
		
		PetStoreEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeJobTitle = employee.getEmployeeJobTitle();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
		}
	}
	
	@Data
	@NoArgsConstructor
	static class PetStoreCustomer {
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		PetStoreCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
	}

}

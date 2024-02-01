package fa.mock.generator;


import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fa.mock.repository.UserRepository;

@Component
public class UserGenerator implements IdentifierGenerator{
	
	@Autowired
	UserRepository userRepository;
	public static final String prefix = "US";

	@Override
	public String generate(SharedSessionContractImplementor session, Object object) {
		
		    String maxId = userRepository.getMaxId();
		
		if (maxId != null) {
		        String numericPart = maxId.substring(prefix.length()); 
		        int numericValue = Integer.parseInt(numericPart); 
		        int incrementedValue = numericValue + 1; 
		        String newNumericPart = String.format("%04d", incrementedValue); 
		        String generatedId = prefix + newNumericPart; 
		        return generatedId;
		    }else {
		    	 String generatedId = "US0001"; 
			     return generatedId;
			}

	}
	

}

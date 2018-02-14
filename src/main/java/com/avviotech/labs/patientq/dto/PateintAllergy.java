package com.avviotech.labs.patientq.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PateintAllergy {

	@Id
	private Long id;
	
	private Long pateintid;
	
	private String allergies;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public Long getPateintid() {
		return pateintid;
	}

	public void setPateintid(Long pateintid) {
		this.pateintid = pateintid;
	}
	
	
}

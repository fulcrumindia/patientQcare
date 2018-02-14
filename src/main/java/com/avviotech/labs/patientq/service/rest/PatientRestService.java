package com.avviotech.labs.patientq.service.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avviotech.labs.patientq.dto.Pateint;
import com.avviotech.labs.patientq.dto.Provider;
import com.avviotech.labs.patientq.dto.QueueData;
import com.avviotech.labs.patientq.model.PateintProviderModel;
import com.avviotech.labs.patientq.repository.PateintsRepository;
import com.avviotech.labs.patientq.repository.PatientPerHourRepository;
import com.avviotech.labs.patientq.repository.ProviderRepository;
import com.avviotech.labs.patientq.repository.QueueDataRepository;
import com.avviotech.labs.patientq.repository.QueueRepository;
import com.avviotech.labs.patientq.repository.SymtompsRepository;
import com.avviotech.labs.patientq.repository.TreatmentRepository;
import com.avviotech.labs.patientq.repository.WaitTimeRepository;
import com.avviotech.labs.patientq.service.CounterService;

@RestController
public class PatientRestService {

	@Autowired
	private QueueDataRepository queueDataRepository;

	@Autowired
	private PatientPerHourRepository patientperhourRepository;

	@Autowired
	private WaitTimeRepository waittimeRepository;

	@Autowired
	private MongoOperations mongoOps;

	@Autowired
	private SymtompsRepository symtompsDataRepository;

	@Autowired
	private PateintsRepository patientsRepository;

	@Autowired
	private ProviderRepository providersRepository;

	@Autowired
	private QueueRepository queueRepository;

	@Autowired
	private TreatmentRepository treatmentRepository;

	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/patient-records")
    public PateintProviderModel getRecords(@RequestParam(value="email") String email) {
     
		Pateint p1 = patientsRepository.findByemail(email);
		ArrayList<PateintProviderModel> pps = new ArrayList<PateintProviderModel>();
		if(p1 != null)
		{
			Collection<QueueData> newqueuedatas = queueDataRepository
					.findBypateintid(p1.getId());

			

			if (newqueuedatas != null) {
				for (QueueData q : newqueuedatas) {
					PateintProviderModel pp = new PateintProviderModel();
					pp.setWaittime(q.getAssignedwaittime());
					pp.setSymptoms(q.getSymptom());
					pp.setProvidernotes(q.getRemarks());
					pp.setTreatment(q.getTreatment());
					pp.setReference(q.getUniquereference());
					if(q.getCheckouttime() != null)
						pp.setCheckouttime(q.getCheckouttime().toString());
					pp.setLastdatevisit(new Date().toString());
					if (q.getPateintid() != null && q.getPateintid() > 0) {
						Pateint p = patientsRepository.findByid(q.getPateintid()
								.longValue());
						pp.setId(p.getId().toString());
						if (p != null) {
							pp.setFirstname(p.getFirstname());
							pp.setLastname(p.getLastname());
							pp.setAddress(p.getAddress());
							pp.setCity(p.getCity());
							pp.setZip(p.getZipcode());
							pp.setEmail(p.getEmail());
							pp.setZip(p.getZipcode());
							pp.setAllergies(p.getAllergies());
							pp.setPcp(p.getPcp());
							pp.setEmergencycontact(p.getEmeregencycontact());
							pp.setEmergencyname(p.getEmeregencycontactnumber());
							
						}
					}

					if (q.getProviderid() != null && q.getProviderid() > 0) {
						Provider pr = providersRepository.findByid(q
								.getProviderid().longValue());
						if (pr != null) {
							pp.setProvidename(pr.getFirstname() + " "
									+ pr.getLastname());
							pp.setProvidercontact(pr.getMobile());
							pp.setProvideremail(pr.getEmail());
						}
					}

					pps.add(pp);
					break;
				}
			}
			
		}
			
		if(pps.size() > 0)
			return pps.get(0);
		
		return null;
    }
}

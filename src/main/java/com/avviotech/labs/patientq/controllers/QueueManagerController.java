package com.avviotech.labs.patientq.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avviotech.labs.patientq.dto.CurrentWaitTime;
import com.avviotech.labs.patientq.dto.Pateint;
import com.avviotech.labs.patientq.dto.PatientPerHour;
import com.avviotech.labs.patientq.dto.Provider;
import com.avviotech.labs.patientq.dto.Queue;
import com.avviotech.labs.patientq.dto.QueueData;
import com.avviotech.labs.patientq.dto.Symtoms;
import com.avviotech.labs.patientq.dto.Treatments;
import com.avviotech.labs.patientq.model.PateintProviderModel;
import com.avviotech.labs.patientq.model.QueueFormData;
import com.avviotech.labs.patientq.repository.PateintsRepository;
import com.avviotech.labs.patientq.repository.PatientPerHourRepository;
import com.avviotech.labs.patientq.repository.ProviderRepository;
import com.avviotech.labs.patientq.repository.QueueDataRepository;
import com.avviotech.labs.patientq.repository.QueueRepository;
import com.avviotech.labs.patientq.repository.SymtompsRepository;
import com.avviotech.labs.patientq.repository.TreatmentRepository;
import com.avviotech.labs.patientq.repository.WaitTimeRepository;
import com.avviotech.labs.patientq.service.CounterService;

@Controller
public class QueueManagerController {

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
	
	private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

	@RequestMapping("/queuemanager")
	public String home(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName", "queuemanager");
		return "layout";
	}

	@RequestMapping("/updatePateintPerHour")
	public String updatePateintPerHour(
			@Valid @ModelAttribute("patientperhour") PatientPerHour patientperhour,
			BindingResult result, ModelMap model) {
		patientperhour.setCurrentdate(format.format(new Date()));
		patientperhour.setId(counterService.getNext());
		patientperhourRepository.save(patientperhour);
		return "redirect:/queuemanager";
	}

	@RequestMapping("/updateWaitTime")
	public String updateWaitTime(
			@Valid @ModelAttribute("waittime") CurrentWaitTime waittime,
			BindingResult result, ModelMap model) {
		waittime.setCurrentdate(format.format(new Date()));
		waittime.setId(counterService.getNext());
		waittimeRepository.save(waittime);
		return "redirect:/queuemanager";
	}

	@RequestMapping("/addQueueData")
	public String addQueueData(
			@Valid @ModelAttribute("queuedata") QueueData queuedata,
			BindingResult result, ModelMap model,
			@RequestParam("pateintname") String pateintname,
			@RequestParam("mobile") String mobile) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
			return "error";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		if (!pateintname.isEmpty()) {
			Pateint p = new Pateint();
			p.setId(counterService.getNext());
			p.setFirstname(pateintname);
			p.setMobile(mobile);
			patientsRepository.save(p);

			if (queuedata.getId() == null)
				queuedata.setId(counterService.getNext());

			queuedata.setPatient(p);
			queuedata.setUniquereference(String.valueOf(new Date().getTime()));
			queuedata.setPateintid(p.getId());
			queuedata.setPateintname(pateintname);
			queuedata.setQueueid(getQueueId("Normal"));
			queuedata.setVisittime(new Date());
			queuedata.setEntertime(new Date());
			queuedata.setStatus("CheckedIn");
			queuedata.setStatuscolor("#ed552f");
			queueDataRepository.save(queuedata);
		}

		addModelData(model);
		return "redirect:/queuemanager";
	}

	@RequestMapping("/updateNQueueData")
	public String updateNQueueData(
			@Valid @ModelAttribute("qform") QueueFormData qform,
			BindingResult result, ModelMap model, @RequestParam("id") String id) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
			return "error";
		}
		if (!id.isEmpty()) {
			if (qform != null && qform.getNqueuelist() != null) {
				for (QueueData nq : qform.getNqueuelist()) {
					if (nq.getId().longValue() == Long.valueOf(id).longValue()) {
						if (nq.getStatus().equalsIgnoreCase("CheckedIn")) {
							nq.setEntertime(new Date());
							nq.setQueueid(getQueueId("Normal"));
							nq.setStatuscolor("#ed552f");
						} else if (nq.getStatus().equalsIgnoreCase("Treating")) {
							nq.setQueueid(getQueueId("Treatment"));
							nq.setStatuscolor("#3c6778");
						} else if (nq.getStatus().equalsIgnoreCase("Completed")) {
							nq.setQueueid(getQueueId("Completed"));
							nq.setCheckouttime(new Date());
						} else if (nq.getStatus().equalsIgnoreCase("Cancelled")) {
							nq.setQueueid(getQueueId("Cancelled"));
							nq.setCheckouttime(new Date());
						} else if (nq.getStatus().equalsIgnoreCase(
								"Rescheduled")) {
							nq.setQueueid(getQueueId("Cancelled"));
							nq.setCheckouttime(new Date());
						} else if (nq.getStatus().equalsIgnoreCase("Referred")) {
							nq.setQueueid(getQueueId("Cancelled"));
							nq.setCheckouttime(new Date());
						}

						if (nq.getProviderid() > 0) {
							nq.setProvider(providersRepository.findByid(nq
									.getProviderid().longValue()));
							nq.setEntertime(new Date());
							nq.setQueueid(getQueueId("Treatment"));
							nq.setStatuscolor("#3c6778");
							nq.setStatus("Treating");
						}

						if (nq.getTraige().equalsIgnoreCase("LevelII"))
							nq.setTraigecolor("red");
						else if (nq.getTraige().equalsIgnoreCase("LevelIII"))
							nq.setTraigecolor("yellow");
						else if (nq.getTraige().equalsIgnoreCase("LevelIV"))
							nq.setTraigecolor("green");
						else if (nq.getTraige().equalsIgnoreCase("LevelV"))
							nq.setTraigecolor("white");
						queueDataRepository.save(nq);
						break;
					}
				}
			}
		}
		model.addAttribute("qform", qform);
		return "redirect:/queuemanager";
	}

	@RequestMapping("/updateTQueueData")
	public String updateTQueueData(
			@Valid @ModelAttribute("qform") QueueFormData qform,
			BindingResult result, ModelMap model, @RequestParam("id") String id)
			throws Exception {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
			return "error";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		if (!id.isEmpty()) {
			if (qform != null && qform.getTqueuelist() != null) {
				for (QueueData nq : qform.getTqueuelist()) {
					if (nq.getId() == Long.valueOf(id).longValue()) {
						double totalwaittime = new Date().getTime()
								- nq.getVisittime().getTime();
						Double diffMinutes = totalwaittime / (60 * 1000);

						nq.setWaittime(new Integer(diffMinutes.intValue())
								.toString());

						if (nq.getStatus().equalsIgnoreCase("CheckedIn")) {
							nq.setEntertime(new Date());
							nq.setQueueid(getQueueId("Normal"));
							nq.setStatuscolor("#ed552f");
						} else if (nq.getStatus().equalsIgnoreCase("Treating")) {
							nq.setQueueid(getQueueId("Treatment"));
							nq.setStatuscolor("#3c6778");
						} else if (nq.getStatus().equalsIgnoreCase("Completed")) {
							nq.setQueueid(getQueueId("Completed"));
							nq.setCheckouttime(new Date());
						} else if (nq.getStatus().equalsIgnoreCase("Cancelled")) {
							nq.setQueueid(getQueueId("Cancelled"));
							nq.setCheckouttime(new Date());
						} else if (nq.getStatus().equalsIgnoreCase(
								"Rescheduled")) {
							nq.setQueueid(getQueueId("Cancelled"));
							nq.setCheckouttime(new Date());
						} else if (nq.getStatus().equalsIgnoreCase("Referred")) {
							nq.setQueueid(getQueueId("Cancelled"));
							nq.setCheckouttime(new Date());
						}

						if (nq.getProviderid() > 0)
							nq.setProvider(providersRepository.findByid(nq
									.getProviderid().longValue()));

						if (nq.getPateintid() > 0)
							nq.setPatient(patientsRepository.findByid(nq
									.getPateintid().longValue()));

						queueDataRepository.save(nq);
						break;
					}
				}
			}
		}
		model.addAttribute("qform", qform);
		return "redirect:/queuemanager";
	}

	public void addModelData(ModelMap model) {

		

		Collection<Treatments> ts = treatmentRepository.findAll();
		Collection<Symtoms> sps = symtompsDataRepository.findAll();
		Collection<Pateint> patients = patientsRepository.findAll();
		Collection<Provider> providers = providersRepository.findAll();
		Collection<QueueData> newqueuedatas = queueDataRepository
				.findByqueueid(getQueueId("Normal"));
		Collection<QueueData> treatingqueuedatas = queueDataRepository
				.findByqueueid(getQueueId("Treatment"));
		Collection<QueueData> completedqueuedatas = queueDataRepository
				.findByqueueid(getQueueId("Completed"));
		QueueFormData qform = new QueueFormData();

		qform.setNqueuelist(newqueuedatas);
		qform.setTqueuelist(treatingqueuedatas);
		qform.setCqueuelist(completedqueuedatas);

		model.addAttribute("ts", ts);
		model.addAttribute("sps", sps);
		model.addAttribute("patients", patients);
		model.addAttribute("providers", providers);
		model.addAttribute("qform", qform);

		PatientPerHour patientperhour = patientperhourRepository
				.findAllToday(format.format(new Date()));

		CurrentWaitTime waittime = null;
		
		List<CurrentWaitTime> waittimeliste = waittimeRepository.findAllDataToday(format
				.format(new Date()));
		
		if(waittimeRepository.findAllDataToday(format
				.format(new Date()))  != null && waittimeRepository.findAllDataToday(format
				.format(new Date())).size() > 0)
		{
			
			
			waittime = waittimeliste.get(waittimeliste.size() - 1);
			
		}
		
		
		model.addAttribute("patientperhour", patientperhour);
		model.addAttribute("waittime", waittime);
		model.addAttribute("waittimes", waittimeliste);

		addPatientProviderData(model);

	}

	public Long getQueueId(String type) {
		Queue q = queueRepository.findBytype(type);
		return q.getId();
	}

	public void addPatientProviderData(ModelMap model) {
		Collection<QueueData> newqueuedatas = queueDataRepository
				.findAllToday(new Date());

		ArrayList<PateintProviderModel> pps = new ArrayList<PateintProviderModel>();

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
			}
		}

		model.addAttribute("pps", pps);
	}

}

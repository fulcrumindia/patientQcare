package com.avviotech.labs.patientq.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.Queue;
import com.avviotech.labs.patientq.dto.QueueData;
import com.avviotech.labs.patientq.model.UrgentVisitModel;
import com.avviotech.labs.patientq.repository.QueueDataRepository;
import com.avviotech.labs.patientq.repository.QueueRepository;

@Controller
public class ERDashboardController {

	@Autowired
	private QueueDataRepository queueDataRepository;

	@Autowired
	private QueueRepository queueRepository;

	@RequestMapping("/erdashboard")
	public String index(ModelMap model) {
		addYTDData(model);
		addPatientRegistry(model);
		model.addAttribute("pageName", "erdashboard");
		return "layout";
	}

	public void addYTDData(ModelMap model) {
		Date firstday = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(firstday);
		cal.add(Calendar.DAY_OF_MONTH, -360);
		Date lastday = cal.getTime();

		Collection<QueueData> alldata = queueDataRepository.findLast30Days(
				firstday, lastday);

		int lwot = 0;
		int ytdp = 0;
		int waittime = 0;
		int newcustomer = 0;
		int newcustomerm = 0;
		if (alldata != null) {
			for (QueueData q : alldata) {
				if (q != null && q.getStatus() != null) {
					if (q.getStatus().equalsIgnoreCase("Cancelled")
							|| q.getStatus().equalsIgnoreCase("Rescheduled"))
						lwot = lwot + 1;

					int w = 0;
					if (q.getWaittime() != null && !q.getWaittime().isEmpty())
						w = Integer.parseInt(q.getWaittime());

					ytdp = ytdp + 1;
					waittime = waittime + w;

					newcustomer = newcustomer + 1;
				}
			}
		}

		waittime = waittime / 12;
		newcustomerm = newcustomer % 12;

		model.addAttribute("lwot", lwot);
		model.addAttribute("ytdp", ytdp);
		model.addAttribute("waittime", waittime);
		model.addAttribute("newcustomer", newcustomer);
		model.addAttribute("newcustomerm", newcustomerm);

	}

	public void addPatientRegistry(ModelMap model) {
		ArrayList<UrgentVisitModel> urgentdayslist = new ArrayList<UrgentVisitModel>();
		HashMap<String, UrgentVisitModel> map = new HashMap<String, UrgentVisitModel>();
		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -360);
		Date today30 = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MMM");
		Collection<QueueData> lasturgents = queueDataRepository.findLast30Days(
				today, today30);
		for (QueueData q : lasturgents) {
			if (q.getStatus() == null || q.getStatus().isEmpty())
				continue;

			if (q.getEntertime() == null)
				continue;

			
			String dd = formatter.format(q.getEntertime());

			if (map.get(dd) == null) {
				UrgentVisitModel m = new UrgentVisitModel();
				m.setDate(dd);

				if (q.getStatus().equalsIgnoreCase("Completed")) {
					m.setCount(1);
				} else if (q.getStatus().equalsIgnoreCase("Cancelled")) {
					m.setErcount(1);
				} else if (q.getStatus().equalsIgnoreCase("Reschedule")) {
					m.setNonercount(1);
				}

				map.put(dd, m);
			} else {
				UrgentVisitModel m = map.get(dd);

				if (q.getStatus().equalsIgnoreCase("Completed")) {
					m.setCount(m.getCount() + 1);
				} else if (q.getStatus().equalsIgnoreCase("Cancelled")) {
					m.setErcount(m.getErcount() + 1);
				} else if (q.getStatus().equalsIgnoreCase("Reschedule")) {
					m.setNonercount(m.getNonercount() + 1);
				}

				map.put(dd, m);
			}

			
		}
		for (Map.Entry<String, UrgentVisitModel> entry : map.entrySet()) {
			urgentdayslist.add(entry.getValue());
		}
		model.addAttribute("urgentdayslist", urgentdayslist);
	}

	public Long getQueueId(String type) {
		Queue q = queueRepository.findBytype(type);
		return q.getId();
	}

}

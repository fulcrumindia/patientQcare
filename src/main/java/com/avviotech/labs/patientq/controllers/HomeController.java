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
public class HomeController {
	
	@Autowired 
	private QueueDataRepository queueDataRepository;
	
	@Autowired 
	private QueueRepository queueRepository;
	
	@RequestMapping("/home")
	public String home(ModelMap model) {
		model.addAttribute("pageName","home");
		return "home";
	}
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		addLast30DaysMap(model);
		addLast30DaysVisitUrgent(model);
		addTodayVisit(model);
		model.addAttribute("pageName","index");
		return "layout";
	}
	
	
	public void addLast30DaysMap(ModelMap model)
	{
		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -30);
		Date today30 = cal.getTime();
		
		ArrayList<UrgentVisitModel> urgentdayslist = new ArrayList<UrgentVisitModel>();
		HashMap<String, UrgentVisitModel> map = new HashMap<String, UrgentVisitModel>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd");
		Collection<QueueData> lasturgents = queueDataRepository.findLast30Days(today, today30);
		for(QueueData q : lasturgents)
		{
			if(q.getStatus().equalsIgnoreCase("Cancelled") || q.getStatus().equalsIgnoreCase("Rescheduled"))
				continue;
			
			int waittime = 0;
			
			if(q.getWaittime() != null && !q.getWaittime().isEmpty())
				waittime = Integer.parseInt(q.getWaittime());
			
			String dd = formatter.format(q.getEntertime());
			if(map.get(dd) == null)
			{
				UrgentVisitModel m = new UrgentVisitModel();
				m.setDate(dd);
				
				if(q.getTraige().equalsIgnoreCase("LevelIII")){
					m.setWaittime(waittime);
				}
				else if(q.getTraige().equalsIgnoreCase("LevelII")){
					m.setErwaittime(waittime);
				}
				else if(q.getTraige().equalsIgnoreCase("LevelIV") || q.getTraige().equalsIgnoreCase("LevelV")){
					m.setNonerwaittime(waittime);
				}
				
				map.put(dd, m);
			}
			else
			{
				UrgentVisitModel m = map.get(dd);
				
				if(q.getTraige().equalsIgnoreCase("LevelIII")){
					m.setWaittime((m.getWaittime() + waittime)/2);
				}
				else if(q.getTraige().equalsIgnoreCase("LevelII")){
					m.setErwaittime((m.getErwaittime() + waittime)/2);
				}
				else if(q.getTraige().equalsIgnoreCase("LevelIV") || q.getTraige().equalsIgnoreCase("LevelV")){
					m.setNonerwaittime((m.getNonerwaittime() + waittime)/2);
				}
				
				map.put(dd, m);
			}
			
		}
		for (Map.Entry<String, UrgentVisitModel> entry : map.entrySet()) {
			urgentdayslist.add(entry.getValue());
		}
		model.addAttribute("urgentdayslist",urgentdayslist);
	}
	public void addTodayVisit(ModelMap model)
	{
		Date today = new Date();
		Collection<QueueData> todayDatas = queueDataRepository.findTodayByTraige(today,"LevelIII");
		Collection<QueueData> todayLWOT = queueDataRepository.findTodayByQueueType(today,getQueueId("Cancelled"));
		model.addAttribute("todayDatas",todayDatas);
		model.addAttribute("todayLWOT",todayLWOT);
	}
	public void addLast30DaysVisitUrgent(ModelMap model)
	{
		ArrayList<UrgentVisitModel> urgentdayslist = new ArrayList<UrgentVisitModel>();
		HashMap<String, UrgentVisitModel> map = new HashMap<String, UrgentVisitModel>();
		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -30);
		Date today30 = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("dd");
		Collection<QueueData> lasturgents = queueDataRepository.findLast30Days(today, today30);
		for(QueueData q : lasturgents)
		{
			if(q.getEntertime() == null)
				continue ; 
			String dd = formatter.format(q.getEntertime());
			if(map.get(dd) == null)
			{
				UrgentVisitModel m = new UrgentVisitModel();
				m.setDate(dd);
				
				if(q.getTraige().equalsIgnoreCase("LevelIII")){
					m.setCount(1);
				}
				else if(q.getTraige().equalsIgnoreCase("LevelII")){
					m.setErcount(1);
				}
				else if(q.getTraige().equalsIgnoreCase("LevelIV") || q.getTraige().equalsIgnoreCase("LevelV")){
					m.setNonercount(1);
				}
				
				map.put(dd, m);
			}
			else
			{
				UrgentVisitModel m = map.get(dd);
				
				if(q.getTraige().equalsIgnoreCase("LevelIII")){
					m.setCount(m.getCount() + 1);
				}
				else if(q.getTraige().equalsIgnoreCase("LevelII")){
					m.setErcount(m.getErcount() + 1);
				}
				else if(q.getTraige().equalsIgnoreCase("LevelIV") || q.getTraige().equalsIgnoreCase("LevelV")){
					m.setNonercount(m.getNonercount() + 1);
				}
				
				map.put(dd, m);
			}
			
			
		}
		for (Map.Entry<String, UrgentVisitModel> entry : map.entrySet()) {
			urgentdayslist.add(entry.getValue());
		}
		model.addAttribute("urgentdaysvisits",urgentdayslist);
	}
	
	public Long getQueueId(String type)
	{
		Queue q = queueRepository.findBytype(type);
		return q.getId();
	}
	
}

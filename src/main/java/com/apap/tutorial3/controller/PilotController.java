package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "licenseNumber", required = true) String licenseNumber,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		
		model.addAttribute("pilotList", archive);
		return "viewall-pilot";
	}
	
	@RequestMapping({"/pilot/view/license-number", "/pilot/view/license-number/{lnum}"})
	public String viewByLnum(@PathVariable Optional<String> lnum, Model model) {
		
		if (lnum.isPresent()) {
			PilotModel pilotnya = pilotService.getPilotDetailByLicenseNumber(lnum.get());
			
			if (pilotnya == null) {
				model.addAttribute("message", "Pilot dengan nomor "+ lnum.get() +" tidak ditemukan");
				return "error";
			}
			
			model.addAttribute("pilot", pilotnya);
			return "view-pilot";
		}
		
		else {
			model.addAttribute("message", "Harap masukkan license numbernya");
			return "error";
		}
	}
	
	@RequestMapping({"/pilot/update/license-number/fly-hour/{fhour}", "/pilot/update/license-number/{lnum}/fly-hour/{fhour}"})
	public String setFlyHourByLnum(@PathVariable Optional<String> lnum, @PathVariable Optional<Integer> fhour, Model model) {
		
		if (lnum.isPresent()) {
			PilotModel pilotnya = pilotService.setPilotFlyHour(lnum.get(), fhour.get());
			
			if (pilotnya == null) {
				model.addAttribute("message", "Pilot dengan nomor "+ lnum.get() +" tidak ditemukan");
				return "error";
			}
			
			model.addAttribute("pilot", pilotnya);
			return "update-pilot";
		}
		
		else {
			model.addAttribute("message", "Harap masukkan license numbernya");
			return "error";
		}
	}
	
	@RequestMapping({"/pilot/delete/id", "/pilot/delete/id/{id}"})
	public String deleteById(@PathVariable Optional<String> id, Model model) {
		
		if(id.isPresent()) {
			PilotModel pilotnya = pilotService.getPilotDetailById(id.get());
			
			if (pilotnya == null) {
				model.addAttribute("message", "Pilot dengan id "+ id.get() +" tidak ditemukan");
				return "error";
			}
			
			pilotService.deletePilot(pilotnya);
			model.addAttribute("id", id.get());
			return "delete";
		}
		
		else {
			model.addAttribute("message", "Harap masukkan idnya");
			return "error";
		}
	}

}

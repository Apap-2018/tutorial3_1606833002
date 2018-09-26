package com.apap.tutorial3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tutorial3.model.PilotModel;

@Service
public class PilotInMemoryService implements PilotService{
	private List<PilotModel> archivePilot;
	
	public PilotInMemoryService() {
		archivePilot = new ArrayList<>();
	}

	@Override
	public void addPilot(PilotModel pilot) {
		archivePilot.add(pilot);
	}

	@Override
	public List<PilotModel> getPilotList() {
		return archivePilot;
	}

	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		PilotModel targetPilot = null;
		
		for(int i = 0; i < archivePilot.size(); i++) {
			if(archivePilot.get(i).getLicenseNumber().equals(licenseNumber)) {
				targetPilot = archivePilot.get(i);
			}
		}
		return targetPilot;	
	}
	
	@Override
	public PilotModel setPilotFlyHour(String licenseNumber, Integer flyHour) {
		PilotModel targetPilot = getPilotDetailByLicenseNumber(licenseNumber);
		
		if (targetPilot != null)
			targetPilot.setFlyHour(flyHour);
		
		return targetPilot;
	}

	@Override
	public PilotModel getPilotDetailById(String id) {
		PilotModel targetPilot = null;
		
		for(int i = 0; i < archivePilot.size(); i++) {
			if(archivePilot.get(i).getId().equals(id)) {
				targetPilot = archivePilot.get(i);
			}
		}
		return targetPilot;	
	}

	@Override
	public void deletePilot(PilotModel pilot) {
		if (pilot != null)
			archivePilot.remove(pilot);
		
	}

}

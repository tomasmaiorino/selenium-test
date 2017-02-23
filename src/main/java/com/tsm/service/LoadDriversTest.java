package com.tsm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.ChromeTestDriver;
import com.tsm.config.FireFoxTestDriver;

import lombok.Getter;
import lombok.Setter;

@Service
public class LoadDriversTest {

	@Getter @Setter
	public List<BaseTestDriver> drives = new ArrayList<>();

	@Autowired
	@Getter @Setter
	public FireFoxTestDriver fireFoxTestDriver;
	
	@Autowired
	@Getter @Setter
	public ChromeTestDriver chromeTestDriver; 
	
	public BaseTestDriver getTestDriverByName(final String name) {
		 Assert.notNull(name, "Name must not be null!");
		 if (drives.isEmpty()) {
			drives.add(fireFoxTestDriver);
			drives.add(chromeTestDriver);
		 }
		 return drives.stream().filter(d -> d.getDriverName().equals(name)).findFirst().get();
	}
	
	public List<BaseTestDriver> getTestDriversByName(List<String>names) {
		List<BaseTestDriver> list = new ArrayList<>();
		for (String n : names) {
			list.add(getTestDriverByName(n));
		}
		return list;
	}
}

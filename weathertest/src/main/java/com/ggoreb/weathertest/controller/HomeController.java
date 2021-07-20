package com.ggoreb.weathertest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggoreb.weathertest.model.AreaBasedList;
import com.ggoreb.weathertest.model.AreaTemp;
import com.ggoreb.weathertest.model.Covid;
import com.ggoreb.weathertest.model.Weather;
import com.ggoreb.weathertest.repository.AirConditionRepository;
import com.ggoreb.weathertest.repository.AreaBasedListRepository;
import com.ggoreb.weathertest.repository.AreaTempRepository;
import com.ggoreb.weathertest.repository.CovidRepository;
import com.ggoreb.weathertest.repository.WeatherRepository;

@Controller
public class HomeController {
	@Autowired
	CovidRepository covidRepository;
	@Autowired
	WeatherRepository weatherRepository;
	@Autowired
	AirConditionRepository airConditionRepository;
	@Autowired
	AreaBasedListRepository areaRepository;
	@Autowired
	AreaTempRepository areaTempRepository;

	@GetMapping("/recommend")
	public void recommend() {
		List<AreaBasedList> areaList = new ArrayList<AreaBasedList>();

		Covid covid = new Covid();
		List<String> stringTemp = new ArrayList<String>();
		stringTemp.add("경북");
		stringTemp.add("경남");
		stringTemp.add("대구");
		stringTemp.add("부산");
		stringTemp.add("울산");
		Map<Integer, Covid> covidMap = new HashMap<Integer, Covid>();
		for (String a : stringTemp) {
			covid = covidRepository.findByArea(a);
			if (covid.getIncdec().compareTo("50") < 0) {
				if (covid.getArea().equals("경북")) {
					covidMap.put(35, covid);
				} else if (covid.getArea().equals("경남")) {
					covidMap.put(36, covid);
				} else if (covid.getArea().equals("대구")) {
					covidMap.put(4, covid);
				} else if (covid.getArea().equals("부산")) {
					covidMap.put(6, covid);
				} else if (covid.getArea().equals("울산")) {
					covidMap.put(7, covid);
				}
			}
		}

		System.out.println(covidMap);
		List<Weather> weatherTemp = weatherRepository.findAll();
		List<Weather> weatherResult = new ArrayList<Weather>();
		for (Weather w : weatherTemp) {
			if (w.getPopCategoryValue().compareTo("40") < 0) {
				weatherResult.add(w);
			}
		}

		List<AreaBasedList> areaTemp = new ArrayList<AreaBasedList>();

		for (Weather w : weatherResult) {
			for (Integer key : covidMap.keySet()) {
				if (w.getAreacode() == key) {
					areaTemp = areaRepository.findAllByAreacode(key);
				}
			}
			areaList.addAll(areaTemp);
		}

		AreaTemp at = new AreaTemp();
		for (AreaBasedList abl : areaList) {
			at.setTitle(abl.getTitle());
			at.setAddr1(abl.getAddr1());
			at.setAreacode(abl.getAreacode());
			areaTempRepository.save(at);
			at = new AreaTemp();
		}
	}

	@GetMapping("/chu")
	public String index(Model model, @RequestParam(defaultValue = "1") int page, Pageable pageable) {
		List<Covid> covidList = covidRepository.findAll();
		
		pageable = PageRequest.of(page-1, 10);

		Page<AreaTemp> pageList = areaTempRepository.findAll(pageable);

		int startPage = (page - 1) / 10 * 10 + 1;
		int endPage = startPage + 9;
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("page", page);

		model.addAttribute("covidList", covidList);
		model.addAttribute("areaList", pageList);

		return "weather";
	}

}

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

import com.ggoreb.weathertest.model.Air;
import com.ggoreb.weathertest.model.AreaBasedList;
import com.ggoreb.weathertest.model.AreaTemp;
import com.ggoreb.weathertest.model.Covid;
import com.ggoreb.weathertest.model.Weather;
import com.ggoreb.weathertest.repository.AirRepository;
import com.ggoreb.weathertest.repository.AreaBasedListRepository;
import com.ggoreb.weathertest.repository.AreaTempRepository;
import com.ggoreb.weathertest.repository.CovidRepository;
import com.ggoreb.weathertest.repository.WeatherRepository;

@Controller
public class HomeController {
	@Autowired
	CovidRepository covidRepository;

	@Autowired
	AreaTempRepository areaTempRepository;

	@Autowired
	AirRepository airRepository;

	@Autowired
	AreaBasedListRepository areaBasedList; // 지역관광정보

	@Autowired
	WeatherRepository weatherRepository;

	@GetMapping("/chu")
	public String index(Model model, @RequestParam(defaultValue = "1") int page, Pageable pageable) {
		List<Covid> covidList = covidRepository.findAll();

		pageable = PageRequest.of(page - 1, 10);

		Page<AreaTemp> pageList = areaTempRepository.findAll(pageable);

		List<Air> airList = airRepository.findAll();
		List<Weather> weatherList = weatherRepository.findAll();

		int startPage = (page - 1) / 10 * 10 + 1;
		int endPage = startPage + 9;
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("page", page);

		model.addAttribute("covidList", covidList);
		model.addAttribute("areaList", pageList);
		model.addAttribute("airList", airList);
		model.addAttribute("weatherList", weatherList);

		return "weather";
	}

	@GetMapping("/areaList")
	public String areaList(@RequestParam("areacode") Integer areacode, Model model) {
		List<AreaBasedList> list = areaBasedList.findAllByAreacode(areacode);

		model.addAttribute("list", list);
		return "areaList";
	}

}

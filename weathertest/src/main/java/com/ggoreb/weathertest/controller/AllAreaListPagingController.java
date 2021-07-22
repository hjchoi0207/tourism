package com.ggoreb.weathertest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggoreb.weathertest.model.AreaBasedList;
import com.ggoreb.weathertest.repository.AreaBasedListRepository;

@Controller
public class AllAreaListPagingController {
	@Autowired
	AreaBasedListRepository areaBasedList; // 지역관광정보
	
	
	@GetMapping("/allAreaList")
	public String allAreaList(Model model, @RequestParam(defaultValue = "1") int page, Pageable pageable) {
		
		pageable = PageRequest.of(page - 1, 9);
		Page<AreaBasedList> pageList = areaBasedList.findAll(pageable);
		
		int startPage = (page - 1) / 10 * 10 + 1;
		int endPage = startPage + 9;
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("page", page);
		model.addAttribute("areaList", pageList);
		
		return "allAreaList";
	}
}

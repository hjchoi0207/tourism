package com.ggoreb.weathertest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ggoreb.weathertest.repository.AirConditionRepository;

@RestController
public class AirConditionController {
	@Autowired
	AirConditionRepository airConditionRepository;
	
	@GetMapping("/aircondition")
	public Map<String, Object> airCondition() throws IOException {
		JsonParser jsonParser = JsonParserFactory.getJsonParser();

		// 요청 URL
		URL url = null;
		try {
			url = new URL(
					"http://apis.data.go.kr/1480523/MetalMeasuringResultService/MetalService?"
					+"numOfRows=1&pageNo=1&resultType=json&stationcode=6&date=20210719&timecode=RH24&itemcode=90303&serviceKey=2Ty16FHtqKEzO%2BqOMfaqyU6BjHFRao4HW4JyAjbvZMjbIucyPrl2CX%2FKNWBsO6WMLVHsse8zTQwdew1%2BESQsfA%3D%3D");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// url.openStream()으로 요청 결과를 stream으로 받음.
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

		// JsonParser를 사용하기 위해 stream을 하나의 String 객체로 묶음.
		String line = br.readLine();
		StringBuilder json = new StringBuilder();
		while (line != null && !line.equals("")) {
			json.append(line);

			line = br.readLine();
		}
		
		Map<String, Object> metalService = (Map<String, Object>) jsonParser.parseMap(json.toString()).get("MetalService");
		List<Map<String, Object>> item = (List<Map<String, Object>>) metalService.get("item");
		Map<String, Object> result = item.get(0);
		
		
		return result;
	}
}

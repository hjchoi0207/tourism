package com.ggoreb.weathertest.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ggoreb.weathertest.model.Weather;
import com.ggoreb.weathertest.repository.WeatherRepository;

@RestController
public class WeatherController {
	@Autowired
	WeatherRepository weatherRepository;
	
	@GetMapping("/weather")
	public List<Map<String, Object>> weather() {
		Weather weather = new Weather();
		RestTemplate rt = new RestTemplate();
		RequestEntity requestEntity = null;
		try {
			requestEntity = RequestEntity
					.get(new URI("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?"
							+ "serviceKey=2Ty16FHtqKEzO%2BqOMfaqyU6BjHFRao4HW4JyAjbvZMjbIucyPrl2CX%2FKNWBsO6WMLVHsse8zTQwdew1%2BESQsfA%3D%3D"
							+ "&dataType=json" + "&numOfRows=10" + "&pageNo=1" + "&base_date=20210720"
							+ "&base_time=2000" + "&nx=91" + "&ny=77"))
					.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		ResponseEntity<Map> entity = rt.exchange(requestEntity, Map.class);
		Map<String, Object> result = entity.getBody();
		Map<String, Object> response = (Map<String, Object>) result.get("response");
		Map<String, Object> body = (Map<String, Object>) response.get("body");
		Map<String, Object> items = (Map<String, Object>) body.get("items");
		List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");
		
		for(Map<String, Object> map : item) {
			System.out.println((String)map.get("category"));
			if(((String)map.get("category")).equals("POP")) {
				weather.setPopCategory((String) map.get("category"));
				weather.setPopCategoryValue((String) map.get("fcstValue"));
			
			}else if(((String)map.get("category")).equals("SKY")) {
				weather.setSkyCategory((String) map.get("category"));
				weather.setSkyCategoryValue((String) map.get("fcstValue"));
			}
			weather.setNx((Integer) map.get("nx"));
			weather.setNy((Integer) map.get("ny"));
		}
		weather.setAreacode(4);
		weatherRepository.save(weather);
		return item;
	}
}

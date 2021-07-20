package com.ggoreb.weathertest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ggoreb.weathertest.model.AreaBasedList;
import com.ggoreb.weathertest.model.Weather;
import com.ggoreb.weathertest.repository.AreaBasedListRepository;
import com.ggoreb.weathertest.repository.WeatherRepository;

@RestController 
//@RequestMapping("api")
public class ApiTest {

	@Autowired
	AreaBasedListRepository areaBasedListRepository;
	@Autowired
	WeatherRepository weatherRepository;

	private final String TourKey = "MUMreSgE%2BSS7myJQ4TQvAZ4v3GKdHjY4w58566xQLA%2Fvt702075b8IUFHfFyv96B3l6j%2FyksrgR%2FY2yAeUIruA%3D%3D";
	private final String TourUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService";

	// **** [ AreaBasedList ] AreaCode로 관광지정보 조회 *****************
	// addr1, areaCode, x,y, tel, title, firstimage
	@GetMapping("/api3")
	public List<Map<String, Object>> allowBasic3() throws Exception {
		RestTemplate rt = new RestTemplate();
		RequestEntity requestEntity = null;
		try {
			requestEntity = RequestEntity.get(new URI(TourUrl + "/areaBasedList" + "?serviceKey=" + TourKey
					+ "&numOfRows=100" + "&pageNo=1" + "&MobileOS=ETC" + "&MobileApp=AppTest" + "&arrange=A"
					+ "&contentTypeId=15" + "&areaCode=36"// 4,6,7,35,36
					+ "&listYN=Y" + "&_type=json")).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		ResponseEntity<Map> entity = rt.exchange(requestEntity, Map.class);
		Map<String, Object> result = entity.getBody();
		Map<String, Object> response = (Map<String, Object>) result.get("response");
		Map<String, Object> body = (Map<String, Object>) response.get("body");
		Map<String, Object> items = (Map<String, Object>) body.get("items");
		List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");

		
		Weather w = new Weather();
		
		// 조회수 추가하기
		AreaBasedList areaBasedList = new AreaBasedList();
		for (Map<String, Object> map : item) {
			areaBasedList.setAreacode((Integer) map.get("areacode"));
			areaBasedList.setAddr1(String.valueOf(map.get("addr1")));
			areaBasedList.setTitle(String.valueOf(map.get("title")));
			areaBasedList.setMapx(String.valueOf(map.get("mapx")));
			areaBasedList.setMapy(String.valueOf(map.get("mapy")));
			areaBasedList.setFirstimage(String.valueOf(map.get("firstimage")));
			areaBasedList.setTel(String.valueOf(map.get("tel")));
			areaBasedList.setReadcount(String.valueOf(map.get("readcount")));
			
			w=weatherRepository.findByAreacode((Integer) map.get("areacode"));
			areaBasedList.setWeather(w);
			
			areaBasedListRepository.save(areaBasedList);
			areaBasedList = new AreaBasedList();
		}

		return item;
	}

	@GetMapping("/test")
	public List<AreaBasedList> test() {
		return areaBasedListRepository.findAll();
	}

}

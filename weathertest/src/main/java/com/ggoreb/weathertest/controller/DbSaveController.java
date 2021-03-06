package com.ggoreb.weathertest.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

@RestController
public class DbSaveController {
	@Autowired
	CovidRepository covidRepository;
	@Autowired
	WeatherRepository weatherRepository;
	@Autowired
	AirRepository airRepository;
	@Autowired
	AreaBasedListRepository areaBasedListRepository;
	@Autowired
	AreaTempRepository areaTempRepository;

	private final String covidKey ="2Ty16FHtqKEzO%2BqOMfaqyU6BjHFRao4HW4JyAjbvZMjbIucyPrl2CX%2FKNWBsO6WMLVHsse8zTQwdew1%2BESQsfA%3D%3D";
	private final String AirKey = "2Ty16FHtqKEzO%2BqOMfaqyU6BjHFRao4HW4JyAjbvZMjbIucyPrl2CX%2FKNWBsO6WMLVHsse8zTQwdew1%2BESQsfA%3D%3D";
	private final String WeatherKey = "2Ty16FHtqKEzO%2BqOMfaqyU6BjHFRao4HW4JyAjbvZMjbIucyPrl2CX%2FKNWBsO6WMLVHsse8zTQwdew1%2BESQsfA%3D%3D";
	private final String TourKey = "MUMreSgE%2BSS7myJQ4TQvAZ4v3GKdHjY4w58566xQLA%2Fvt702075b8IUFHfFyv96B3l6j%2FyksrgR%2FY2yAeUIruA%3D%3D";
	
	
	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}

	
	@GetMapping("/covid")
	public void covid() {
		Covid covid = new Covid();
		try {
			String covidUrl = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey="
					+ covidKey
					+ "&pageNo=1&numOfRows=10&startCreateDt=20210722&endCreateDt=20210722";

			Document documentInfo = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(covidUrl);

			// root tag
			documentInfo.getDocumentElement().normalize();

			// ????????? tag
			NodeList nList = documentInfo.getElementsByTagName("item");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					covid.setArea(getTagValue("gubun", eElement));
					covid.setIncdec(getTagValue("incDec", eElement));
					covid.setTotal(getTagValue("defCnt", eElement));
					covid.setIsol(getTagValue("isolIngCnt", eElement));
					covid.setIsolClear(getTagValue("isolClearCnt", eElement));
					covid.setOverflow(getTagValue("overFlowCnt", eElement));
					covid.setLocal(getTagValue("localOccCnt", eElement));

					covidRepository.save(covid);
					covid = new Covid();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@GetMapping("/air")
	public String[] air() {
		RestTemplate rt = new RestTemplate();
		RequestEntity requestEntity = null;
		try {
			requestEntity = RequestEntity.get(new URI(
					"http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth?searchDate=2021-07-22&returnType=json&serviceKey="
					+ AirKey
					+ "&numOfRows=100&pageNo=1"))
					.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		ResponseEntity<Map> entity = rt.exchange(requestEntity, Map.class);
		Map<String, Object> temp = entity.getBody();
		Map<String, Object> response = (Map<String, Object>) temp.get("response");
		Map<String, Object> body = (Map<String, Object>) response.get("body");
		List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("items");
		Map<String, Object> item = items.get(0);
		String informGrade = (String) item.get("informGrade");
		String[] array = informGrade.split(",");

		for (int i = 0; i < array.length; i++) {
			Air air = new Air();
			if (array[i].contains("??????")) {
				air.setAirInfo(array[i]);
				air.setAreacode(35);
				airRepository.save(air);
			} else if (array[i].contains("??????")) {
				air.setAirInfo(array[i]);
				air.setAreacode(36);
				airRepository.save(air);
			} else if (array[i].contains("??????")) {
				air.setAirInfo(array[i]);
				air.setAreacode(4);
				airRepository.save(air);
			} else if (array[i].contains("??????")) {
				air.setAirInfo(array[i]);
				air.setAreacode(6);
				airRepository.save(air);
			} else if (array[i].contains("??????")) {
				air.setAirInfo(array[i]);
				air.setAreacode(7);
				airRepository.save(air);
			}

		}
		return array;
	}

	@GetMapping("/weather")
	public List<Map<String, Object>> weather() {
		Weather weather = new Weather();
		RestTemplate rt = new RestTemplate();
		RequestEntity requestEntity = null;
		try {
			requestEntity = RequestEntity
					.get(new URI("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?"
							+ "serviceKey="
							+ WeatherKey
							+ "&dataType=json" + "&numOfRows=10" + "&pageNo=1" + "&base_date=20210722"
							+ "&base_time=0800" + "&nx=89" + "&ny=91"))
					.build();
			//    x  y ????????????
			//?????? 98 76 6
			//?????? 89 90 4
			//?????? 102 84 7
			//?????? 89 91 35
			//?????? 91 77 36
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		ResponseEntity<Map> entity = rt.exchange(requestEntity, Map.class);
		Map<String, Object> result = entity.getBody();
		Map<String, Object> response = (Map<String, Object>) result.get("response");
		Map<String, Object> body = (Map<String, Object>) response.get("body");
		Map<String, Object> items = (Map<String, Object>) body.get("items");
		List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");

		for (Map<String, Object> map : item) {
			System.out.println((String) map.get("category"));
			if (((String) map.get("category")).equals("POP")) {
				weather.setPopCategory((String) map.get("category"));
				weather.setPopCategoryValue((String) map.get("fcstValue"));

			} else if (((String) map.get("category")).equals("SKY")) {
				weather.setSkyCategory((String) map.get("category"));
				weather.setSkyCategoryValue((String) map.get("fcstValue"));
			}
			weather.setNx((Integer) map.get("nx"));
			weather.setNy((Integer) map.get("ny"));
		}
		weather.setAreacode(35);
		weatherRepository.save(weather);
		return item;
	}
	private final String TourUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService";
	// **** [ AreaBasedList ] AreaCode??? ??????????????? ?????? *****************
	// addr1, areaCode, x,y, tel, title, firstimage
	@GetMapping("/api3")
	public List<Map<String, Object>> allowBasic3() throws Exception {
		RestTemplate rt = new RestTemplate();
		RequestEntity requestEntity = null;
		try {
			requestEntity = RequestEntity.get(new URI(TourUrl + "/areaBasedList" 
					+ "?serviceKey=" + TourKey
					+ "&numOfRows=100" + "&pageNo=1" + "&MobileOS=ETC" + "&MobileApp=AppTest" + "&arrange=A"
					+ "&contentTypeId=15" + "&areaCode=4"// 4,6,7,35,36
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

		// ????????? ????????????
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

			w = weatherRepository.findByAreacode((Integer) map.get("areacode"));
			areaBasedList.setWeather(w);

			areaBasedListRepository.save(areaBasedList);
			areaBasedList = new AreaBasedList();
		}

		return item;
	} 

	@GetMapping("/recommend")
	public void recommend() {
		List<AreaBasedList> areaList = new ArrayList<AreaBasedList>();

		Covid covid = new Covid();
		List<String> stringTemp = new ArrayList<String>();
		stringTemp.add("??????");
		stringTemp.add("??????");
		stringTemp.add("??????");
		stringTemp.add("??????");
		stringTemp.add("??????");
		Map<Integer, Covid> covidMap = new HashMap<Integer, Covid>();
		for (String a : stringTemp) {
			covid = covidRepository.findByArea(a);
			if (covid.getIncdec().compareTo("50") < 0) {
				if (covid.getArea().equals("??????")) {
					covidMap.put(35, covid);
				} else if (covid.getArea().equals("??????")) {
					covidMap.put(36, covid);
				} else if (covid.getArea().equals("??????")) {
					covidMap.put(4, covid);
				} else if (covid.getArea().equals("??????")) {
					covidMap.put(6, covid);
				} else if (covid.getArea().equals("??????")) {
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

		List<Air> airTemp = airRepository.findAll();
		List<Air> airResult = new ArrayList<Air>();
		for (Air a : airTemp) {
			if (a.getAirInfo().contains("??????")) {
				airResult.add(a);
			}
		}

		List<AreaBasedList> areaTemp = new ArrayList<AreaBasedList>();

		for (Weather w : weatherResult) {
			for (Integer key : covidMap.keySet()) {
				for (Air a : airResult) {
					if (w.getAreacode() == key && a.getAreacode() == key) {
						areaTemp = areaBasedListRepository.findAllByAreacode(key);
					}
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

}

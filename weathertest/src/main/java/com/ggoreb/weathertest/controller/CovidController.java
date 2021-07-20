package com.ggoreb.weathertest.controller;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ggoreb.weathertest.model.Covid;
import com.ggoreb.weathertest.repository.CovidRepository;

@RestController
public class CovidController { 
	@Autowired
	CovidRepository covidRepository;
	
	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node)nlList.item(0);
		if(nValue==null)
			return null;
		return nValue.getNodeValue();
	}
	
	@GetMapping("/covid")
	public void covid() {
		Covid covid = new Covid();
		try {
			String url="http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=2Ty16FHtqKEzO%2BqOMfaqyU6BjHFRao4HW4JyAjbvZMjbIucyPrl2CX%2FKNWBsO6WMLVHsse8zTQwdew1%2BESQsfA%3D%3D&pageNo=1&numOfRows=10&startCreateDt=20210720&endCreateDt=20210720";
			
			Document documentInfo = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url);
			
			//root tag
			documentInfo.getDocumentElement().normalize();
			
			//파싱할 tag
			NodeList nList = documentInfo.getElementsByTagName("item");
			
			for(int temp=0;temp<nList.getLength();temp++) {
				Node nNode = nList.item(temp);
				if(nNode.getNodeType()==Node.ELEMENT_NODE) {
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
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

package com.demo.config;



import com.demo.config.mfi.MfiSeriesNameEndPoints;
import com.demo.config.mfi.UrlResolver;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum AppConfig {

	APPCONFIG;

	private Config conf;

	private MfiSeriesNameEndPoints mfi;
	private List<String> mfiSeriesName;

	private String startTime;
	private String stopTime;


	AppConfig() {
		conf = ConfigFactory.load();
		readMfiSeriesNameEndPoints();
		readMfiSeriesName();
		readTimes();

	}

	public String getStartTime() {
		return startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	private void readTimes() {
		startTime = conf.getString("config.starttime");
		stopTime = conf.getString("config.stoptime");

	}

	public MfiSeriesNameEndPoints getMfiSeriesNameEndPoint() {
		return mfi;
	}

	public List<String> getMfiSeriesName() {
		return mfiSeriesName;
	}

	private void readMfiSeriesNameEndPoints() {

		mfi = (MfiSeriesNameEndPoints) new MfiSeriesNameEndPoints()
				.setHolidayCalendar(extractUrlResolver("config.mfiseriesnameEndPoints.holidayCalendar"))
				.setTranslateSymbol(extractUrlResolver("config.mfiseriesnameEndPoints.translateSymbol"))
				.setLiquidHours(extractUrlResolver("config.mfiseriesnameEndPoints.liquidHours"));

	}

	@SuppressWarnings("unchecked")
	private UrlResolver extractUrlResolver(String queryPath) {
		Map<String, Object> treeRes = conf.getObject(queryPath).unwrapped();
		URI uri = null;
		String type = null;
		String body = null;
		Map<String, String> httpHeaders = null;
		List<String> queryParams = null;
		try {
			uri = new URI((String) treeRes.get("url"));
			type = (String) treeRes.get("type");
			List<Map<String, Object>> headers = (List<Map<String, Object>>) treeRes.get("httpheaders");
			httpHeaders = headers.stream().map(v -> v.entrySet().iterator().next())
					.collect(Collectors.toMap(val -> val.getKey(), val -> (String) val.getValue()));
			body = (String) treeRes.get("body");
			queryParams = (List<String>) treeRes.get("queryparams");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return new UrlResolver().setUri(uri).setBody(body).setType(type).setHttpHeaders(httpHeaders)
				.setQueryParams(queryParams);

	}

	public void readMfiSeriesName() {
		mfiSeriesName = conf.getList("config.mfiseriesname").unwrapped().stream().map(Object::toString)
				.collect(Collectors.toList());
	}



}
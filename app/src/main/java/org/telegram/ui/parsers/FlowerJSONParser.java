package org.telegram.ui.parsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.ui.model.Flower;

import java.util.ArrayList;
import java.util.List;

public class FlowerJSONParser {
	
	public static List<Flower> parseFeed(String content) {
	
		try {

			JSONObject jsonObj = new JSONObject(content);
			// Getting JSON Array node
			JSONArray ar = jsonObj.getJSONArray("lastvideos");


			//JSONArray ar = new JSONArray(content);
			List<Flower> flowerList = new ArrayList<>();

			for (int i = 0; i < ar.length(); i++) {
				
				JSONObject obj = ar.getJSONObject(i);
				Flower flower = new Flower();

				//flower.setProductId(obj.getInt("productId"));
				flower.setName(obj.getString("title"));
				flower.setLink(obj.getString("frame"));
				flower.setPhoto(obj.getString("small_poster"));
               // flower.setBitmap(obj.getString("body"));

				
				flowerList.add(flower);
			}
			
			return flowerList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}

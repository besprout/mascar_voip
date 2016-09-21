package com.besprout.voip.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:json工具類
 * </p>
 * 
 * <p>
 * Copyright: Copyright 2012 Besprout All right reserved.
 * </p>
 * 
 * <p>
 * Company:besprout
 * </p>
 * 
 * @author wander
 * 
 * @version 1.0
 * 
 * @createtime 2:15:29 PM-Jun 18, 2012
 * 
 * @histroy 修改历史
 * 
 * <li>版本号 修改日期 修改人 修改说明
 * 
 * <li>
 * 
 * <li>
 */
public class JsonUtil {
	/**
	 * 转成JSON字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String getJsonString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param jsonStr
	 * @param T
	 * @return
	 */
	public static <T> T readJsonEntity(String jsonStr, Class<T> T) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			return mapper.readValue(jsonStr, T);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * JsonNode
	 * 
	 * @param jsonStr
	 * @see { 代码示例： String resultJson
	 *      ="{'playerResults':[{'playerId':'111','gameId':'','tee':'0,0,0'},{'playerId':'ff80808137f7daac0137f7dd1ab80001','gameId':'','tee':'255,255,255'}]}";
	 *      JsonNode jn=readJsonEntity(resultJson); jn=jn.get("playerResults");
	 *      for (int i = 0; i < jn.size(); i++){ String
	 *      playerId=jn.get(i).get("playerId").asText();
	 *      logger.info("playerId="+playerId); } }
	 * @return
	 */
	public static JsonNode readJsonEntity(String jsonStr) {
		return readJsonEntity(jsonStr, JsonNode.class);
	}
}

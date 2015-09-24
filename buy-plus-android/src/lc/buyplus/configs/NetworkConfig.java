package lc.buyplus.configs;

public class NetworkConfig {
	public static String NEO4J_USERNAME = "uetkitchen";
	public static String NEO4J_PASSWORD = "08Baal7sKsWTRU4wU3vu";
	public static String NEO4J_HOST = "uetkitchen.sb05.stations.graphenedb.com:24789";
	
	
	public static final String URL_JSON_OBJECT_REQUREST = "http://api.androidhive.info/volley/person_object.json";
	public static final String URL_JSON_ARRAY_REQUEST = "http://api.androidhive.info/volley/person_array.json";
	public static final String URL_STRING_REQUEST = "http://api.androidhive.info/volley/string_response.html";
	public static final String URL_IMAGE_REQUEST = "http://api.androidhive.info/volley/volley-image.jpg";
	
	public static String AIML_BOT_ID_EN = "55b5a215e4b0b343f04f50c0";
	public static String AIML_BOT_ID_VI = "55b5a21be4b0b343f04f50c1";
	public static String AIML_HOST = "http://118.69.135.23/AIML/api/bots/";
	public static String AIML_TOKEN = "9809cdc1-2481-4959-bdc6-a2f4ec3dc3a7";
	
	public static String TTS_SERVER = "http://118.69.135.22";
	
	public static String getAIMLQueryEn(String request) {
		return AIML_HOST + AIML_BOT_ID_EN + "/chat?request=" + request + "&token=" + AIML_TOKEN;
	}
	
	public static String getAIMLQueryVi(String request) {
		return AIML_HOST + AIML_BOT_ID_VI + "/chat?request=" + request + "&token=" + AIML_TOKEN;
	}
}

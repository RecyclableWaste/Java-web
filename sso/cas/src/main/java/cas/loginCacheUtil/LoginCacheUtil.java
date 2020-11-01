package cas.loginCacheUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cas.domain.User;

public class LoginCacheUtil {
	public static Map<String,User> tokenAndUser = new HashMap<>();
	public static Map<String,Set<Pair<String,String>>> tokenAndURLs = new HashMap<>();

	
	public static void addTokenAndUrlAndSession(String token, String url, String session)
	{
		Set<Pair<String,String>> urlAndSession = tokenAndURLs.get(token);
		if(urlAndSession==null)
		{
			urlAndSession = new HashSet<Pair<String, String>>();
			tokenAndURLs.put(token, urlAndSession);
		}
		Pair<String,String> pair = new Pair<String, String>(url,session);
		urlAndSession.add(pair);
		
	}
	public static Set<Pair<String,String>> getURLAndSessions(String token)
	{
		return tokenAndURLs.get(token);
	}
	

}


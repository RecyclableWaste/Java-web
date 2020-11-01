package cas.verifyTool;

import cas.domain.User;
import cas.loginCacheUtil.LoginCacheUtil;

public class Verification {

	public static boolean verifyToken(String token)
	{
		User user = LoginCacheUtil.tokenAndUser.get(token);
		return user!=null;
	}
}

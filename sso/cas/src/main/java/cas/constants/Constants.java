package cas.constants;

public class Constants {

	public static final String LOCAL_SERVICE = "LOCAL_SERVICE";
	public static final String CAS_URL = "http://localhost:8080/cas/";
	public static final String CAS_LOGOUT_URL = CAS_URL+"logout";
	public static final String CAS_LOGIN_URL = CAS_URL + "login";
	public static final String LOGOUT_ARG_NAME = "CAS_LOGOUT";
//	public static final String CAS_LOGIN_URL = CAS_URL+"login/";
	public static final String VERIFY_URL = "http://localhost:8080/cas/ma";
	public static final String VERIFY_ARG_NAME = "info";
	public static final String VERIFY_RESULT_ARG_NAME = "result";
	public static final String VERIFY_PASS = "pass";
	public static final String VERIFY_FAIL = "fail";
	public static final String APP1_URL = "http://localhost:8080/app1/";
	public static final String APP2_URL = "http://localhost:8080/app2/";
	public static final String USER_ARG_NAME = "uname";
	
	public static boolean isSubSystem(String url)
	{
		return url!=null && (url.startsWith(APP1_URL) || url.startsWith(APP2_URL));
	}
}

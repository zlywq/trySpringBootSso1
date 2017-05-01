package com.example;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Value;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	@Value("${myOauthServer.hostUrl}")
//	String myOauthServer_hostUrl;
	
	@Value("${myOauthServer.logoutUrl}")
	String myOauthServer_logoutUrl;

//	@Value("${security.oauth2.client.accessTokenUri}")
//	String security_oauth2_client_accessTokenUri;

    @RequestMapping("/")
    public String home(Locale locale, Model model, Principal puser){
    	
    	//logger.debug("myOauthServer_hostUrl="+myOauthServer_hostUrl+"  ,myOauthServer_logoutUrl="+myOauthServer_logoutUrl+"  ,security_oauth2_client_accessTokenUri="+security_oauth2_client_accessTokenUri);
    	
    	Date date = new Date();
    	
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("srvTimeStr", formattedDate );
		
		if (puser != null){
//			HashMap<String,Object> mp = new HashMap<>();
//			mp.put("username",puser.getName());
			model.addAttribute("username", puser.getName());
		}
    	
        return "home";
    }
	

    @RequestMapping(value="/deny")
    public String handleDeny(){
        return "deny";
    }

    @RequestMapping("/signout2")
    public String signout2aboutsso(Model model) {
    	
    	//model.addAttribute("myOauthServer_hostUrl", myOauthServer_hostUrl);
    	model.addAttribute("myOauthServer_logoutUrl", myOauthServer_logoutUrl);
    	
        return "signout2";
    }

    
//	@RequestMapping("/sso/login")
//	public String dashboard() {
//		return "redirect:/#/";
//	}
//    @RequestMapping("/login")
//    public String login() {
//        return "redirect:/#/";
//    }

	
	
	@RequestMapping("/throwerr0")
	public int zeroException(){
		return 100/0;
	}
}

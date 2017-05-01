package com.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/myif")
public class InfoController {
	private static final Logger logger = LoggerFactory.getLogger(InfoController.class);
	
    

    @RequestMapping("/info")
    public String info(Locale locale, Model model, Principal puser){
    	
    	Date date = new Date();
    	
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("srvTimeStr", formattedDate );
		
		if (puser != null){
//			HashMap<String,Object> mp = new HashMap<>();
//			mp.put("username",puser.getName());
			model.addAttribute("username", puser.getName());
		}
    	
        return "info";
    }


}

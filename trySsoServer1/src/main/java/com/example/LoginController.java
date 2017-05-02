package com.example;


import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Date;
import java.util.Map;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(){
        return "loginpage";//"login";
    }

    @RequestMapping("/signout")
    public String signout(HttpServletRequest request) throws Exception{
        request.logout();
        return "tologin";
    }
    
    @RequestMapping("/logout2")
    public String logout2(HttpServletRequest request) throws Exception{
        request.logout();
        return "redirect:/";
    }
    
    //  /logout的mapping默认是post的，get的没有，然而某些时候莫名其妙会跳到get的/logout。不管logoutSuccessUrl是设置为/logout2还是/signout。
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(HttpServletRequest request) throws Exception{
        request.logout();
        return "redirect:/";
    }

    
    /*
注意如果在AuthorizationServerConfigurerAdapter的子类的configure(ClientDetailsServiceConfigurer clients)里面进行了autoApprove(true)，则这个页面不起作用。
     */
    @RequestMapping("/oauth/confirm_access")
    public String confirm_access(){
        return "confirm_access";
    }


}

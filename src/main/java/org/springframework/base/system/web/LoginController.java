//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.base.common.utils.StringUtils;
import org.springframework.base.system.utils.DBUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"treesoft"})
public class LoginController {
    public static Map<String, String> loginUserMap = new HashMap();

    public LoginController() {
    }

    @RequestMapping(
            value = {"login"},
            method = {RequestMethod.GET}
    )
    public String login() {
        return "system/login";
    }

    @RequestMapping({"index"})
    public String treesoft(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String username = (String) session.getAttribute("LOGIN_USER_NAME");
        String permission = (String) session.getAttribute("LOGIN_USER_PERMISSION");
        request.setAttribute("username", username);
        request.setAttribute("permission", permission);
        return "system/index";
    }

    @RequestMapping(
            value = {"loginVaildate"},
            method = {RequestMethod.POST}
    )
    public String loginVaildate(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username1").toLowerCase();
        String password = request.getParameter("password1").toLowerCase();
        String captcha = request.getParameter("captcha").toLowerCase();
        username = StringEscapeUtils.escapeHtml4(username.trim());
        HttpSession session = request.getSession(true);
        String cap = (String) session.getAttribute("KAPTCHA_SESSION_KEY");
        String message = "";
        HashMap map = new HashMap();
        if (username != "" && username != null) {
            String identifyingCode = "0";

            try {
                ClassPathResource list = new ClassPathResource("application.properties");
                Properties sql = PropertiesLoaderUtils.loadProperties(list);
                identifyingCode = (String) sql.get("identifyingCode");
            } catch (IOException var23) {
                var23.printStackTrace();
            }

            if (identifyingCode.equals("1") && !captcha.equals(cap)) {
                message = "验证码错误！";
                map.put("error", message);
                request.setAttribute("message", message);
                return "system/login";
            } else {
                new ArrayList();
                String sql1 = " select * from treesoft_users where  username=\'" + username + "\'";
                DBUtil db = new DBUtil();

                List list1;
                try {
                    list1 = db.executeQuery(sql1);
                } catch (Exception var22) {
                    list1 = null;
                    var22.printStackTrace();
                }

                if (list1.size() <= 0) {
                    message = "您输入的帐号或密码有误！";
                    map.put("error", message);
                    request.setAttribute("message", message);
                    return "system/login";
                } else {
                    String pas = (String) ((Map) list1.get(0)).get("password");
                    String status = (String) ((Map) list1.get(0)).get("status");
                    String expiration = (String) ((Map) list1.get(0)).get("expiration");
                    String permission = (String) ((Map) list1.get(0)).get("permission");
                    if ("1".equals(status)) {
                        message = "当前用户已禁用！";
                        map.put("error", message);
                        request.setAttribute("message", message);
                        return "system/login";
                    } else {
                        SimpleDateFormat sdf;
                        if (!"".equals(expiration) || expiration != null) {
                            try {
                                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date bt = sdf.parse(expiration);
                                Date nowDate = new Date();
                                if (bt.before(nowDate)) {
                                    message = "当前用户已过期！";
                                    map.put("error", message);
                                    request.setAttribute("message", message);
                                    return "system/login";
                                }
                            } catch (Exception var21) {
                                ;
                            }
                        }

                        if (!pas.equals(StringUtils.MD5(password + "treesoft" + username))) {
                            message = "您输入的帐号或密码有误！";
                            map.put("error", message);
                            request.setAttribute("message", message);
                            return "system/login";
                        } else {
                            message = "登录成功！";
                            session.setAttribute("LOGIN_USER_NAME", username);
                            session.setAttribute("LOGIN_USER_PERMISSION", permission);
                            loginUserMap.put(username, session.getId());
                            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            System.out.println("TreeDMS login user  " + username + " " + sdf.format(new Date()));
                            request.setAttribute("username", username);
                            return "redirect:/treesoft/index";
                        }
                    }
                }
            }
        } else {
            message = "请输入帐号！";
            map.put("error", message);
            request.setAttribute("message", message);
            return "system/login";
        }
    }

    @RequestMapping({"logout"})
    public String logout(HttpServletRequest request) {
        Enumeration em = request.getSession().getAttributeNames();

        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(((String) em.nextElement()).toString());
        }

        request.getSession().invalidate();
        return "system/login";
    }
}

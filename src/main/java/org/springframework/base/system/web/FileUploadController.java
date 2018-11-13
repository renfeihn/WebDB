/*    */ package org.springframework.base.system.web;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.springframework.base.common.web.BaseController;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.util.FileCopyUtils;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.ResponseBody;
/*    */ import org.springframework.web.multipart.MultipartFile;
/*    */ 
/*    */ @Controller
/*    */ @RequestMapping({"system/fileUpload"})
/*    */ public class FileUploadController extends BaseController
/*    */ {
/*    */   @RequestMapping(value={"i/fileUpload"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   @ResponseBody
/*    */   public Map<String, Object> fileUpload(MultipartFile fileToUpload, HttpServletRequest request)
/*    */     throws Exception
/*    */   {
/* 32 */     String filename = fileToUpload.getOriginalFilename();
/* 33 */     System.out.println("上传文件:" + filename);
/* 34 */     String realPathStr = request.getSession().getServletContext().getRealPath("/backup");
/* 35 */     File realPath = new File(realPathStr);
/*    */ 
/* 38 */     if (!realPath.exists()) realPath.mkdirs();
/*    */ 
/* 41 */     File writeFile = new File(realPath + File.separator + filename);
/*    */ 
/* 43 */     Map map = new HashMap();
/* 44 */     String mess = "";
/* 45 */     String status = "";
/*    */     try
/*    */     {
/* 48 */       FileCopyUtils.copy(fileToUpload.getBytes(), writeFile);
/* 49 */       mess = "文件上传完成！";
/* 50 */       status = "success";
/*    */     } catch (Exception e) {
/* 52 */       System.out.println(e.getMessage());
/* 53 */       mess = e.getMessage();
/* 54 */       status = "fail";
/*    */     }
/*    */ 
/* 57 */     map.put("mess", mess);
/* 58 */     map.put("status", status);
/* 59 */     return map;
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.web.FileUploadController
 * JD-Core Version:    0.6.0
 */
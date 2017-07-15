package ssoft.servlet.admin;

import io.rong.ApiHttpClient;
import io.rong.models.BroadcastMessage;
import io.rong.models.FormatType;
import io.rong.models.Message;
import io.rong.models.SdkHttpResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import ssoft.global.Global;

public class VersionAdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置编码格式为utf-8
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); 
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        List<FileItem> list = null;
		try {
			list = (List<FileItem>)upload.parseRequest(request);
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		} 
		FileItem item1 = list.get(0);
        String version = item1.getString() ; 
        String filename = "";
        //获取APK需要上传到的路径（服务器根目录）  
		String APKpath = request.getRealPath("");       
        FileItem item = list.get(1);
        //获取路径名  
        String value = item.getName() ;  
        //索引到最后一个反斜杠  
        int start = value.lastIndexOf("\\");  
        //截取 上传文件的 字符串名字，加1是 去掉反斜杠，  
        filename = value.substring(start+1);  
        //真正写到磁盘上  
        //它抛出的异常 用exception 捕捉     
        try {
        	OutputStream out = new FileOutputStream(new File(APKpath,filename));             
            InputStream in = item.getInputStream() ;              
            int length = 0 ;  
            byte [] buf = new byte[1024] ;               
            System.out.println("获取上传APk的总共的容量："+item.getSize());  
            while( (length = in.read(buf) ) != -1)  
            {  
                out.write(buf, 0, length);       
            }   
            in.close();  
            out.close();  
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        //版本信息写到服务器根目录下的version.txt中
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String formateDate = format.format(date);
        String data = formateDate + "===" + version + "===" + filename;
        byte[] buffer = data.getBytes();
        try {
        	FileOutputStream os = new FileOutputStream(APKpath + "/version.txt");
            os.write(buffer);
            os.flush();
            os.close();
        } catch (IOException e) {
        	e.printStackTrace();  
        }
        //通过融云发送广播通知用户新版本发布了 
        try {
			SdkHttpResult removeRet = ApiHttpClient.broadcastMessage(
					Global.Rong_App_Key, Global.Rong_App_Secret, "10099",new BroadcastMessage("RC:TxtMsg", "新版本发布！"),
					"", "", FormatType.json);
			Map<String, String> mapCode = new HashMap<String, String>();
			Gson gson = new Gson();
			mapCode = gson.fromJson(removeRet.getResult(), Map.class);
			if (mapCode.get("code") == "200") {
				System.out.println("新版本发布广播成功！");
			} else{
				System.out.println("新版本发布广播失败！" + mapCode.get("errorMessage"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        String path = request.getContextPath();
		String URL = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
        request.setAttribute("version", version);
        request.setAttribute("date", formateDate);
        request.setAttribute("url", URL + filename);       
        request.getRequestDispatcher("/WEB-INF/updateAndroid.jsp").forward(request,response);
	}

}

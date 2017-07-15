package ssoft.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateApkServlet extends Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求的编码格式
		super.setEncoding(request,response);
		String version = "";
		String path = request.getContextPath();
		String APKpath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		String versionpath = request.getRealPath("");
		try {
			FileInputStream in = new FileInputStream(versionpath + "/version.txt");
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
        	version = new String(buffer);
        	in.close();
        } catch (IOException e) {
        	StringBuffer buffer = new StringBuffer();
    		buffer.append("{");
    		buffer.append("\"error\":\"Not find version.txt!\",");
    		buffer.append("}");
    		PrintWriter w = response.getWriter();
    		w.print(buffer.toString()); 
    		return;
        }
		String[] v = version.split("===");
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"version\":\"" + v[1] + "\",");
		buffer.append("\"date\":\"" + v[0] + "\",");
		buffer.append("\"apkUrl\":\"" + APKpath + v[2] + "\"");
		buffer.append("}");
		PrintWriter w = response.getWriter();
		w.print(buffer.toString());
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}

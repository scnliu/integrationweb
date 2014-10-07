package net.linybin7.common.tag;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class Msg {
 private final HttpServletResponse rsp;
 private final List<String> msgList=new ArrayList<String>();
 private boolean hasError;
 private boolean hasWarn;
 public Msg(HttpServletResponse rsp){
	 this.rsp=rsp;
 }
  public void addMsg(String msg){
	 msgList.add(msg);
  }
  public static final void writeObj(Object obj,HttpServletResponse rsp) throws IOException
  {
		writeObj(obj,rsp,0);
  }
  public static final void writeObj(Object obj,HttpServletResponse rsp,int type) throws IOException
  {
	  rsp.setContentType("text/json;charset=GBK");
	  rsp.setHeader("Cache-Control","no-store, max-age=0, no-cache, must-revalidate");    
	  rsp.addHeader("Cache-Control", "post-check=0, pre-check=0");
	  rsp.setHeader("Pragma", "no-cache"); 
	  rsp.setCharacterEncoding("GBK");
	  JsonConfig jc=new JsonConfig();
	  jc.setIgnoreDefaultExcludes(false);
	  jc.setSkipJavaIdentifierTransformationInMapKeys(false);
	  String result=JSONSerializer.toJSON(obj,jc).toString(1);
//		System.out.println("result:"+result);
	  PrintWriter pw = rsp.getWriter();
	  pw.write(result);
	  pw.flush();
	  pw.close();
  }
  public void addError(String error){
	  hasError=true;
	  msgList.add(error);
  }
  public void writeToPage(){
		rsp.setContentType("text/json;charset=GBK");
        rsp.setHeader("Cache-Control","no-store, max-age=0, no-cache, must-revalidate");    
        rsp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        rsp.setHeader("Pragma", "no-cache"); 
		rsp.setCharacterEncoding("GBK");
		Map map=new HashMap();
		map.put("success", !hasError);
		map.put("items",msgList);
		try {
			this.writeObj(map,rsp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
  }
  public void success(){
	  addMsg("1");
	  writeToPage();
  }
  public void addSuccess(){
	  addMsg("添加成功!");
	  writeToPage();
  }
  public void editSuccess(){
	  addMsg("修改成功!");
	  writeToPage();
  }
  public void error(){
	  addError("发生错误!");
	  writeToPage();
  }
  public void duplicateId(){
	  addError("该编号已存在!");
	  writeToPage(); 
  }
}

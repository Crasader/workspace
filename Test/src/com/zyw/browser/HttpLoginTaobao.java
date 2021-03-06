package com.zyw.browser;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
/**
 * This class implements a web browser
 */
public class HttpLoginTaobao {
    private Browser browser;  
    private Browser browser2;  
    private Browser browser3; 
    private Browser browser4;  
    public final static String USER_DIR = System.getProperty("user.dir") + File.separatorChar;
    /**
     * Runs the application
     * 
     * the initial location to display
     */
    public void run() {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("淘宝代付款");
        createContents(shell);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
 
    /**
     * Creates the main window's contents
     * 
     * @param shell
     * the main window
     * @param location
     * the initial location
     */
    public void createContents(Shell shell) {
        shell.setLayout(new FormLayout());
 
        Composite controls = new Composite(shell, SWT.NONE);
        FormData data = new FormData();
        browser = new Browser(shell, SWT.BORDER);
        data.top = new FormAttachment(controls);
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(100, 0);
        data.bottom = new FormAttachment(100, 0);
        browser.setLayoutData(data);
        controls.setLayout(new GridLayout(0, false));
        //第一步：登录淘宝
        browser.setUrl("https://login.taobao.com/member/login.jhtml?redirect_url=http%3A%2F%2Fwww.taobao.com%2F");
        browser.addProgressListener(new ProgressAdapter(){
            public void    completed(ProgressEvent event){
                //System.out.println(browser.getUrl());
                if(browser.getUrl().startsWith("https://")){
                    //browser.execute("alert(document.documentElement.outerHTML)");  //可以得到页面源文件
                    browser.execute("function getElements(formId) {  \r\n" + 
                    		"  var form = document.getElementById(formId);  \r\n" + 
                    		"  var elements = new Array();  \r\n" + 
                    		"  var tagElements = form.getElementsByTagName('input');  \r\n" + 
                    		"  for (var j = 0; j < tagElements.length; j++){ \r\n" + 
                    		"     elements.push(tagElements[j]);alert(tagElements[j].name+'='+tagElements[j].value); \r\n" + 
                    		"  \r\n" + 
                    		"  } \r\n" + 
                    		"  return elements;  \r\n" + 
                    		"};document.getElementById('J_SubmitStatic').addEventListener(\"click\",hello);function hello(){document.getElementById('TPL_password_1').value='ZyW1987541x';getElements('J_Form');"
                    		+ "};document.getElementById('J_SubmitStatic').click();");
                    //browser.execute(getGotoScript());
                }
            }
        });
         
        /*//第二步：访问代付款链接
        browser2 = new Browser(shell, SWT.BORDER);
        browser2.setLayoutData(data);
        controls.setLayout(new GridLayout(0, false));
        browser2.setUrl("https://passport.baidu.com/?reg&tpl=mn");
        browser2.addProgressListener(new ProgressAdapter(){
            public void    completed(ProgressEvent event){
                //System.out.println(browser.getUrl());
                if(browser2.getUrl().startsWith("https://")){
                    browser.execute(gotoPayForAnother());
                }
            }
        });
        //第三步：举个例子，第二步没完成，就已经执行第三步了
        browser4 = new Browser(shell, SWT.BORDER);
        browser4.setLayoutData(data);
        controls.setLayout(new GridLayout(0, false));
        browser4.setUrl("https://passport.baidu.com/?reg&tpl=mn");
        browser4.addProgressListener(new ProgressAdapter(){
            public void    completed(ProgressEvent event){
                //System.out.println(browser.getUrl());
                if(browser4.getUrl().startsWith("https://")){
                    browser.execute(gotoConfirmStep1());
                }
            }
        });*/
    }
     
    private String loginPageScript(){
        //内部执行script
        String scriptContent = "var inp_unameObj=document.getElementById('TPL_username_1');" + 
                                "if(inp_unameObj!=null) inp_unameObj.value='慕率两';" +
                                "var passwordObj=document.getElementById('TPL_password');" +
                                "if(passwordObj!=null) passwordObj.value='q12345';" + 
                                "var form = document.getElementById('J_StaticForm'); form.submit();";
        /*
        try{
            //System.out.println(USER_DIR + "scripts/taobao_login_url.js");
            File scriptFile=new File(USER_DIR + "scripts/taobao_login_url.js");
            if(scriptFile.exists()){
                scriptContent = FileUtils.readFileToString(scriptFile);
                //System.out.println("scriptContent=" + scriptContent);
            }
        }catch(Exception e){
            e.printStackTrace();
        }*/
        return scriptContent;
    }
     
    private String gotoPayForAnother(){
        String script="window.location.href = 'http://trade.taobao.com/trade/pay.htm?biz_order_id=63601298052309&biz_type=200&ispayforanother=true'";
        return script;
    }
     
    private String gotoConfirmStep1(){
        //内部执行script
        String scriptContent = "window.location.href = 'https://auth.alipay.com/login/index.htm'";
        /*
        try{
            File scriptFile=new File(USER_DIR + "scripts/confirm_step_1.js");
            if(scriptFile.exists()){
                scriptContent = FileUtils.readFileToString(scriptFile);
                System.out.println("scriptContent=" + scriptContent);
            }
        }catch(Exception e){
            e.printStackTrace();
        }*/
        return scriptContent;
    }
 
    public static void main(String[] args) {
        new HttpLoginTaobao().run();
    }
}

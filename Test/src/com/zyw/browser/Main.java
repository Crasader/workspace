package com.zyw.browser;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.zyw.util.Common;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;

public class Main extends JPanel {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame("Ives");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(new Main(), BorderLayout.CENTER);
                    frame.setSize(800, 600);
                    frame.setLocationByPlatform(true);
                    frame.setVisible(true);
                } catch (HeadlessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        NativeInterface.runEventPump();
    }

    public Main() throws IOException, URISyntaxException {
        super(new BorderLayout());
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        webBrowserPanel.setBorder(BorderFactory.createTitledBorder("Ives"));

        final JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.navigate("https://login.taobao.com/member/login.jhtml");
        webBrowser.addWebBrowserListener(new WebBrowserListener() {

			@Override
			public void commandReceived(WebBrowserCommandEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void loadingProgressChanged(WebBrowserEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void locationChangeCanceled(WebBrowserNavigationEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void locationChanged(WebBrowserNavigationEvent arg0) {
				String url=arg0.getNewResourceLocation();
				System.out.println(url);
				if(url.equalsIgnoreCase("https://login.taobao.com/member/login.jhtml")) {
					Common.TAO_BAO_LOGIN_COOKIE.forEach((key,value)->{
						String mpValue=JWebBrowser.getCookie(url, key);
						if(null!=mpValue) {
							System.out.println(mpValue);
						}
					});
				}
				
			}

			@Override
			public void locationChanging(WebBrowserNavigationEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void statusChanged(WebBrowserEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void titleChanged(WebBrowserEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WebBrowserEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpening(WebBrowserWindowOpeningEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowWillOpen(WebBrowserWindowWillOpenEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));

        // webBrowser.setBarsVisible(false); //显示前进返回刷新按钮
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

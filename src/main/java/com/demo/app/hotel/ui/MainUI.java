package com.demo.app.hotel.ui;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.demo.app.hotel.database.EntityService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
@SpringUI
public class MainUI extends UI {

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		
		getPage().setTitle("demo project: Hotels");
				
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		setContent(layout);

		VerticalLayout menu = new VerticalLayout();
		menu.setWidth("150px");
		layout.addComponent(menu);

		Panel content = new Panel();
		content.setSizeFull();
		layout.addComponent(content);
		layout.setExpandRatio(content, 1f);

		final Navigator navigator = new Navigator(this, content);

		menu.addComponent(new Button("Hotels", e -> {
			navigator.removeView("Hotels");
			navigator.addView("Hotels", new HotelForm());
			navigator.navigateTo("Hotels");
		}));
		menu.addComponent(new Button("Categories", e -> {
			navigator.removeView("Categories");
			navigator.addView("Categories", new CategoryForm());
			navigator.navigateTo("Categories");
		}));

		navigator.addView("Hotels", new HotelForm());
		navigator.navigateTo("Hotels");
	}
	
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
	
	@Configuration
	@EnableVaadin
	public static class MyConfiguration {
		@Bean
		public static EntityService getEntityService() {
			return new EntityService();
		};
	}
	
	@WebListener
	public static class MyContextLoaderListener extends ContextLoaderListener {}
}

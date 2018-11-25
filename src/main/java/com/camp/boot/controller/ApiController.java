package com.camp.boot.controller;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import com.camp.boot.bean.JavassitUtils;
import com.camp.boot.bean.UserService;
import com.camp.boot.dao.GnodeDao;
import com.camp.boot.dao.UserDao;
import com.camp.boot.domain.GnodeBO;
import com.camp.boot.domain.KafkaMsg;
import com.camp.boot.domain.ResUserOb;
import com.camp.boot.domain.User;
import com.camp.boot.job.FlowDetailJob;
import com.camp.boot.kafka.KafkaProducerCom;

@RestController
public class ApiController implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	private SchedulerFactory factory;
	
	private Scheduler scheduler;
	
	
	@Autowired
    @Qualifier("dataSourceS")
    private DataSource dataSource;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GnodeDao gNodeDao;
	
	@Autowired
	private KafkaProducerCom kafkaProducerCom;
	
	@RequestMapping(value = "/addApi" ,method = RequestMethod.GET)
	public String addApi(@RequestParam String apiName) throws Exception
	{
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;  
		
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext  
		        .getBeanFactory();  
		
		
		Class ctl = JavassitUtils.createDynamicClazz();
		
		/*ctl.getDeclaredMethod("sayHello",String.class).getAnnotations();*/
		
		
		
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder  
		        .genericBeanDefinition(ctl); 
		
		defaultListableBeanFactory.registerBeanDefinition("hello",  
		        beanDefinitionBuilder.getBeanDefinition());  
		
		//configurableApplicationContext.refresh();
		
		/*UserService service = (UserService) applicationContext.getBean("userService");
		
		String result = service.queryUser(apiName);*/
		
		RequestMappingInfoHandlerMapping servlet = (RequestMappingInfoHandlerMapping) applicationContext.getBean(RequestMappingInfoHandlerMapping.class);
		
		
		servlet.afterPropertiesSet();
		
		return apiName;
	}
	
	@RequestMapping(value = "/getUser" ,method = RequestMethod.GET)
	public List<User> getUser()
	{
		List<User> users = new ArrayList<User>();
		
		User user = new User();
		user.setUserName("liuwei");
		user.setAge(23);
		users.add(user);
		
		User user1 = new User();
		user1.setUserName("liuwei");
		user1.setAge(23);
		users.add(user1);
		
		return users;
	}
	
	@RequestMapping(value = "/getUsers" ,method = RequestMethod.GET)
	public ResUserOb getUsers()
	{
		ResUserOb result = new ResUserOb();
		List<User> users = new ArrayList<User>();
		
		User user = new User();
		user.setUserName("liuwei");
		user.setAge(23);
		users.add(user);
		
		User user1 = new User();
		user1.setUserName("liuwei");
		user1.setAge(23);
		users.add(user1);
		
		result.setData(users);
		result.setDraw(2);
		result.setRecordsFiltered(2);
		result.setRecordsTotal(2);
		
		return result;
	}
	
	
	@RequestMapping(value = "/addUser" ,method = RequestMethod.POST)
	public String addUser(@RequestBody User user)
	{
		/*String sql = "insert into sys_user(id) value(\"123\")";
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		userDao.insertUser(user);
		return "success";
	}
	
	
	@RequestMapping(value = "/queryGnode" ,method = RequestMethod.GET)
	public String queryGnode()
	{
		/*String sql = "insert into sys_user(id) value(\"123\")";
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		List<GnodeBO> result = gNodeDao.queryAll();
		return "success";
	}
	
	@RequestMapping(value = "/sendMsg" ,method = RequestMethod.POST)
	public String sendKafkaMsg(@RequestBody KafkaMsg kafkaMsg)
	{
		kafkaProducerCom.sendMsg(kafkaMsg.getTopic(), kafkaMsg.getKey(), kafkaMsg.getMsg());
		return "success";
	}
	
	@RequestMapping(value = "/sendMsgPref" ,method = RequestMethod.POST)
	public String sendMsgPref(@RequestBody KafkaMsg kafkaMsg,@RequestParam Integer times)
	{
		while(times>=0)
		{
			kafkaProducerCom.sendMsg(kafkaMsg.getTopic(), kafkaMsg.getKey(), kafkaMsg.getMsg());
			times--;
		}
		return "success";
	}
	
	@RequestMapping(value = "/schedule" ,method = RequestMethod.GET)
	public String scheduleJob(@RequestParam String jobName) throws SchedulerException
	{
		if(factory == null)
		{
		  factory = new StdSchedulerFactory();
		  
		  scheduler = factory.getScheduler();
		  scheduler.start();
		}
		
		
		
		String cron = "0 */" + 1 + " * * * ?";
		
		JobDetail jobDetail = JobBuilder
				.newJob(FlowDetailJob.class)
				.withIdentity(jobName).build();
		
		CronTrigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(jobName)
				.withSchedule(CronScheduleBuilder.cronSchedule(cron))
				.build();
		
		jobDetail.getJobDataMap().put("jobName", jobName);
		
		scheduler.scheduleJob(jobDetail,trigger);
		
		
		
		return "success";
	}
	
	@RequestMapping(value = "/upload.do",method = RequestMethod.GET)  
    public User upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {  
  
        System.out.println("开始");  
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();  
//        String fileName = new Date().getTime()+".jpg";  
        System.out.println(path);  
        File dir = new File(path);
        File targetFile = new File(path, fileName);  
        if(!dir.exists()){  
        	dir.mkdirs();  
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        //model.addAttribute("fileUrl", request.getContextPath()+"/upload/"+fileName);  
  
        User user1 = new User();
		user1.setUserName("liuwei");
		user1.setAge(23);
		
        return user1;  
    }  

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

}

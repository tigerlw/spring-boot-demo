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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
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
import com.alibaba.fastjson.TypeReference;

@RestController
public class ApiController implements ApplicationContextAware,InitializingBean{
	
	private ApplicationContext applicationContext;
	
	private SchedulerFactory factory;
	
	private Scheduler scheduler;
	
	@Autowired
    private Environment env;
	
	/*
	@Autowired
    @Qualifier("dataSourceS")
    private DataSource dataSource;*/
	
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
		
		System.out.println("getUser");
		
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
	
	@RequestMapping(value = "/testSentinel" ,method = RequestMethod.GET)
	public String testSentinel(@RequestParam String msg)
	{
		//ContextUtil.enter("HelloWorld", msg);
		/*try (Entry entry = SphU.entry("HelloWorld")) {
	            // 被保护的逻辑
	            System.out.println("hello world");
		} catch (BlockException ex) {
	            // 处理被流控的逻辑
		    System.out.println("blocked!");
		}*/
		
		//ContextUtil.exit();
		  
		return "success";
	}
	
	@RequestMapping(value = "/testSentinelAlone" ,method = RequestMethod.GET)
	public String testSentinelAlone(@RequestParam String msg)
	{
		//ContextUtil.enter("HelloWorld", msg);
		/*try (Entry entry = SphU.entry("HelloWorld")) {
	            // 被保护的逻辑
	            System.out.println("hello world");
		} catch (BlockException ex) {
	            // 处理被流控的逻辑
		    System.out.println("blocked!");
		}*/
		
		//ContextUtil.exit();
		  
		return "success";
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		//initFlowRules();
		//loadRules();
		loadRules2();
	}
	
	private static void initFlowRules(){
	    List<FlowRule> rules = new ArrayList<>();
	    FlowRule rule = new FlowRule();
	    rule.setResource("HelloWorld");
	    
	    
	    rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
	    // Set limit QPS to 20.
	    rule.setCount(3);
	    rules.add(rule);
	    FlowRuleManager.loadRules(rules);
	}
	
	private static void loadRules() {

        final String remoteAddress = "127.0.0.1:2181";
        final String path = "/Sentinel-Demo/SYSTEM-CODE-DEMO-FLOW";

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ZookeeperDataSource<>(remoteAddress, path,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());


    }
	
	private  void loadRules2() {
        // Set up basic information, only for demo purpose. You may adjust them based on your actual environment.
        // For more information, please refer https://github.com/ctripcorp/apollo
		
		int type = Integer.valueOf(env.getProperty("sentinel.server.type")) ;
		
        String appId = "sentinel-demo";
        String apolloMetaServerAddress = "http://10.1.77.106:8080";
        System.setProperty("app.id", appId);
        System.setProperty("apollo.meta", apolloMetaServerAddress);

        String namespaceName = "application";
        String flowRuleKey = "flowRules";
        // It's better to provide a meaningful default value.
        String defaultFlowRules = "[]";

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(namespaceName,
            flowRuleKey, defaultFlowRules, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        ClusterFlowRuleManager.setPropertySupplier(namespace ->flowRuleDataSource.getProperty());
        

		if (ClusterStateManager.CLUSTER_SERVER == type) 
		{
			ClusterStateManager.applyState(ClusterStateManager.CLUSTER_SERVER);
			
		} else {
			ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
			initClientConfigProperty(namespaceName);
			initClientServerAssignProperty(namespaceName);
		}
        
        
       
        
    }
	
	private static void initClientConfigProperty(String namespaceName) {
       /* ReadableDataSource<String, ClusterClientConfig> clientConfigDs = new NacosDataSource<>(remoteAddress, groupId,
            configDataId, source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {}));*/
		
		String clientKey = "clientKey";
        
        ReadableDataSource<String, ClusterClientConfig> clientConfigDs =new ApolloDataSource<>(namespaceName,
        		clientKey, "[]", source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {
                }));
        ClusterClientConfigManager.registerClientConfigProperty(clientConfigDs.getProperty());
    }
	
	private static void initClientServerAssignProperty(String namespaceName) {
        // Cluster map format:
        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
        // machineId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)
        /*ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new NacosDataSource<>(remoteAddress, groupId,
            clusterMapDataId, source -> {
            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {});
            return Optional.ofNullable(groupList)
                .flatMap(this::extractClientAssignment)
                .orElse(null);
        });*/
		
		String clientServerKey = "clientServerKey";
        
		ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new ApolloDataSource<>(namespaceName,
				clientServerKey, "[]", source -> JSON.parseObject(source, new TypeReference<ClusterClientAssignConfig>() {
                }));
        
        
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignDs.getProperty());
    }

}

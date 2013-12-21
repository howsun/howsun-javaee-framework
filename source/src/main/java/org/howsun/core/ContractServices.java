/**
 * ========================================================
 * 日 期：2012-3-21 下午2:23:51
 * 作 者：张纪豪
 * 版 本：3.0
 * ========================================================
 * 修订日期                        修订人                     描述
 *
 */
package org.howsun.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import org.howsun.dcs.ContractService;
import org.howsun.log.Log;
import org.howsun.log.LogFactory;
import org.howsun.util.Asserts;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.StringUtils;

/**
 * 功能描述：
 *
 * @author 张纪豪(zhangjihao@sohu.com)
 * @version 3.0
 */
@Singleton
public class ContractServices {

	/**
	 * 容器缓存
	 */
	private final static Map<String, ApplicationContext> CTX = new HashMap<String, ApplicationContext>();

	/**
	 * 服务接口
	 */
	private final static Map<Class<?>, ContractServiceBeanDescription> CONTRACT_SERVICES = new HashMap<Class<?>, ContractServiceBeanDescription>();

	private final static Log log = LogFactory.getLog(ContractServices.class);

	private final static String PATH = "file:%s%s/%s-sc.xml";//file:/opt/hjf/baikemy_core/core-sc.xml

	/**
	 * 注入模块
	 * @param modules
	 */
	public void setModules(Map<String, String> modules) {

		if(modules == null || modules.size() == 0){
			return;
		}

		for(Map.Entry<String, String> module : modules.entrySet()){
			String module_key = module.getKey();
			try {
				if(CTX.get(module_key) != null){
					continue;
				}
				FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext(String.format(PATH, Howsun.CONFIG_PREFIX, module.getValue(), module.getKey()));
				CTX.put(module_key, fileSystemXmlApplicationContext);

				ConfigurableListableBeanFactory configurableListableBeanFactory = fileSystemXmlApplicationContext.getBeanFactory();
				Map<String, Object>  contractServiceBeans = configurableListableBeanFactory.getBeansWithAnnotation(ContractService.class);
				for(String contractServiceBean : contractServiceBeans.keySet()){
					BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(contractServiceBean);
					Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
					Class<?>[] interfaces = beanClass.getInterfaces();
					for(Class<?> _interface : interfaces){
						ContractService contractServiceInterface = _interface.getAnnotation(ContractService.class);
						if(contractServiceInterface != null){
							ContractServiceBeanDescription beanDescription = CONTRACT_SERVICES.get(_interface);
							if(beanDescription == null){
								beanDescription = new ContractServices.ContractServiceBeanDescription(module_key);
							}
							beanDescription.addBeanName(contractServiceBean);
							CONTRACT_SERVICES.put(_interface, beanDescription);
							break;
						}
					}
				}
			}
			catch(ClassNotFoundException e){
				log.warn(String.format("初始化%s容器出错：", module_key), e);
			}
			catch (Exception e) {
				log.warn(String.format("加载%s容器失败：", module_key), e);
			}
		}
	}

	public ApplicationContext getApplicationContext(String moduleName){
		return CTX.get(moduleName);
	}
	
	
	/**
	 *
	 * @param clazz Bean的类型，一般为其接口类
	 * @return T
	 */
	public <T> T getContractService(Class<T> contractServiceInterface){
		return getContractService(contractServiceInterface, null);
	}
	
	/**
	 * 获取Bean对象
	 * @param beanId beanId
	 * @param clazz  Bean的类型，一般为其接口类
	 * @param context 所属模块或容器
	 * @return
	 */
	public <T> T getContractService(Class<T> contractServiceInterface, String beanName){

		T t = null;

		ContractServiceBeanDescription contractServiceBeanDescription = CONTRACT_SERVICES.get(contractServiceInterface);
		Asserts.notNull(contractServiceBeanDescription, String.format("在所有容器里未找到名为“%s”类型的bean对象", contractServiceInterface.getName()));

		String context = contractServiceBeanDescription.getModule();
		ApplicationContext applicationContext = CTX.get(context);
		Asserts.notNull(applicationContext, String.format("未发现“%s”容器", context));

		if(contractServiceBeanDescription.getBeanNames().size() > 1){
			Asserts.isTrue(beanName != null, String.format("接口名'%s'在多个服务中存在，需要使用BeanName限定", contractServiceInterface.getName()));
			t = applicationContext.getBean(beanName, contractServiceInterface);
		}
		else{
			Map<String, T> beans = applicationContext.getBeansOfType(contractServiceInterface);
			int size = beans.size();
			if(size == 1){
				t = beans.entrySet().iterator().next().getValue();
			}
			else if(size > 1){
				
				if(beanName == null){
					beanName = StringUtils.uncapitalize(contractServiceInterface.getSimpleName());
				}
				
				t = beans.get(beanName);
			}
		}

		Asserts.notNull(t == null, String.format("在“%s”容器里未能找到“%s”服务接口实例，请查检服务是否注入，或指定BeanName重试", context, contractServiceInterface.getName()));

		return t;
	}

	

	/**
	 * 功能描述：Bean
	 *
	 * @author 张纪豪(zjh@baikemy.com)
	 * @version 3.0
	 */
	static class ContractServiceBeanDescription{
		private Set<String> beanNames = new HashSet<String>(1);

		private String module;

		/////////////////////////////////////////////////////////////////////////////////////////
		public ContractServiceBeanDescription() {
			super();
		}
		public ContractServiceBeanDescription(String module) {
			super();
			this.module = module;
		}
		public ContractServiceBeanDescription(String beanName, String module) {
			super();
			this.beanNames.add(beanName);
			this.module = module;
		}

		/////////////////////////////////////////////////////////////////////////////////////////
		public Set<String> getBeanNames() {
			return beanNames;
		}
		public void setBeanNames(Set<String> beanNames) {
			this.beanNames = beanNames;
		}
		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
		public void addBeanName(String beanName){
			this.beanNames.add(beanName);
		}


	}

}

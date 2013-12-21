/**
 * 版本修订记录
 * 创建：2013-3-30
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.core;

import java.util.Map;

import org.howsun.dcs.ContractService;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-3-30
 *
 */
public class ContractServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext("R:/opt/hjf/chinaot_core/core-sc.xml");
		ConfigurableListableBeanFactory configurableListableBeanFactory = fileSystemXmlApplicationContext.getBeanFactory();
		Map<String, Object>  contractServiceBeans = configurableListableBeanFactory.getBeansWithAnnotation(ContractService.class);
		for(Map.Entry<String, Object> contractServiceBean : contractServiceBeans.entrySet()){
			System.out.println(contractServiceBean.getKey() + "\t" + contractServiceBean.getValue());
		}
	}

}

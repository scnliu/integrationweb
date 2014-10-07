/**
 * 
 */
package net.linybin7.core.web.util;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author Administrator
 * 
 */
public class SpringBeanFactory {
	/**
	 * spring bean工厂类
	 */
	private static BeanFactory beanFactory;

	/**
	 * 获得spring容器中的对象;
	 * 
	 * @param beanName
	 *            对象名称
	 * @return Spring容器中获得指定名称的对象
	 */
	public static Object getBean(String beanName) {
		if (beanFactory == null) {
			// Resource resource = new FileSystemResource(
			// "config/transportContext.xml");
			// beanFactory = new XmlBeanFactory(resource);
			System.err.println("=Spring Context not inited yet!!!=");
			return null;
		} else
			return beanFactory.getBean(beanName);
	}

	/**
	 * @return the beanFactory
	 */
	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}

	/**
	 * @param beanFactory
	 *            the beanFactory to set
	 */
	public static void setBeanFactory(BeanFactory beanFactory) {
		SpringBeanFactory.beanFactory = beanFactory;
	}

}

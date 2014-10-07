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
	 * spring bean������
	 */
	private static BeanFactory beanFactory;

	/**
	 * ���spring�����еĶ���;
	 * 
	 * @param beanName
	 *            ��������
	 * @return Spring�����л��ָ�����ƵĶ���
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

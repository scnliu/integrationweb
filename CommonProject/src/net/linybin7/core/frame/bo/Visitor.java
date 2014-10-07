package net.linybin7.core.frame.bo;

import java.util.List;
import java.util.Map;

import net.linybin7.core.util.Constants;


/**
 * ������,����Session���������Ϣ
 * 
 * 
 */
public class Visitor {

	/**
	 * ��ǰ�û�
	 */
	private User user;

	/**
	 * �û�Ȩ��
	 */
	private Map<String, String> purviews;

	/**
	 * ���Ի�����
	 */
	private Map<String, String> individuations;

	/**
	 * �û����ڲ����������¼�����
	 */
	private List<Org> orgs;

	/**
	 * ����
	 */
	private String topic;

	/**
	 * ��ҳ
	 */
	private String indexPage;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Map<String, String> getPurviews() {
		return purviews;
	}

	public void setPurviews(Map<String, String> purviews) {
		this.purviews = purviews;
	}

	public Map<String, String> getIndividuations() {
		return individuations;
	}

	public void setIndividuations(Map<String, String> individuations) {
		this.individuations = individuations;
		this.topic = individuations.get(Constants.INDIVIDUATION_TOPIC);
		if (this.topic == null) {
			this.topic = Constants.INDIVIDUATION_TOPIC_DEFAULT;
		}
	}

	public List<Org> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Org> orgs) {
		this.orgs = orgs;
	}

	public String getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(String indexPage) {
		this.indexPage = indexPage;
	}

}

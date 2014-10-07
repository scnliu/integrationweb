package net.linybin7.core.frame.org.cmd;


public class ZTreeCmd {
	/**{ id:1, pId:0, name:"ÀÊ“‚π¥—° 1", open:true},**/
	private String id;
	private String pId;
	private String name;
	private String title;
	private boolean open;
	private boolean checked;
	private boolean chkDisabled;
	private boolean IsParent;
	private Integer urlType;
	private String htmlUrl;
	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public ZTreeCmd() {
		super();
	}
	
	public ZTreeCmd(String id) {
		super();
		this.id = id;
	}

	public ZTreeCmd(String id, String pId, String name, boolean open,
			boolean checked,String title,Integer urlType,String url) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
		this.checked = checked;
		this.title = title;
		this.urlType=urlType;
		this.htmlUrl=url;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZTreeCmd other = (ZTreeCmd) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setIsParent(boolean isParent) {
		IsParent = isParent;
	}

	public boolean isIsParent() {
		return IsParent;
	}

	public void setUrlType(Integer urlType) {
		this.urlType = urlType;
	}

	public Integer getUrlType() {
		return urlType;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}
}

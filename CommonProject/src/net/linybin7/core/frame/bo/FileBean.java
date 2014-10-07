/**
 * 
 */
package net.linybin7.core.frame.bo;

/**
 * @author hezheng 2011-8-12 ÉÏÎç10:20:43
 */
public class FileBean {
	private String filename;
	private Long filesize;
	private String createdate;
	private String filepath;
	private Long sdate;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Long getSdate() {
		return sdate;
	}

	public void setSdate(Long sdate) {
		this.sdate = sdate;
	}

}

package net.linybin7.core.web.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

public class HttpUploader {
	/**
	 * 缓冲区大小，默认：10kb
	 */
	private int sizeThreshold = 1024 * 10;

	/**
	 * 设置最大文件大小:默认4M
	 */
	private long sizeMax = 1024 * 1024 * 4;

	/**
	 * 文件上传的目录
	 */
	private String uploadDir = System.getProperty("java.io.tmpdir");

	private DiskFileItemFactory dsiFactory;

	public HttpUploader() {
		this.init();
	}

	public HttpUploader(String uploadDir) {
		this.uploadDir = uploadDir;
		this.init();
	}

	public HttpUploader(int sizeThreshold, String uploadDir) {
		this.sizeThreshold = sizeThreshold;
		this.uploadDir = uploadDir;
		this.init();
	}

	private void init() {
		dsiFactory = new DiskFileItemFactory();
		dsiFactory.setSizeThreshold(sizeThreshold);
		File upDir = new File(uploadDir);
		upDir.mkdirs();
		dsiFactory.setRepository(upDir);
	}

	public FormData uploadFile(HttpServletRequest req, ProgressReport report) throws Exception {

		FileUpload fu = null;
		if (report != null) {
			fu = new ProgressFileUpload(dsiFactory, report);
		} else {
			fu = new FileUpload(dsiFactory);
		}

		fu.setSizeMax(sizeMax);
		FormData fdata = new FormData();
		List fileItems = fu.parseRequest(new ServletRequestContext(req));
		Iterator it = fileItems.iterator();

		// 依次处理每一个文件：
		while (it.hasNext()) {
			FileItem fi = (FileItem) it.next();// Mozilla
			String fieldName = fi.getFieldName();
			String fileName = fi.getName();

			if (!fi.isFormField() && fileName != null && fileName.trim().length() > 0) {
				int lindex = fileName.lastIndexOf("\\");
				if (lindex < 0) {
					lindex = fileName.lastIndexOf("//");
				}
				fileName = fileName.substring(lindex + 1);

				File upFile = new File(uploadDir, new File(fileName).getName());
				// 写到文件
				fi.write(upFile);
				fi.delete();
				fdata.putField(fieldName, upFile);
			} else {
				String value = fi.getString("GBK");
				fdata.putField(fieldName, value);
			}
		}
		return fdata;
	}

	public long getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(long sizeMax) {
		this.sizeMax = sizeMax;
	}

	class ProgressFileUpload extends FileUpload {
		ProgressReport report;

		public ProgressFileUpload() {
			super();
		}

		public ProgressFileUpload(DiskFileItemFactory fileItemFactory, ProgressReport report) {
			super(fileItemFactory);
			this.report = report;
		}

		@Override
		public List parseRequest(RequestContext ctx) throws FileUploadException {
			if (ctx == null) {
				throw new NullPointerException("ctx parameter");
			}

			ArrayList items = new ArrayList();
			String contentType = ctx.getContentType();

			if ((null == contentType) || (!contentType.toLowerCase().startsWith(MULTIPART))) {
				throw new InvalidContentTypeException("the request doesn't contain a "
						+ MULTIPART_FORM_DATA + " or " + MULTIPART_MIXED
						+ " stream, content type header is " + contentType);
			}
			int requestSize = ctx.getContentLength();

			if (requestSize == -1) {
				throw new UnknownSizeException(
						"the request was rejected because its size is unknown");
			}

			if (sizeMax >= 0 && requestSize > sizeMax) {
				throw new SizeLimitExceededException("the request was rejected because its size ("
						+ requestSize + ") exceeds the configured maximum (" + sizeMax + ")",
						requestSize, sizeMax);
			}

			String charEncoding = getHeaderEncoding();

			if (charEncoding == null) {
				charEncoding = ctx.getCharacterEncoding();
			}

			try {
				byte[] boundary = getBoundary(contentType);
				if (boundary == null) {
					throw new FileUploadException("the request was rejected because "
							+ "no multipart boundary was found");
				}

				InputStream input = ctx.getInputStream();

				ProgressInputStream uis = new ProgressInputStream(input, this.report);

				MultipartStream multi = new MultipartStream(uis, boundary);
				multi.setHeaderEncoding(charEncoding);

				boolean nextPart = multi.skipPreamble();
				while (nextPart) {
					Map headers = parseHeaders(multi.readHeaders());
					String fieldName = getFieldName(headers);
					if (fieldName != null) {
						String subContentType = getHeader(headers, CONTENT_TYPE);
						if (subContentType != null
								&& subContentType.toLowerCase().startsWith(MULTIPART_MIXED)) {
							// Multiple files.
							byte[] subBoundary = getBoundary(subContentType);
							multi.setBoundary(subBoundary);
							boolean nextSubPart = multi.skipPreamble();
							while (nextSubPart) {
								headers = parseHeaders(multi.readHeaders());
								if (getFileName(headers) != null) {
									FileItem item = createItem(headers, false);
									OutputStream os = item.getOutputStream();
									try {
										multi.readBodyData(os);
									} finally {
										os.close();
									}
									items.add(item);
								} else {
									// Ignore anything but files inside
									// multipart/mixed.
									multi.discardBodyData();
								}
								nextSubPart = multi.readBoundary();
							}
							multi.setBoundary(boundary);
						} else {
							String fileName = getFileName(headers);
							if (fileName != null)
								this.report.setFile(fileName);
							FileItem item = createItem(headers, fileName == null);
							OutputStream os = item.getOutputStream();
							try {
								multi.readBodyData(os);
							} finally {
								os.close();
							}
							items.add(item);
						}
					} else {
						// Skip this part.
						multi.discardBodyData();
					}
					nextPart = multi.readBoundary();
				}
			} catch (IOException e) {
				throw new FileUploadException("Processing of " + MULTIPART_FORM_DATA
						+ " request failed. " + e.getMessage());
			}

			return items;
		}
	}

}

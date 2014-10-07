package net.linybin7.common.uploader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.UnknownSizeException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

public class ProgressInfoFileUpload extends FileUpload {
	
	/**
	 * 设置最大文件大小:默认100M
	 */
	private long sizeMax = 1024 * 1024 * 100;
	
	ProgressReport report;

	public ProgressInfoFileUpload() {
		super();
	}

	public ProgressInfoFileUpload(DiskFileItemFactory fileItemFactory,
			ProgressReport report) {
		super(fileItemFactory);
		this.report = report;
	}

	@Override
	public List parseRequest(RequestContext ctx) throws FileUploadException {
		if (ctx == null) {
			throw new NullPointerException("ctx parameter");
		}

		ArrayList<FileItem> items = new ArrayList<FileItem>();
		String contentType = ctx.getContentType();

		if ((null == contentType)
				|| (!contentType.toLowerCase().startsWith(MULTIPART))) {
			throw new InvalidContentTypeException(
					"the request doesn't contain a " + MULTIPART_FORM_DATA
							+ " or " + MULTIPART_MIXED
							+ " stream, content type header is "
							+ contentType);
		}
		int requestSize = ctx.getContentLength();

		if (requestSize == -1) {
			throw new UnknownSizeException(
					"the request was rejected because its size is unknown");
		}

		if (sizeMax >= 0 && requestSize > sizeMax) {
			throw new SizeLimitExceededException(
					"the request was rejected because its size ("
							+ requestSize
							+ ") exceeds the configured maximum ("
							+ sizeMax + ")", requestSize, sizeMax);
		}

		String charEncoding = getHeaderEncoding();

		if (charEncoding == null) {
			charEncoding = ctx.getCharacterEncoding();
		}

		try {
			byte[] boundary = getBoundary(contentType);
			if (boundary == null) {
				throw new FileUploadException(
						"the request was rejected because "
								+ "no multipart boundary was found");
			}

			InputStream input = ctx.getInputStream();

			ProgressInputStream uis = new ProgressInputStream(input,
					this.report);

			MultipartStream multi = new MultipartStream(uis, boundary);
			multi.setHeaderEncoding(charEncoding);

			boolean nextPart = multi.skipPreamble();
			while (nextPart) {
				Map headers = parseHeaders(multi.readHeaders());
				String fieldName = getFieldName(headers);
				if (fieldName != null) {
					String subContentType = getHeader(headers, CONTENT_TYPE);
					if (subContentType != null
							&& subContentType.toLowerCase().startsWith(
									MULTIPART_MIXED)) {
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
						FileItem item = createItem(headers,
								fileName == null);
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
			throw new FileUploadException("Processing of "
					+ MULTIPART_FORM_DATA + " request failed. "
					+ e.getMessage());
		}

		return items;
	}
}

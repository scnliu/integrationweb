package net.linybin7.common.widget;

import java.util.ArrayList;
import java.util.List;

import net.linybin7.common.component.table.Pagination;

import org.json.JSONArray;
import org.json.JSONObject;

import common.Logger;

public class Grid {

	public Grid() {
		super();
		this.id = "grid" + System.currentTimeMillis();
	}

	public static final Logger logger = Logger.getLogger(Grid.class);
	List gridData = new ArrayList();
	String id;

	String style = "width: 100%; height: 100%;";

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getId() {
		return id;
	}

	public List<Object> getGridData() {
		return gridData;
	}

	public void setGridData(List gridData) {
		this.gridData = gridData;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFields(List<GridField> fields) {
		this.fields = fields;
	}

	Pagination pagination = new Pagination();

	List<GridField> fields = new ArrayList<GridField>();

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getLayout() {
		JSONArray jsonArr = new JSONArray();
		for (GridField f : this.fields) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("field", f.getField());
			jsonObj.put("name", f.getName());
			jsonObj.put("width", f.getWidth());
			jsonArr.put(jsonObj);
		}
		return jsonArr.toString();
	}

	public String getFields() {
		String fieldStr = "";
		for (GridField f : this.fields) {
			if (fieldStr.trim().length() == 0)
				fieldStr += f.getField();
			else
				fieldStr += "," + f.getField();
		}
		return fieldStr;
	}

	public String getCsvData() {
		StringBuffer sb = new StringBuffer();
		for (Object obj : this.gridData) {
			if (obj instanceof CsvData) {
				CsvData csv = (CsvData) obj;
				String csvData = csv.toCsvData();
				if (sb.toString().trim().length() == 0) {
					sb.append(csvData);
				} else {
					sb.append("\\n");
					sb.append(csvData);
				}
			} else {
				logger.warn(obj + " 无法转换成Csv数据！");
			}
		}
		return sb.toString();
	}
}

package net.linybin7.common.component.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.linybin7.common.util.Constant;
import net.linybin7.common.util.OgnlUtil;



public class TableModel<T> {
	/**
	 * 表列信息列表
	 */
	private List<Column> columns = new ArrayList<Column>();

	/**
	 * 当前页数据
	 */
	private List data;

	/**
	 * 是否有序号列
	 */
	private boolean hasIndex = true;
	
	/**
	 * 是否显示序号列和分页
	 */
	private boolean hiddenIndex = true;

	/**
	 * 是否分页显示
	 */
	private boolean paging = true;

	private boolean displayCount = true;

	/**
	 * 分页对象
	 */
	private Paging page = new Paging();

	/**
	 * 当前行对象
	 */
	private T row;

	/**
	 * 当前行上下文对象
	 */
	private Map context;

	public TableModel() {
		page.setCurrentPageProp("tableModel.page.currentPage");
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public boolean isPaging() {
		return paging;
	}

	public void setPaging(boolean paging) {
		this.paging = paging;
	}

	public Paging getPage() {
		return page;
	}

	public T getRow() {
		return row;
	}

	public void setRow(T row) {
		this.row = row;
		context(row);
	}

	public boolean isHasIndex() {
		return hasIndex;
	}

	public void setHasIndex(boolean hasIndex) {
		this.hasIndex = hasIndex;
	}

	public boolean isDisplayCount() {
		return displayCount;
	}

	public void setDisplayCount(boolean displayCount) {
		this.displayCount = displayCount;
	}

	/**
	 * 设置当前行上下文对象
	 * 
	 * @param row
	 */
	private void context(T row) {
		try {
			this.context = OgnlUtil.getInstance().getContext(row);
		} catch (Exception e) {
		}
	}

	/**
	 * 获得当前行对应属性
	 * 
	 * @param prop
	 * @return
	 */
	public Object getProp(String props) {
		try {
			int index = props.indexOf(Constant.TABLE_LITERAL);
			if (index >= 0) {
				String value = props.substring(index
						+ Constant.TABLE_LITERAL.length());
				return value = parse(value);
			}

			String[] propsArr = props.split(",");
			StringBuilder builder = new StringBuilder();
			for (String prop : propsArr) {
				Object value = OgnlUtil.getInstance().getValue(prop, context,
						row);
				builder.append(value == null ? "" : value);
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据当前行对象，解释表达式
	 * 
	 * @param exp
	 * @return
	 */
	public String parse(String exp) {
		try {
			return OgnlUtil.parse(exp, row);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public static void main(String[] args) throws Exception {
		TableModel<String[]> tm = new TableModel<String[]>();
		String[] rowArray = new String[] { "hello", "me" };
		tm.setRow(rowArray);
		System.out.println(tm.getProp("[0]"));
	}

	public boolean isHiddenIndex() {
		return hiddenIndex;
	}

	public void setHiddenIndex(boolean hiddenIndex) {
		this.hiddenIndex = hiddenIndex;
	}
}

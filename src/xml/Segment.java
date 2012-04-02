package xml;


/**
 * 合并审批－－ 定义环节的显示模版中业务表单与数据库字段的对应关系
 * @author yangwm in Jul 2, 2008 2:17:46 PM
 */
public class Segment {
	private String property;			// 业务表单属性名称
	private String column;				// 数据库字段名称
	
	public Segment() {
	}
	public Segment(String property, String column) {
		this.property = property;
		this.column = column;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}

	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Segment{");
		str.append(property);
		str.append(",");
		str.append(column);
		str.append("}");
		return str.toString();
	}
}

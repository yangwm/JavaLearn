package xml;

import java.util.List;

/**
 * 定义环节的显示模版
 * 
 * @author yangwm in May 9, 2009 4:27:16 PM
 */
public class Templet {
	private long stepId;			// 环节stepId
	private String columns;			// 附加的查询字段
	private String tables;			// 附加的查询表
	private String condition;		// 附加的查询条件
	private String html;			// 界面显示的附加的自定义html代码
	private List<Segment> mapping; 	// Segment列表
	
	public Templet() {
		
	}
	public Templet(long stepId) {
		this.stepId = stepId;
	}

	// getter setter 
	
	public long getStepId() {
		return stepId;
	}
	public void setStepId(long stepId) {
		this.stepId = stepId;
	}
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getTables() {
		return tables;
	}
	public void setTables(String tables) {
		this.tables = tables;
	}

	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

	public List<Segment> getMapping() {
		return mapping;
	}
	public void setMapping(List<Segment> mapping) {
		this.mapping = mapping;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Templet{");
		str.append(stepId);
		str.append(",");
		str.append(columns);
		str.append(",");
		str.append(tables);
		str.append(",");
		str.append(condition);
		str.append(",");
		str.append(html);
		str.append(",");
		str.append(mapping);
		str.append("}");
		return str.toString();
	}
}

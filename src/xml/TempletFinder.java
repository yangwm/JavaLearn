package xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import util.PathUtil;

/**
 * 合并审批－－ 定义环节的显示模版的查找类
 * @author yangwm in Jul 2, 2008 2:51:52 PM
 */
public class TempletFinder {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(TempletFinder.class);
	

	// 返回一个Templet
	public static Templet getTemplet(long workflowId, long stepId) {
		logger.debug("workflowId=" + workflowId + ", stepId=" + stepId);
		String templetFileName = "combinedTasks_" + workflowId + ".xml";		// 模版文件名
		String templetNodeName = "/workflow/templet[@stepId=" + stepId + "]";	// 模版指定节点
		String templetDefaultNodeName = "/workflow/templet[@stepId=0]";			// 模版默认节点
		Templet templet = null;													// 定义环节的显示模版对象
		
		try {			
			// 定位到模版文件相对全路径名。
			String relativeTempletFileName = PathUtil.getXmlPath() + templetFileName;
			logger.debug("relativeTempletFileName=" + relativeTempletFileName);

			InputStream is = Templet.class.getResourceAsStream("/xml/" + templetFileName);
			
			// 读取该模版文件， 如果没有该模版文件， 读取异常直接放回null。
			Document document = new SAXReader().read( is );
			//logger.debug("document.asXML()=" + document.asXML());
		
			// 得到指定stepId的templet节点(环节配置信息)
    		Node node = document.selectSingleNode( templetNodeName );

    		/*
    		 * 是否有指定stepId环节配置信息， 如果有，设置为指定stepId环节配置信息;
    		 * 否则是否有默认stepId环节配置信息， 如果有，设置为默认stepId环节配置信息;
    		 * 否则不设置。
    		 */
    		if (null != node) {
    			// 加载指定stepId的templet节点（环节配置信息）
    			templet = new Templet(stepId);
    			
    			// 把加载好的node信息，设置到templet中。
    			setTemplet(templet, node); 
    		} else {
    			// 得到默认stepId的templet节点(环节配置信息)
    			node = document.selectSingleNode( templetDefaultNodeName );

    			if (null != node) {
    				// 加载默认stepId的templet节点（环节配置信息）
    				templet = new Templet(0);	
    				
    				// 把加载好的node信息，设置到templet中。
        			setTemplet(templet, node);
    			} 
    		}

		} catch (DocumentException e) {
			logger.error("workflowId=" + workflowId + ", stepId=" + stepId);
			logger.error("TempletFinder DocumentException! ", e);
		} catch (Exception e) {
			logger.error("workflowId=" + workflowId + ", stepId=" + stepId);
			logger.error("TempletFinder error! ", e);
		}

		return templet;
	}
	
	/**
	 * 把node信息，设置到templet中。
	 * 如果node为null将不进行设置。
	 * @param templet 	定义环节的显示模版对象
	 * @param node		templet节点(环节配置信息)
	 * @return 			返回传入的templet
	 */
	public static Templet setTemplet(Templet templet, Node node) {
		if (null != node) {
			// 合并审批附加的查询字段
    		Node columns = node.selectSingleNode("columns");
    		if (null != columns) {
    			templet.setColumns( columns.getText() );
    		}
    		
    		// 合并审批附加的查询表
    		Node tables = node.selectSingleNode("tables");
    		if (null != tables) {
    			templet.setTables( tables.getText() );
    		}
    		
    		// 合并审批附加的查询条件
    		Node condition = node.selectSingleNode("condition");
    		if (null != condition) {
    			templet.setCondition( condition.getText() );
    		}
    		
    		// 界面显示的附加的自定义html代码
    		Node html = node.selectSingleNode("html");
    		if (null != html) {
    			templet.setHtml( html.getText() );
    		}
    		
    		// 业务表单与数据库字段的对应关系列表
    		Node mapping = node.selectSingleNode("mapping");
    		List<Segment> segmentList = new ArrayList<Segment>();
    		if (null != mapping) {
    			List<?> segmentNodes = mapping.selectNodes("segment");
    			logger.debug("segmentNodes.size()" + segmentNodes.size());
    			for (Iterator<?> iter = segmentNodes.iterator(); iter.hasNext(); ) {
    				Element e = (Element)iter.next();
    				segmentList.add(
    					new Segment(e.attributeValue("property"), e.attributeValue("column"))
    				);
    			}
    			templet.setMapping(segmentList);
    		}
    		
		}

		return templet;
	}
	
	/**
	 * 合并审批－－ 定义环节的显示模版的查找类的测试类
	 * @author yangwm in Jul 3, 2008 10:32:45 AM
	 */
	public static class TempletFinderTester {
		public static void main(String[] args) {
			try {
				Templet templet = TempletFinder.getTemplet(1260, 2526);
				
				logger.debug("templet=" + templet);				
			} catch (Exception exp) {
				exp.printStackTrace();
			}
			
		}
	}

}

package org.howsun.util.jsptag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.howsun.log.Log;
import org.howsun.log.LogFactory;
import org.howsun.util.Ips;

/**
 * @author 张纪豪
 * @Date 2007-9-26
 * @version v3.0
 */
public class ToolsTag extends TagSupport {

	public static final long serialVersionUID = 1L;

	protected static final Log log = LogFactory.getLog(ToolsTag.class);


	public static String formatIp(Long ip) throws JspTagException {
		if(ip == null || ip < 1){
			return "";
		}
		return Ips.long2ip(ip);
	}

}

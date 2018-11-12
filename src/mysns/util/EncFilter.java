package mysns.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncFilter implements Filter{
	private String encoding;
	Logger logger = LoggerFactory.getLogger(EncFilter.class);
	
	public EncFilter() {
	}
	
    public void destroy() {
    }
    
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	}

    public void init(FilterConfig config) throws ServletException {
    	encoding = config.getInitParameter("charset");
        if(encoding == null)
        	encoding = "utf-8";
    }
}

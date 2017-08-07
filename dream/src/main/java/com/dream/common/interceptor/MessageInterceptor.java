package com.dream.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author gaogao
 *
 */
@Component
public class MessageInterceptor implements HandlerInterceptor {
	

	/** 
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在 
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在 
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返 
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。 
     */  
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    	/**
    	 * 共用信息可以放到session
    	 */
    /*	HttpSession session = request.getSession();
    	session.setAttribute("_tilte", "Dgao小站");
    	session.setAttribute("links", linkService.doSearchListByVO(new LinkVO()));
    	session.setAttribute("hots", articleService.doSearchHots());
    	PageInfo<CommentVO> page = commentService.doSearchPage(new CommentVO(), 1, 5);
    	session.setAttribute("comments", page.getList());
    	session.setAttribute("browseCounts", logService.doSearchCount(null));
    	session.setAttribute("commentCounts", page.getSize());
    	
        if(response.getStatus()==500){
            modelAndView.setViewName("/errorpage/500");
        }else if(response.getStatus()==404){
            //modelAndView.setViewName("/errorpage/404");
        }*/
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    	
	}
}

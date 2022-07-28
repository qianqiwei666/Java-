package com.qianqiwei.chainOfResponsibility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Demo01 {

    /**过滤链
     * 作用:过滤请求。
     * 原理:通过递归。
     */

    private static interface HttpServletRequest{
        public String getName();
        public void setName(String name);
    }

    private static interface HttpServletResponse{
        public String getName();
        public void setName(String name);
    }

    private static class ServletRequest implements HttpServletRequest{
        private String name;


        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name=name;
        }
    }

    private static class ServletResponse implements HttpServletResponse{
        private String name;


        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name=name;
        }
    }


    private static interface Filter{
        public void doFilter(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain);
    }

    private static interface FilterChain{
        public void doFilter(HttpServletRequest request,HttpServletResponse response);
    }


    private static class MyFilter01 implements Filter{

        @Override
        public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
            System.out.println(1);
            filterChain.doFilter(request,response);
            System.out.println(1);
        }
    }

    private static class MyFilter02 implements Filter{

        @Override
        public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
            System.out.println(2);
//            filterChain.doFilter(request,response);
            System.out.println(2);
        }
    }

    private static class MyFilter03 implements Filter{

        @Override
        public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
            System.out.println(3);
            filterChain.doFilter(request,response);
            System.out.println(3);
        }
    }



    //获取过滤链
    private static class MyFilterChain implements FilterChain{
        private List<Filter> filters=new ArrayList<>();
        private Iterator<Filter> filterIterator;
        public MyFilterChain addFilter(Filter filter){
            filters.add(filter);
            return this;
        }
        @Override
        public void doFilter(HttpServletRequest request, HttpServletResponse response) {
            //创建Filter迭代器
            if (filterIterator==null) this.filterIterator=filters.listIterator();
            //为response设置数据
            response.setName(request.getName());
            if (filterIterator.hasNext())filterIterator.next().doFilter(request,response,this);
        }
    }



    public static void main(String[] args) {
        MyFilterChain filterChain=new MyFilterChain();
        filterChain.addFilter(new MyFilter01()).addFilter(new MyFilter02()).addFilter(new MyFilter03());
        HttpServletRequest request=new ServletRequest();
        request.setName("刘德华");
        HttpServletResponse response=new ServletResponse();
        filterChain.doFilter(request,response);

    }


}

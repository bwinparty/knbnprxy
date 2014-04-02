/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.model;

import java.util.List;
import java.util.Map;

/**
 * Created by zyclonite on 02/04/14.
 */
public class V1Request {
    private String from;
    private List<String> select;
    private Map<String, String> where;
    private Map<String, String> with;
    private String find;
    private List<String> findin;
    private List<String> sort;
    private V1Request group;
    private Page page;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getSelect() {
        return select;
    }

    public void setSelect(List<String> select) {
        this.select = select;
    }

    public Map<String, String> getWhere() {
        return where;
    }

    public void setWhere(Map<String, String> where) {
        this.where = where;
    }

    public Map<String, String> getWith() {
        return with;
    }

    public void setWith(Map<String, String> with) {
        this.with = with;
    }

    public String getFind() {
        return find;
    }

    public void setFind(String find) {
        this.find = find;
    }

    public List<String> getFindin() {
        return findin;
    }

    public void setFindin(List<String> findin) {
        this.findin = findin;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public V1Request getGroup() {
        return group;
    }

    public void setGroup(V1Request group) {
        this.group = group;
    }

    public int getPageSize() {
        return page.size;
    }

    public void setPageSize(int size) {
        this.page.size = size;
    }

    public int getPageStart() {
        return page.start;
    }

    public void setPageStart(int start) {
        this.page.start = start;
    }

    private class Page {
        private int size;
        private int start;
    }
}

package com.yzz.dto;

public class Page {

	private Integer currentPage = 1;
	private Integer pageSize = 10;
	private String orderField;
	private String sort = "DESC";

	private Integer totalRecord;
	private Integer totalPage;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField == null ? null : orderField.trim();
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort == null ? null : sort.trim();
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		if (!totalRecord.equals(null)) {
			this.totalPage = (totalRecord % this.pageSize) == 0 ? (totalRecord / this.pageSize)
					: (totalRecord / this.pageSize) + 1;
		}
		this.totalRecord = totalRecord;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

}
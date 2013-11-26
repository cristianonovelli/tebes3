package it.enea.xlab.tebes.controllers.bean;

import it.enea.xlab.tebes.paging.PagingManager;

import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class FilterablePagedDataModel <T> extends DataModel {

	int pageSize;
	int rowIndex;
	DataPage<T> page;
	//Order orderBy;
	PagingManager pagingManager;
	//DetachedCriteria detachedCriteria;
	List<Criterion> restrictions;
	List<Order> ordersBy;
	Class<T> clazz;


	/**
	 * Not used in this class; data is fetched via a callback to the
	 * fetchData method rather than by explicitly assigning a list.
	 */
	@Override
	public void setWrappedData(Object o) {
		throw new UnsupportedOperationException("setWrappedData");
	}

	public FilterablePagedDataModel(int pageSize, List<Criterion> restrictions, List<Order> ordersBy,  PagingManager pagingManager, Class<T> clazz) {
		super();
		this.pageSize = pageSize;
		this.rowIndex = -1;
		this.page = null;
		this.pagingManager = pagingManager;
		this.restrictions = restrictions;
		this.clazz = clazz;
		this.ordersBy = ordersBy;
	}

	/*public FilterablePagedDatamodel(int pageSize,
    		DetachedCriteria detachedCriteria, Order orderBy, CrudManager dbManager
			) {
		super();
		this.pageSize = pageSize;
		this.rowIndex = -1;
		this.page = null;
		this.orderBy = orderBy;
		this.dbManager = dbManager;
		this.detachedCriteria = detachedCriteria;
	}*/

	@Override
	public int getRowIndex() {



		return rowIndex;
	}

	/**
	 * Specify what the "current row" within the dataset is. Note that
	 * the UIData component will repeatedly call this method followed
	 * by getRowData to obtain the objects to render in the table.
	 */
	@Override
	public void setRowIndex(int index) {

		rowIndex = index;


	}

	/**
	 * Return the total number of rows of data available (not just the
	 * number of rows in the current page!).
	 */
	@Override
	public int getRowCount() {
		return getPage().getDatasetSize();
	}


	/**
	 * Return a DataPage object; if one is not currently available then
	 * fetch one. Note that this doesn't ensure that the datapage
	 * returned includes the current rowIndex row; see getRowData.
	 */
	public DataPage<T> getPage() {
		//if (page != null)
			//return page;

		int rowIndex = getRowIndex();
		int startRow = rowIndex;
		if (rowIndex == -1) {
			// even when no row is selected, we still need a page
			// object so that we know the amount of data available.
			startRow = 0;
		}

		// invoke method on enclosing class
		page = fetchPage(startRow, pageSize);


		return page;
	}

	/**
	 * Return the object corresponding to the current rowIndex.
	 * If the DataPage object currently cached doesn't include that
	 * index then fetchPage is called to retrieve the appropriate page.
	 */
	@Override
	public Object getRowData(){
		if (rowIndex < 0) {
			throw new IllegalArgumentException(
					"Invalid rowIndex for PagedListDataModel; not within page");
		}

		// ensure page exists; if rowIndex is beyond dataset size, then 
		// we should still get back a DataPage object with the dataset size
		// in it...
		//if (page == null) {
			page = fetchPage(rowIndex, pageSize);
		//}

		int datasetSize = page.getDatasetSize();
		int startRow = page.getStartRow();
		int nRows = page.getData().size();
		int endRow = startRow + nRows;

		if (rowIndex >= datasetSize) {
			throw new IllegalArgumentException("Invalid rowIndex");
		}

		if (rowIndex < startRow) {
			page = fetchPage(rowIndex, pageSize);
			startRow = page.getStartRow();
		} else if (rowIndex >= endRow) {
			page = fetchPage(rowIndex, pageSize);
			startRow = page.getStartRow();
		}



		Object obj =  page.getData().get(rowIndex - startRow);

		DataModelListener[] list = this.getDataModelListeners();
		for (int i = 0; i < list.length; i++) {
			list[i].rowSelected(new DataModelEvent(this, rowIndex - startRow, obj));
		}

		return obj;
	}

	@Override
	public Object getWrappedData() {
		return page.getData();
	}

	/**
	 * Return true if the rowIndex value is currently set to a
	 * value that matches some element in the dataset. Note that
	 * it may match a row that is not in the currently cached 
	 * DataPage; if so then when getRowData is called the
	 * required DataPage will be fetched by calling fetchData.
	 */
	@Override
	public boolean isRowAvailable() {
		DataPage<T> page = getPage();
		if (page == null)
			return false;

		int rowIndex = getRowIndex();
		if (rowIndex < 0) {
			return false;
		} else if (rowIndex >= page.getDatasetSize()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Method which must be implemented in cooperation with the
	 * managed bean class to fetch data on demand.
	 */
	public DataPage<T> fetchPage(int startRow, int pageSize){

		List<T> data = pagingManager.findByCriteria(clazz, startRow, pageSize, restrictions, ordersBy);
		int dataSetSize = pagingManager.countByCriteria(this.clazz, restrictions);
		DataPage<T> dataPage = new DataPage<T>(dataSetSize, startRow, data);


		page = dataPage;


		return dataPage;
	}


}

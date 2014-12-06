package com.pipalapipapalapi.smartplaces.database;

public interface DAOListener<T> {

	public void onFinish(T t);

}

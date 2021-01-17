/**
 * DBCustomerServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.customer.service;

public interface DBCustomerServicePortType extends java.rmi.Remote {
    public java.lang.String searchPriceData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, double lowerPrice, double higherPrice) throws java.rmi.RemoteException;
    public java.lang.String deleteCustomerData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, int customerId) throws java.rmi.RemoteException;
    public java.lang.String updateCustomerData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, int customerId, java.lang.String values) throws java.rmi.RemoteException;
    public java.lang.String searchNameData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, java.lang.String customerName) throws java.rmi.RemoteException;
    public java.lang.String getCustomerData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, java.lang.String condition) throws java.rmi.RemoteException;
    public java.lang.String writeCustomerData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, java.lang.String values) throws java.rmi.RemoteException;
}

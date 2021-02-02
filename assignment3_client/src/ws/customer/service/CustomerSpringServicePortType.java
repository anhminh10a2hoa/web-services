/**
 * CustomerSpringServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.customer.service;

public interface CustomerSpringServicePortType extends java.rmi.Remote {
    public void addCustomer(ws.customer.data.xsd.Customer c) throws java.rmi.RemoteException;
    public int[] getListOfIds() throws java.rmi.RemoteException;
    public ws.customer.data.xsd.Customer getCustomerById(int id) throws java.rmi.RemoteException;
    public void setCustomer(ws.customer.data.xsd.Customer[] customer) throws java.rmi.RemoteException;
    public ws.customer.data.xsd.Customer[] getCustomersByPrivileged(boolean privileged) throws java.rmi.RemoteException;
    public ws.customer.data.xsd.Customer getLargestShoppingAmount() throws java.rmi.RemoteException;
}

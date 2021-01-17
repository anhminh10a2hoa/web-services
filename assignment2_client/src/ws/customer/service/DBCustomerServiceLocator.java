/**
 * DBCustomerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.customer.service;

public class DBCustomerServiceLocator extends org.apache.axis.client.Service implements ws.customer.service.DBCustomerService {

    public DBCustomerServiceLocator() {
    }


    public DBCustomerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DBCustomerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DBCustomerServiceHttpSoap11Endpoint
    private java.lang.String DBCustomerServiceHttpSoap11Endpoint_address = "http://localhost:8080/axis2/services/DBCustomerService.DBCustomerServiceHttpSoap11Endpoint/";

    public java.lang.String getDBCustomerServiceHttpSoap11EndpointAddress() {
        return DBCustomerServiceHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DBCustomerServiceHttpSoap11EndpointWSDDServiceName = "DBCustomerServiceHttpSoap11Endpoint";

    public java.lang.String getDBCustomerServiceHttpSoap11EndpointWSDDServiceName() {
        return DBCustomerServiceHttpSoap11EndpointWSDDServiceName;
    }

    public void setDBCustomerServiceHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        DBCustomerServiceHttpSoap11EndpointWSDDServiceName = name;
    }

    public ws.customer.service.DBCustomerServicePortType getDBCustomerServiceHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DBCustomerServiceHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDBCustomerServiceHttpSoap11Endpoint(endpoint);
    }

    public ws.customer.service.DBCustomerServicePortType getDBCustomerServiceHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ws.customer.service.DBCustomerServiceSoap11BindingStub _stub = new ws.customer.service.DBCustomerServiceSoap11BindingStub(portAddress, this);
            _stub.setPortName(getDBCustomerServiceHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDBCustomerServiceHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        DBCustomerServiceHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ws.customer.service.DBCustomerServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ws.customer.service.DBCustomerServiceSoap11BindingStub _stub = new ws.customer.service.DBCustomerServiceSoap11BindingStub(new java.net.URL(DBCustomerServiceHttpSoap11Endpoint_address), this);
                _stub.setPortName(getDBCustomerServiceHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("DBCustomerServiceHttpSoap11Endpoint".equals(inputPortName)) {
            return getDBCustomerServiceHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.customer.ws", "DBCustomerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.customer.ws", "DBCustomerServiceHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DBCustomerServiceHttpSoap11Endpoint".equals(portName)) {
            setDBCustomerServiceHttpSoap11EndpointEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

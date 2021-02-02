package ws.customer.service;

public class CustomerSpringServicePortTypeProxy implements ws.customer.service.CustomerSpringServicePortType {
  private String _endpoint = null;
  private ws.customer.service.CustomerSpringServicePortType customerSpringServicePortType = null;
  
  public CustomerSpringServicePortTypeProxy() {
    _initCustomerSpringServicePortTypeProxy();
  }
  
  public CustomerSpringServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initCustomerSpringServicePortTypeProxy();
  }
  
  private void _initCustomerSpringServicePortTypeProxy() {
    try {
      customerSpringServicePortType = (new ws.customer.service.CustomerSpringServiceLocator()).getCustomerSpringServiceHttpSoap11Endpoint();
      if (customerSpringServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)customerSpringServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)customerSpringServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (customerSpringServicePortType != null)
      ((javax.xml.rpc.Stub)customerSpringServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ws.customer.service.CustomerSpringServicePortType getCustomerSpringServicePortType() {
    if (customerSpringServicePortType == null)
      _initCustomerSpringServicePortTypeProxy();
    return customerSpringServicePortType;
  }
  
  public void addCustomer(ws.customer.data.xsd.Customer c) throws java.rmi.RemoteException{
    if (customerSpringServicePortType == null)
      _initCustomerSpringServicePortTypeProxy();
    customerSpringServicePortType.addCustomer(c);
  }
  
  public int[] getListOfIds() throws java.rmi.RemoteException{
    if (customerSpringServicePortType == null)
      _initCustomerSpringServicePortTypeProxy();
    return customerSpringServicePortType.getListOfIds();
  }
  
  public ws.customer.data.xsd.Customer getCustomerById(int id) throws java.rmi.RemoteException{
    if (customerSpringServicePortType == null)
      _initCustomerSpringServicePortTypeProxy();
    return customerSpringServicePortType.getCustomerById(id);
  }
  
  public void setCustomer(ws.customer.data.xsd.Customer[] customer) throws java.rmi.RemoteException{
    if (customerSpringServicePortType == null)
      _initCustomerSpringServicePortTypeProxy();
    customerSpringServicePortType.setCustomer(customer);
  }
  
  public ws.customer.data.xsd.Customer[] getCustomersByPrivileged(boolean privileged) throws java.rmi.RemoteException{
    if (customerSpringServicePortType == null)
      _initCustomerSpringServicePortTypeProxy();
    return customerSpringServicePortType.getCustomersByPrivileged(privileged);
  }
  
  public ws.customer.data.xsd.Customer getLargestShoppingAmount() throws java.rmi.RemoteException{
    if (customerSpringServicePortType == null)
      _initCustomerSpringServicePortTypeProxy();
    return customerSpringServicePortType.getLargestShoppingAmount();
  }
  
  
}
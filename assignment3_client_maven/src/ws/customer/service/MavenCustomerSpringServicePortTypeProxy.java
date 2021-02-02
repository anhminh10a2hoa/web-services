package ws.customer.service;

public class MavenCustomerSpringServicePortTypeProxy implements ws.customer.service.MavenCustomerSpringServicePortType {
  private String _endpoint = null;
  private ws.customer.service.MavenCustomerSpringServicePortType mavenCustomerSpringServicePortType = null;
  
  public MavenCustomerSpringServicePortTypeProxy() {
    _initMavenCustomerSpringServicePortTypeProxy();
  }
  
  public MavenCustomerSpringServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initMavenCustomerSpringServicePortTypeProxy();
  }
  
  private void _initMavenCustomerSpringServicePortTypeProxy() {
    try {
      mavenCustomerSpringServicePortType = (new ws.customer.service.MavenCustomerSpringServiceLocator()).getCustomerSpringServiceHttpSoap11Endpoint();
      if (mavenCustomerSpringServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mavenCustomerSpringServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mavenCustomerSpringServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mavenCustomerSpringServicePortType != null)
      ((javax.xml.rpc.Stub)mavenCustomerSpringServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ws.customer.service.MavenCustomerSpringServicePortType getMavenCustomerSpringServicePortType() {
    if (mavenCustomerSpringServicePortType == null)
      _initMavenCustomerSpringServicePortTypeProxy();
    return mavenCustomerSpringServicePortType;
  }
  
  public void addCustomer(ws.customer.data.xsd.Customer c) throws java.rmi.RemoteException{
    if (mavenCustomerSpringServicePortType == null)
      _initMavenCustomerSpringServicePortTypeProxy();
    mavenCustomerSpringServicePortType.addCustomer(c);
  }
  
  public int[] getListOfIds() throws java.rmi.RemoteException{
    if (mavenCustomerSpringServicePortType == null)
      _initMavenCustomerSpringServicePortTypeProxy();
    return mavenCustomerSpringServicePortType.getListOfIds();
  }
  
  public ws.customer.data.xsd.Customer getCustomerById(int id) throws java.rmi.RemoteException{
    if (mavenCustomerSpringServicePortType == null)
      _initMavenCustomerSpringServicePortTypeProxy();
    return mavenCustomerSpringServicePortType.getCustomerById(id);
  }
  
  public void setCustomer(ws.customer.data.xsd.Customer[] customer) throws java.rmi.RemoteException{
    if (mavenCustomerSpringServicePortType == null)
      _initMavenCustomerSpringServicePortTypeProxy();
    mavenCustomerSpringServicePortType.setCustomer(customer);
  }
  
  public ws.customer.data.xsd.Customer[] getCustomersByPrivileged(boolean privileged) throws java.rmi.RemoteException{
    if (mavenCustomerSpringServicePortType == null)
      _initMavenCustomerSpringServicePortTypeProxy();
    return mavenCustomerSpringServicePortType.getCustomersByPrivileged(privileged);
  }
  
  public ws.customer.data.xsd.Customer getLargestShoppingAmount() throws java.rmi.RemoteException{
    if (mavenCustomerSpringServicePortType == null)
      _initMavenCustomerSpringServicePortTypeProxy();
    return mavenCustomerSpringServicePortType.getLargestShoppingAmount();
  }
  
  
}
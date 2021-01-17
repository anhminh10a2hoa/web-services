package ws.customer.service;

public class DBCustomerServicePortTypeProxy implements ws.customer.service.DBCustomerServicePortType {
  private String _endpoint = null;
  private ws.customer.service.DBCustomerServicePortType dBCustomerServicePortType = null;
  
  public DBCustomerServicePortTypeProxy() {
    _initDBCustomerServicePortTypeProxy();
  }
  
  public DBCustomerServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initDBCustomerServicePortTypeProxy();
  }
  
  private void _initDBCustomerServicePortTypeProxy() {
    try {
      dBCustomerServicePortType = (new ws.customer.service.DBCustomerServiceLocator()).getDBCustomerServiceHttpSoap11Endpoint();
      if (dBCustomerServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)dBCustomerServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)dBCustomerServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (dBCustomerServicePortType != null)
      ((javax.xml.rpc.Stub)dBCustomerServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ws.customer.service.DBCustomerServicePortType getDBCustomerServicePortType() {
    if (dBCustomerServicePortType == null)
      _initDBCustomerServicePortTypeProxy();
    return dBCustomerServicePortType;
  }
  
  public java.lang.String searchPriceData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, double lowerPrice, double higherPrice) throws java.rmi.RemoteException{
    if (dBCustomerServicePortType == null)
      _initDBCustomerServicePortTypeProxy();
    return dBCustomerServicePortType.searchPriceData(userName, password, db, table, lowerPrice, higherPrice);
  }
  
  public java.lang.String deleteCustomerData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, int customerId) throws java.rmi.RemoteException{
    if (dBCustomerServicePortType == null)
      _initDBCustomerServicePortTypeProxy();
    return dBCustomerServicePortType.deleteCustomerData(userName, password, db, table, customerId);
  }
  
  public java.lang.String updateCustomerData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, int customerId, java.lang.String values) throws java.rmi.RemoteException{
    if (dBCustomerServicePortType == null)
      _initDBCustomerServicePortTypeProxy();
    return dBCustomerServicePortType.updateCustomerData(userName, password, db, table, customerId, values);
  }
  
  public java.lang.String searchNameData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, java.lang.String customerName) throws java.rmi.RemoteException{
    if (dBCustomerServicePortType == null)
      _initDBCustomerServicePortTypeProxy();
    return dBCustomerServicePortType.searchNameData(userName, password, db, table, customerName);
  }
  
  public java.lang.String getCustomerData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, java.lang.String condition) throws java.rmi.RemoteException{
    if (dBCustomerServicePortType == null)
      _initDBCustomerServicePortTypeProxy();
    return dBCustomerServicePortType.getCustomerData(userName, password, db, table, condition);
  }
  
  public java.lang.String writeCustomerData(java.lang.String userName, java.lang.String password, java.lang.String db, java.lang.String table, java.lang.String values) throws java.rmi.RemoteException{
    if (dBCustomerServicePortType == null)
      _initDBCustomerServicePortTypeProxy();
    return dBCustomerServicePortType.writeCustomerData(userName, password, db, table, values);
  }
  
  
}
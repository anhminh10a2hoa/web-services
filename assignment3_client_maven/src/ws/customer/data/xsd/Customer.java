/**
 * Customer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.customer.data.xsd;

public class Customer  implements java.io.Serializable {
    private java.lang.Integer customerID;

    private java.lang.String customerName;

    private java.lang.Float discount;

    private java.lang.Integer discountPercentage;

    private java.lang.Boolean privileged;

    private java.lang.Float shoppingAmount;

    private java.util.Date shoppingDate;

    public Customer() {
    }

    public Customer(
           java.lang.Integer customerID,
           java.lang.String customerName,
           java.lang.Float discount,
           java.lang.Integer discountPercentage,
           java.lang.Boolean privileged,
           java.lang.Float shoppingAmount,
           java.util.Date shoppingDate) {
           this.customerID = customerID;
           this.customerName = customerName;
           this.discount = discount;
           this.discountPercentage = discountPercentage;
           this.privileged = privileged;
           this.shoppingAmount = shoppingAmount;
           this.shoppingDate = shoppingDate;
    }


    /**
     * Gets the customerID value for this Customer.
     * 
     * @return customerID
     */
    public java.lang.Integer getCustomerID() {
        return customerID;
    }


    /**
     * Sets the customerID value for this Customer.
     * 
     * @param customerID
     */
    public void setCustomerID(java.lang.Integer customerID) {
        this.customerID = customerID;
    }


    /**
     * Gets the customerName value for this Customer.
     * 
     * @return customerName
     */
    public java.lang.String getCustomerName() {
        return customerName;
    }


    /**
     * Sets the customerName value for this Customer.
     * 
     * @param customerName
     */
    public void setCustomerName(java.lang.String customerName) {
        this.customerName = customerName;
    }


    /**
     * Gets the discount value for this Customer.
     * 
     * @return discount
     */
    public java.lang.Float getDiscount() {
        return discount;
    }


    /**
     * Sets the discount value for this Customer.
     * 
     * @param discount
     */
    public void setDiscount(java.lang.Float discount) {
        this.discount = discount;
    }


    /**
     * Gets the discountPercentage value for this Customer.
     * 
     * @return discountPercentage
     */
    public java.lang.Integer getDiscountPercentage() {
        return discountPercentage;
    }


    /**
     * Sets the discountPercentage value for this Customer.
     * 
     * @param discountPercentage
     */
    public void setDiscountPercentage(java.lang.Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }


    /**
     * Gets the privileged value for this Customer.
     * 
     * @return privileged
     */
    public java.lang.Boolean getPrivileged() {
        return privileged;
    }


    /**
     * Sets the privileged value for this Customer.
     * 
     * @param privileged
     */
    public void setPrivileged(java.lang.Boolean privileged) {
        this.privileged = privileged;
    }


    /**
     * Gets the shoppingAmount value for this Customer.
     * 
     * @return shoppingAmount
     */
    public java.lang.Float getShoppingAmount() {
        return shoppingAmount;
    }


    /**
     * Sets the shoppingAmount value for this Customer.
     * 
     * @param shoppingAmount
     */
    public void setShoppingAmount(java.lang.Float shoppingAmount) {
        this.shoppingAmount = shoppingAmount;
    }


    /**
     * Gets the shoppingDate value for this Customer.
     * 
     * @return shoppingDate
     */
    public java.util.Date getShoppingDate() {
        return shoppingDate;
    }


    /**
     * Sets the shoppingDate value for this Customer.
     * 
     * @param shoppingDate
     */
    public void setShoppingDate(java.util.Date shoppingDate) {
        this.shoppingDate = shoppingDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Customer)) return false;
        Customer other = (Customer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.customerID==null && other.getCustomerID()==null) || 
             (this.customerID!=null &&
              this.customerID.equals(other.getCustomerID()))) &&
            ((this.customerName==null && other.getCustomerName()==null) || 
             (this.customerName!=null &&
              this.customerName.equals(other.getCustomerName()))) &&
            ((this.discount==null && other.getDiscount()==null) || 
             (this.discount!=null &&
              this.discount.equals(other.getDiscount()))) &&
            ((this.discountPercentage==null && other.getDiscountPercentage()==null) || 
             (this.discountPercentage!=null &&
              this.discountPercentage.equals(other.getDiscountPercentage()))) &&
            ((this.privileged==null && other.getPrivileged()==null) || 
             (this.privileged!=null &&
              this.privileged.equals(other.getPrivileged()))) &&
            ((this.shoppingAmount==null && other.getShoppingAmount()==null) || 
             (this.shoppingAmount!=null &&
              this.shoppingAmount.equals(other.getShoppingAmount()))) &&
            ((this.shoppingDate==null && other.getShoppingDate()==null) || 
             (this.shoppingDate!=null &&
              this.shoppingDate.equals(other.getShoppingDate())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCustomerID() != null) {
            _hashCode += getCustomerID().hashCode();
        }
        if (getCustomerName() != null) {
            _hashCode += getCustomerName().hashCode();
        }
        if (getDiscount() != null) {
            _hashCode += getDiscount().hashCode();
        }
        if (getDiscountPercentage() != null) {
            _hashCode += getDiscountPercentage().hashCode();
        }
        if (getPrivileged() != null) {
            _hashCode += getPrivileged().hashCode();
        }
        if (getShoppingAmount() != null) {
            _hashCode += getShoppingAmount().hashCode();
        }
        if (getShoppingDate() != null) {
            _hashCode += getShoppingDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Customer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://data.customer.ws/xsd", "Customer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.customer.ws/xsd", "customerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.customer.ws/xsd", "customerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.customer.ws/xsd", "discount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discountPercentage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.customer.ws/xsd", "discountPercentage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("privileged");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.customer.ws/xsd", "privileged"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shoppingAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.customer.ws/xsd", "shoppingAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shoppingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.customer.ws/xsd", "shoppingDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}

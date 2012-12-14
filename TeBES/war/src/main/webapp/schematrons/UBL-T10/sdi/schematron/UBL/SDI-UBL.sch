<!-- Schematron binding rules generated automatically. -->
<!-- Data binding to UBL syntax for T10 -->
<!-- (2009). Invinet Sistemes -->
<pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="SDIUBL-T10" is-a="SDIT10">
  <param
    value="((cac:PartyTaxScheme/cbc:CompanyID[@schemeID = 'IT:CF']) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT')) or (not(//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="SDI-T10-R002"/>
  <param
    value="((cbc:BuildingNumber) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT')) or (not(//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="SDI-T10-R001"/>
  <param
    value="((cbc:BuildingNumber) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT')) or (not(//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="SDI-T10-R003"/>
  <param
    value="(not(cac:TaxCategory/cac:TaxScheme/cbc:Name = 'Withholding Tax')) or ((cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode) and (cac:TaxCategory/cbc:Name) and (cac:TaxCategory/cbc:Percent) and (cbc:TaxAmount) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT')) or (not(//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="SDI-T10-R004"/>
  <param
    value="(not(cac:TaxCategory/cac:TaxScheme/cbc:ID = 'BOL')) or ((cac:TaxCategory/cac:TaxScheme/cbc:Name) and (cbc:TaxAmount) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT')) or (not(//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="SDI-T10-R005"/>
  <param
    value="(not(cac:TaxCategory/cbc:ID = 'AE')) or ((cac:TaxCategory/cbc:Name) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT')) or (not(//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="SDI-T10-R006"/>
  <param
    value="((cbc:ID and cbc:IssueDate) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT')) or (not(//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="SDI-T10-R007"/>
  <param
    value="((cbc:ID and cbc:IssueDate) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT')) or (not(//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="SDI-T10-R008"/>
  <param
    value="((cac:RequestedDeliveryPeriod/cbc:StartDate) and ((cac:RequestedDeliveryPeriod/cbc:StartDate)) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT')) or (not(//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="SDI-T10-R009"/>
  <param
    value="(not(cac:TaxCategory/cac:TaxScheme/cbc:Name = 'Withholding Tax')) or ((cac:TaxCategory/cac:TaxScheme/cbc:ID = 'OTH') and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT')) or (not(//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="SDI-T10-R010"/>
  <param value="//cac:TaxSubtotal" name="Sub_Total"/>
  <param value="//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress"
    name="Supplier_Party_Address"/>
  <param value="//cac:AccountingCustomerParty/cac:Party/cac:PostalAddress"
    name="Customer_Party_Address"/>
  <param value="//cac:InvoiceLine//cac:DocumentReference"
    name="Line_Document_Reference"/>
  <param value="//cac:InvoiceLine/cac:OrderLineReference/cac:OrderReference"
    name="Line_Order_Reference"/>
  <param value="//cac:InvoiceLine/cac:BillingReference/cac:InvoiceDocumentReference"
    name="Line_Invoice_Reference"/>
  <param value="//cac:AccountingCustomerParty/cac:Party" name="Customer_Party"/>
 
  <param value="//cac:InvoiceLine/cac:DeliveryPeriod" name="Delivery_Period"/>
</pattern>

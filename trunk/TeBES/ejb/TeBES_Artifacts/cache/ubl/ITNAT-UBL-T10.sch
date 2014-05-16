<!-- Schematron binding rules generated automatically. -->
<!-- Data binding to UBL syntax for T10 -->
<!-- (2009). Invinet Sistemes -->
<pattern id="UBL-T10" xmlns="http://purl.oclc.org/dsdl/schematron" is-a="T10">
  <param
    value="(cac:PartyTaxScheme/cbc:CompanyID[@schemeID = 'IT:VAT'] and cac:PartyName/cbc:Name) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT') or not((//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="IT-T10-R003"/>
  <param
    value="(cbc:StreetName and cbc:CityName and cbc:PostalZone and cbc:CountrySubentity and cac:Country/cbc:IdentificationCode) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT') or not((//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="IT-T10-R005"/>
  <param
    value="(cbc:StreetName and cbc:CityName and cbc:PostalZone and cbc:CountrySubentity and cac:Country/cbc:IdentificationCode) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT') or not((//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="IT-T10-R008"/>
  <param
    value="not(cac:PartyLegalEntity/cbc:CompanyID[@schemeID = 'IT:CC']) or (cac:PartyLegalEntity[cbc:CompanyID/@schemeID = 'IT:CC']/cac:CorporateRegistrationScheme/cac:JurisdictionRegionAddress/cbc:CountrySubentity) or (cac:PartyLegalEntity[cbc:CompanyID/@schemeID = 'IT:CC']/cac:CorporateRegistrationScheme/cac:JurisdictionRegionAddress/cbc:CountrySubentityCode) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT') or not((//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="IT-T10-R013"/>
  <param
    value="cbc:InvoiceTypeCode and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT') or not((//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="IT-T10-R016"/>
  <param
    value="(cbc:ID and cbc:IssueDate and cbc:DocumentType) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT') or not((//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="IT-T10-R017"/>
  <param
    value="(cbc:InvoicedQuantity) and (cbc:InvoicedQuantity/@unitCode) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT') or not((//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="IT-T10-R024"/>
  <param
    value="(cac:Price/cbc:PriceAmount) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT') or not((//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="IT-T10-R031"/>
  <param
    value="(cbc:ID and cbc:IssueDate and cbc:DocumentType) and (//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT') or not((//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode = 'IT'))"
    name="IT-T10-R032"/>
  <param value="//cac:InvoiceLine" name="Invoice_Line"/>
  <param value="/ubl:Invoice" name="Invoice"/>
  <param value="//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress"
    name="Supplier_Party_Address"/>
  <param value="//cac:AccountingCustomerParty/cac:Party/cac:PostalAddress"
    name="Customer_Party_Address"/>
  <param value="//cac:DespatchDocumentReference" name="Transport_Document"/>
  <param value="//cac:TaxRepresentativeParty" name="Tax_Representative_Party"/>
  <param value="//cac:AccountingSupplierParty/cac:Party" name="Supplier_Party"/>
  <param value="//cac:DocumentReference" name="Line_Level_Transport_Document"/>
</pattern>

<!-- Schematron rules generated automatically. -->
<!-- Abstract rules for T10 -->
<!-- (2009). Invinet Sistemes -->
<pattern abstract="true" id="T10" xmlns="http://purl.oclc.org/dsdl/schematron">
  <rule context="$Supplier_Party_Address">
    <assert test="$IT-T10-R005" flag="Fatal">[IT-T10-R005]-A suppliers postal address in an invoice MUST contain at least, Street name, city name, zip code, country subentity and country code.</assert>
  </rule>
  <rule context="$Invoice_Line">
    <assert test="$IT-T10-R024" flag="Fatal">[IT-T10-R024]-Each invoice line MUST contain the quantity and unit of measure.</assert>
    <assert test="$IT-T10-R031" flag="Fatal">[IT-T10-R031]-If the supplier country code is &#8220;IT&#8221;, each invoice line MUST contain the product/service unit price.</assert>
  </rule>
  <rule context="$Invoice">
    <assert test="$IT-T10-R016" flag="Fatal">[IT-T10-R016]-If the supplier country code is &#8220;IT&#8221;, an invoice MUST contain the invoice type.</assert>
  </rule>
  <rule context="$Transport_Document">
    <assert test="$IT-T10-R017" flag="Fatal">[IT-T10-R017]-If the supplier country code is &#8220;IT&#8221;, the reference to the transport document in an invoice MUST contain document identifier, issue date, reference law.</assert>
  </rule>
  <rule context="$Customer_Party_Address">
    <assert test="$IT-T10-R008" flag="Fatal">[IT-T10-R008]-A customer postal address in an invoice MUST contain at least, Street name, city name, zip code, country subentity and country code.</assert>
  </rule>
  <rule context="$Supplier_Party">
    <assert test="$IT-T10-R013" flag="Warning">[IT-T10-R013]-If the supplier country code is &#8220;IT&#8221; and is registered in the Italian Chamber of Commerce, the information about supplier&#8217;s Items Registration Company SHOULD include Country Subentity (as code or text) of Chambers of Commerce of company register.</assert>
  </rule>
  <rule context="$Tax_Representative_Party">
    <assert test="$IT-T10-R003" flag="Warning">[IT-T10-R003]-If Tax Representative Party exists and its country code is &#8220;IT&#8221;, an invoice MUST contain VAT Company Identifier and name of the Tax Representative Party.</assert>
  </rule>
  <rule context="$Line_Level_Transport_Document">
    <assert test="$IT-T10-R032" flag="Fatal">[IT-T10-R032]-If the supplier country code is &#8220;IT&#8221;, the reference to the transport document at line level in an invoice MUST contain document identifier, issue date, reference law.</assert>
  </rule>
</pattern>

<!-- Schematron rules generated automatically. -->
<!-- Abstract rules for T10 -->
<!-- (2009). Invinet Sistemes -->
<pattern abstract="true" id="SDIT10" xmlns="http://purl.oclc.org/dsdl/schematron">
  <rule context="$Supplier_Party_Address">
    <assert test="$SDI-T10-R001" flag="Fatal">[SDI-T10-R001] - Se il cedente/prestatore &#232;
      italiano, l&#39;indirizzo del cedente/prestatore deve contenere il numero civico.</assert>
  </rule>
  <rule context="$Customer_Party_Address">
    <assert test="$SDI-T10-R003" flag="Fatal">[SDI-T10-R003] - Se il cedente/prestatore &#232;
      italiano, l&#39;indirizzo del cessionario/committente deve contenere il numero
      civico.</assert>
  </rule>
  <rule context="$Customer_Party">
    <assert test="$SDI-T10-R002" flag="Warning">[SDI-T10-R002] - Se il cedente/prestatore &#232;
      italiano, la fattura DEVE contenere il codice fiscale del cessionario/committente.</assert>
  </rule>
  <rule context="$Sub_Total">
    <assert test="$SDI-T10-R004" flag="Fatal">[SDI-T10-R004] - Se il cedente/prestatore &#232;
      italiano, nei casi in cui sia applicabile la ritenuta di acconto DEVONO essere indicati
      l&#39;importo, la tipologia, l&#39;aliquota e la causale pagamento.</assert>
    <assert test="$SDI-T10-R005" flag="Fatal">[SDI-T10-R005] - Se il cedente/prestatore &#232;
      italiano, nei casi in cui sia prevista l&#39;imposta di bollo DEVONO essere indicati
      l&#39;importo e gli estremi della relativa autorizzazione.</assert>
    <assert test="$SDI-T10-R006" flag="Fatal">[SDI-T10-R006] - Se il cedente/prestatore &#232;
      italiano ed il cessionario/committente &#232; debitore di imposta in luogo del
      cedente/prestatore (reverse charge), la fattura DEVE contenere la norma di riferimento,
      comunitaria o nazionale.</assert>
    <assert test="$SDI-T10-R010" flag="Fatal">[SDI-T10-R010] - Nei casi di ritenuta di acconto, il
      valore dell'identificatore dello schema di imposta deve essere &#39;OTH&#39;.</assert>
  </rule>
  <rule context="$Line_Order_Reference">
    <assert test="$SDI-T10-R007" flag="Fatal">[SDI-T10-R007] - Se il cedente/prestatore &#232;
      italiano, ogni riferimento ad un documento collegato ad una riga di fattura DEVE contenere
      numero e data di emissione del documento.</assert>
  </rule>

  <rule context="$Line_Invoice_Reference">
    <assert test="$SDI-T10-R008" flag="Fatal">[SDI-T10-R008] - Se il cedente/prestatore &#232;
      italiano, ogni riferimento ad una fattura collegata a questa fattura DEVE contenere numero e
      data di emissione del fattura collegata ed almeno un riferimento ad una riga della fattura
      collegata.</assert>
  </rule>
  <rule context="$Line_Document_Reference">
    <assert test="$SDI-T10-R007" flag="Fatal">[SDI-T10-R007] - Se il cedente/prestatore &#232;
      italiano, ogni riferimento ad un documento collegato ad una riga di fattura DEVE contenere
      numero e data di emissione del documento.</assert>
  </rule>
  <rule context="$Delivery_Period">
    <assert test="$SDI-T10-R009" flag="Fatal">[SDI-T10-R009] - Se il cedente/prestatore &#232;
      italiano, ogni riga di fattura relativa a prestazione di servizio DEVE indicare la data di
      inizio e la data di fine del periodo cui si riferisce l&#39;eventuale servizio
      prestato.</assert>
  </rule>
</pattern>

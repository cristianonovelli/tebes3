<?xml version="1.0" encoding="UTF-8"?>
<!-- 

        	UBL syntax binding to the T10   
        	Author: Oriol Bausà

     -->
<schema xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" xmlns:ubl="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2" queryBinding="xslt2" xmlns="http://purl.oclc.org/dsdl/schematron">
  <title>EUGEN  T10 bound to UBL</title>
  <ns prefix="cbc" uri="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"/>
  <ns prefix="cac" uri="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"/>
  <ns prefix="ubl" uri="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"/>
  <phase id="EUGENT10_phase">
    <active pattern="UBL-T10"/>
  </phase>
  <phase id="codelist_phase">
    <active pattern="CodesT10"/>
  </phase>
  <!-- Abstract CEN BII patterns -->
  <!-- ========================= -->
  <include href="abstract/EUGEN-T10.sch"/>
  <!-- Data Binding parameters -->
  <!-- ======================= -->
  <include href="UBL/EUGEN-UBL-T10.sch"/>
  <!-- Code Lists Binding rules -->
  <!-- ======================== -->
  <include href="codelist/EUGENCodesT10.sch"/>
</schema>

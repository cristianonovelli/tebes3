<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- TEST-PLAN
       15/12/2013
        -->



<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:tebes="http://www.ubl-italia.org/TeBES"
	>
 
 
<!-- Importazione dei template comuni -->
<xsl:import href="TeBES_commons.xsl"/>

<xsl:param name="enit" select="tebes:TestPlan/@lg" />
	
 <xsl:template match="/">
   	<html>
    <head>
		  <title>Test Plan descriptor</title>
		  <xsl:call-template name="callCSS"/> 
	</head>
	<body>
		<xsl:call-template name="TitleAndlogo">
			<xsl:with-param name="parTitle">Test Plan descriptor</xsl:with-param>
		</xsl:call-template>
		<xsl:apply-templates select="//tebes:TestPlan"/> 
	</body>
    </html>
 	</xsl:template>


<!--  TestPlan show         -->	
<xsl:template match="tebes:TestPlan">

	<xsl:call-template name="TestPlanHeaderShow"> 
		<xsl:with-param name="parTestPlan" select="."/>
	</xsl:call-template> 
	<br/>
	<!--  TestPlan and Test Session header show         -->	
	<h1>Test Actions List</h1>
	<xsl:for-each select="./tebes:TestActionList/tebes:TestAction">
		<xsl:apply-templates select="."  mode="summary"/>

		<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
			<xsl:attribute name="id">tListTable<xsl:value-of select="@number"/></xsl:attribute>
			<xsl:apply-templates select="."  mode="extended"/>
		</div>
	</xsl:for-each>
</xsl:template>

<!--  TestPlan header show         -->	

<xsl:template name="TestPlanHeaderShow">
	<xsl:param name="parTestPlan"/>
	<xsl:for-each select="./tebes:TestPlanHeader">
	    <table >
		    <tr><td class="TP_tableleft">Test Plan Id.Name</td>
		    	<td class="TP_tableright"><xsl:value-of select="tebes:Id"/> . <xsl:value-of select="tebes:Name"/></td>   
		    </tr>
			<tr>
			    <td class="TP_tableleft">UserID</td>
				<td class="TP_tableright"><xsl:value-of select="tebes:UserId"/></td>
			</tr>
			<tr>
			    <td class="'TP_tableleft">Description</td>
				<td class="TP_tableright"><xsl:value-of select="tebes:Description [@lg=$enit]"/></td>
			</tr>
			<tr>
			    <td class="TP_tableleft">creationDatetime</td>
				<td class="TP_tableright"><xsl:value-of select="tebes:CreationDatetime"/></td>
			</tr>
			<tr>
			    <td class="TP_tableleft">lastUpdateDatetime</td>
				<td class="TP_tableright"><xsl:value-of select="tebes:LastUpdateDatetime"/></td>
			</tr>
			<tr>
			    <td class="TP_tableleft">State</td>
				<td class="TP_tableright"><xsl:value-of select="./tebes:State"/></td>
			</tr>
	    </table>
	</xsl:for-each>
</xsl:template>



<!--  NOT USED generic row 
        implements tr and td, 
        receives parCol1, parCol2
        <xsl:call-template name="GenericRow"><xsl:with-param name="parCol1">TA Description</xsl:with-param><xsl:with-param name="parCol2" ><xsl:value-of select="tebes:ActionDescription"/></xsl:with-param></xsl:call-template>
         -->	
<xsl:template name="GenericRow">
	<xsl:param name="parCol1"/>
	<xsl:param name="parCol2"/>
<xsl:if test="string-length($parCol1) >0  or  string-length($parCol2) >0  ">
	<tr>
	<td class="TP_tableleft"><xsl:value-of select="$parCol1"/></td>
	<td class="TP_tableright"    disable-output-escaping="yes"><xsl:value-of select="$parCol2"/></td>
	</tr>
</xsl:if>	
</xsl:template>



	  
			  
</xsl:stylesheet>

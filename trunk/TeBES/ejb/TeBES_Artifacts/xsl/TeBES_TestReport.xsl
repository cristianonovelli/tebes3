<?xml version='1.0' encoding='ISO-8859-1'?>


<!-- TEST-REPORT
       16/12/2013
        -->



<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:tebes="http://www.ubl-italia.org/TeBES"
	>
 
	 
 
<!-- Importazione dei template comuni -->
<xsl:import href="TeBES_commons.xsl"/>
	
	<xsl:template match="/">
<html>
<head>
	<title>Test REPORT</title>
	<xsl:call-template name="callCSS"/>

	
</head>
<body>
		<xsl:call-template name="TitleAndlogo">
			<xsl:with-param name="parTitle">Test Report</xsl:with-param>
		</xsl:call-template>
		<xsl:apply-templates select="//tebes:Report"/> 
</body>
</html>
	
	</xsl:template>	



<xsl:param name="enit" select="tebes:Report/@lg" />
	


<!--  TestPlan show         -->	
<xsl:template match="tebes:Report">
	<!--  TestReport and Test Session header show         -->	
	<xsl:call-template name="TestReportHeaderShow"> 
		<xsl:with-param name="parTestReport" select="."/>
	</xsl:call-template> 
	<xsl:call-template name="TestSessionHeaderShow"> 
		<xsl:with-param name="parTestSession" select="tebes:Session"/>
	</xsl:call-template> 
	<br/>

	<!--  Test Action show         -->	
	<h1>Test Actions List</h1>
	<xsl:for-each select="./tebes:TestPlanExecution/tebes:TestActionList/tebes:TestAction">
		<xsl:apply-templates select="."  mode="summary"/>

		<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
			<xsl:attribute name="id">tListTable<xsl:value-of select="@number"/></xsl:attribute>
			<xsl:apply-templates select="."  mode="extended"/>
		</div>
	</xsl:for-each>
</xsl:template>


<!--  TestPlan header show         -->	
<xsl:template name="TestReportHeaderShow">
	<xsl:param name="parTestReport"/>
	    
	 <table >
		<tr>
		<td class="TP_tableleft">Test Report </td>
		<td class="TP_tableright">
			<span style="CURSOR: hand;"  >
			<xsl:attribute name="onclick">document.getElementById('Report').style.display= document.getElementById('Report').style.display=='none' ? '' : 'none';</xsl:attribute>
			<xsl:attribute name="title">Click here to get Report details</xsl:attribute>
				<a href='#stay_here'><xsl:value-of select="tebes:ReportHeader/tebes:Id"/> . <xsl:value-of select="tebes:ReportHeader/tebes:Name"/> - <xsl:value-of select="tebes:ReportHeader/tebes:Description[@lg=$enit]"/> (<em><xsl:value-of select="tebes:ReportHeader/tebes:State"/></em>) </a>
			</span>
		</td>
		</tr>
	</table>
		    	
	 <div  style="DISPLAY:none ;margin-left:2%;width:98%" >
	 <xsl:attribute name="id">Report</xsl:attribute>   
	    <table  class="lightbg">
		<tr><td class="TP_tableleft">Test Report Id.Name</td>
			<td class="TP_tableright"><b><xsl:value-of select="tebes:ReportHeader/tebes:Id"/></b> . <xsl:value-of select="tebes:ReportHeader/tebes:Name"/></td>   
		</tr>
		<tr>
		<td class="'TP_tableleft">Description</td>
			<td class="TP_tableright"><xsl:value-of select="tebes:ReportHeader/tebes:Description[@lg=$enit]"/></td>
		</tr>
		<tr>
		<td class="TP_tableleft">Creation Date Time</td>
			<td class="TP_tableright"><xsl:value-of select="tebes:Session/tebes:CreationDateTime"/></td>
		</tr>
	    <tr>
	    	<td class="TP_tableleft">Last Update Date Time</td>
	    	<td class="TP_tableright"><xsl:value-of select="tebes:Session/tebes:LastUpdateDateTime"/></td>
	    </tr>
		<tr>
		<td class="TP_tableleft">State</td>
			<td class="TP_tableright"><xsl:value-of select="tebes:ReportHeader/tebes:State"/></td>
		</tr>
		<tr>
		<td class="TP_tableleft">UserID</td>
			<td class="TP_tableright"><xsl:value-of select="tebes:Session/tebes:User/@id"/></td>
		</tr>
	</table>
	</div>
	
</xsl:template>


<!--  TestSession header show         -->	
<xsl:template name="TestSessionHeaderShow">
	<xsl:param name="parTestSession"/>
	
	 <table >
		<tr>
		<td class="TP_tableleft">Test Session </td>
		<td class="TP_tableright">
			<span style="CURSOR: hand;"  >
			<xsl:attribute name="onclick">document.getElementById('Session').style.display= document.getElementById('Session').style.display=='none' ? '' : 'none';</xsl:attribute>
			<xsl:attribute name="title">Click here to get Report details</xsl:attribute>
				<a href='#stay_here'> User id <xsl:value-of select="$parTestSession/tebes:User/@*"/> | SUT id <xsl:value-of select="$parTestSession/tebes:User/tebes:SUTList/tebes:SUT/@*"/> 
					| Test Plan id <xsl:value-of select="$parTestSession/tebes:TestPlan/@*"/>
			 
			</a>
			</span>
		</td>
		</tr>
	</table>	
	
	 <div  style="DISPLAY:none ;margin-left:2%;width:98%" >
	 <xsl:attribute name="id">Session</xsl:attribute>   	
				<table  class="lightbg">
				<tr><td class="TP_tableleft">Test Session ID</td><td class="TP_tableright"><b><xsl:value-of select="$parTestSession/@id"/></b></td></tr>												
				<tr><td class="TP_tableleft">User</td><td class="TP_tableright">
					<xsl:apply-templates select="$parTestSession/tebes:User"  mode="summary"/>
				</td></tr>
				<tr><td class="TP_tableleft">Test Plan</td><td class="TP_tableright">
					<xsl:apply-templates select="$parTestSession/tebes:TestPlan"  mode="extended"/>
				</td></tr>				
				</table>
</div>
</xsl:template>




<!--  USER show,   -->	
<xsl:template match="tebes:User" mode="summary">
			<span style="CURSOR: hand;"  >
			<xsl:attribute name="onclick">document.getElementById('User').style.display= document.getElementById('User').style.display=='none' ? '' : 'none';</xsl:attribute>
			<xsl:attribute name="title">Click here to get User features</xsl:attribute>
			<a href='#stay_here'><xsl:value-of select="tebes:Name" /> - <xsl:value-of select="tebes:Surname" /> (<xsl:value-of select="@id" />)</a>
			</span>			

<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
	<xsl:attribute name="id">User</xsl:attribute>
	<xsl:apply-templates select="."  mode="extended"/>
</div>
 
</xsl:template>

<!--  Test USER extended show, TABLE  -->	
<xsl:template match="tebes:User" mode="extended">
<table class="lightbg">
	<tr><td class="TP_tableleft">Input id </td><td class="TP_tableright"><xsl:value-of select="@id" /></td></tr>
	<tr><td class="TP_tableleft">Name</td><td class="TP_tableright"><xsl:value-of select="tebes:Name" /></td></tr>
	<tr><td class="TP_tableleft">Surname</td><td class="TP_tableright"><xsl:value-of select="tebes:Surname" /></td></tr>
	<tr><td class="TP_tableleft">System Under Test (SUT) List</td><td class="TP_tableright">
		<xsl:for-each select="tebes:SUTList/tebes:SUT">
			<xsl:apply-templates select="."  mode="extended"/>	
		</xsl:for-each>
	</td></tr>
</table>  
</xsl:template>

<!--  Test SUT  show, TABLE  -->	
<xsl:template match="tebes:SUT" mode="extended">
<table class="lightbg">
	<tr><td class="TP_tableleft">SUT Name</td><td class="TP_tableright"><xsl:value-of select="tebes:Name" /></td></tr>
	<tr><td class="TP_tableleft">SUT id </td><td class="TP_tableright"><xsl:value-of select="@id" /></td></tr>
	<tr><td class="TP_tableleft">SUT Type</td><td class="TP_tableright"><xsl:value-of select="tebes:Type" /></td></tr>
	<tr><td class="TP_tableleft">Modality</td><td class="TP_tableright"><xsl:value-of select="tebes:Description" /></td></tr>
</table>  
</xsl:template>

<!--  Test TestPlan extended show, TABLE  -->	
<xsl:template match="tebes:TestPlan" mode="extended">
<table class="lightbg">
	<tr><td class="TP_tableleft">Test Plan id</td><td class="TP_tableright"><xsl:value-of select="@id" /></td></tr>
	<tr><td class="TP_tableleft">Test Plan Name</td><td class="TP_tableright"><xsl:value-of select="tebes:Name" /></td></tr>
	<tr><td class="TP_tableleft">Test Plan state</td><td class="TP_tableright"><xsl:value-of select="tebes:State" /></td></tr>
	<tr><td class="TP_tableleft">Creation data time</td><td class="TP_tableright"><xsl:value-of select="tebes:CreationDateTime" /></td></tr>
	<tr><td class="TP_tableleft">Last update date time</td><td class="TP_tableright"><xsl:value-of select="tebes:LastUpdateDateTime" /></td></tr>
	<tr><td class="TP_tableleft">Description</td><td class="TP_tableright"><xsl:value-of select="tebes:Description[@lg=$enit]" /></td></tr>
</table>  
</xsl:template>



<xsl:template name="TitleAndlogo">
	<xsl:param name="parTitle"/>
<table class="table_transparent">
	<tr>
	<td width="85%"><h1><xsl:value-of select="$parTitle"/></h1></td>
	<td><table class="table_evidenced" border="0"  width="100%"><tr><td><font size="6"><B>TeBES</B></font></td></tr></table>
	</td>
	</tr>
</table>
</xsl:template>			  


	
</xsl:stylesheet>





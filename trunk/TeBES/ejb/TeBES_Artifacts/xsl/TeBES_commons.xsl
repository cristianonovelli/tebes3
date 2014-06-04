<?xml version="1.0" encoding="UTF-8"?>
<!-- TeBES Commons -->

<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:tebes="http://www.ubl-italia.org/TeBES"
	>

	
	
<xsl:param name="enit"></xsl:param>	


<xsl:template name="TitleAndlogo">
	<xsl:param name="parTitle"/>
<table class="table_transparent">
	<tr>
	<td width="85%"><h1><xsl:value-of select="$parTitle"/></h1></td>
	<td><table class="table_evidenced" border="0"  width="100%"><tr><td><font size="6" color="white"><B>TeBES</B></font></td></tr></table>
	</td>
	</tr>
</table>
</xsl:template>	




<xsl:template name="callCSS">
	<link rel="stylesheet" type="text/css" href="../../../xsl/TeBES_Data.css"/>  
</xsl:template> 

	
<!-- x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x  -->


<!--  TestAction Summary show , TD        -->	
<xsl:template match="tebes:TestAction" mode="summary">
<table >
<tr >
<td class="td_evidenced"  width="10%">
	<span style="CURSOR: hand;"  >
		<xsl:attribute name="onclick">document.getElementById('tListTable<xsl:value-of select="@number"/>').style.display= document.getElementById('tListTable<xsl:value-of select="@number"/>').style.display=='none' ? '' : 'none';</xsl:attribute>
		<xsl:attribute name="title">Click here to get <xsl:value-of select="tebes:ActionName"/> features</xsl:attribute>
		<a href='#stay_here'>	
		  <xsl:value-of select="@number"/>
		</a>			
	</span>
</td>
<td>
	<span style="CURSOR: hand;"  >
		<xsl:attribute name="onclick">document.getElementById('tListTable<xsl:value-of select="@number"/>').style.display= document.getElementById('tListTable<xsl:value-of select="@number"/>').style.display=='none' ? '' : 'none';</xsl:attribute>
		<xsl:attribute name="title">Click here to get <xsl:value-of select="tebes:ActionName"/> Features</xsl:attribute>
		<a href='#stay_here'><xsl:attribute name="title">Click here to get <xsl:value-of select="tebes:ActionName"/> features</xsl:attribute>
			<xsl:value-of select="@id"/><br/>
			<xsl:value-of select="tebes:ActionDescription[@lg=$enit]"/>
		  <xsl:if test="tebes:TestResults">
		     <br/>
			 Result:<xsl:value-of select="tebes:TestResults/@result"/>
		  </xsl:if>
		</a>
	</span>
</td>
</tr>
</table>  
</xsl:template>



<!--  TestAction extended show , TABLE        -->	
<xsl:template match="tebes:TestAction" mode="extended">
<table class="lightbg">
	
	
	<tr><td class="TP_tableleft">TA Name</td><td class="TP_tableright"><xsl:value-of select="tebes:ActionName"/></td></tr>
	<tr><td class="TP_tableleft">TA Description</td><td class="TP_tableright"><xsl:value-of select="tebes:ActionDescription[@lg=$enit]"/></td></tr>
	<xsl:if test="tebes:ActionUserInstruction">
	   <tr><td class="TP_tableleft">TA User Instruction</td><td class="TP_tableright"><xsl:value-of select="tebes:ActionUserInstruction"  disable-output-escaping="yes" /></td></tr>
	</xsl:if>	
	<tr><td class="TP_tableleft">Test resource</td><td class="TP_tableright">
		<xsl:apply-templates select="tebes:Test"  mode="summary"/>
	</td></tr>
	<xsl:if test="tebes:Inputs/tebes:Input">
	   <tr><td class="TP_tableleft">TA Input list</td><td class="TP_tableright">
		   <xsl:for-each select="tebes:Inputs/tebes:Input">
			  <xsl:apply-templates select="."  mode="summary"/>
		   </xsl:for-each>
		</td></tr>
	</xsl:if>	
	<xsl:if test="tebes:TestResults">
		<tr><td class="TP_tableleft">Result</td><td class="TP_tableright"><xsl:value-of select="tebes:TestResults/@result"/></td></tr>
		<tr><td class="TP_tableleft">Test Results Message</td><td class="TP_tableright"><xsl:value-of select="tebes:TestResults/@message"/></td></tr>
		<tr><td class="TP_tableleft">Test Single Result</td><td class="TP_tableright">
			<xsl:apply-templates select="tebes:TestResults/tebes:SingleResult" mode="summary"/>
		</td></tr>
		<xsl:if test="tebes:Prerequisites">
		   <tr><td class="TP_tableleft">Prerequisite Result</td><td class="TP_tableright">
			  <xsl:apply-templates select="tebes:Prerequisites/tebes:PrerequisiteResult" mode="summary"/>	
		   </td></tr>
		</xsl:if>
	</xsl:if>	
</table>  
</xsl:template>
	
	
	<!-- Single Result extended show, TABLE  -->	
	<xsl:template match="tebes:TestResults/tebes:SingleResult" mode="summary">
		<span style="CURSOR: hand;"  >
			<xsl:attribute name="onclick">document.getElementById('tListSingleResult<xsl:value-of select="@id" />').style.display= document.getElementById('tListSingleResult<xsl:value-of select="@id" />').style.display=='none' ? '' : 'none';</xsl:attribute>
			<xsl:attribute name="title">Click here to get single result</xsl:attribute>
			<a href='#stay_here'>Single Result <xsl:value-of select="@id"/> : <xsl:value-of select="@result"/><br/></a>
		</span>		
		
		<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
			<xsl:attribute name="id">tListSingleResult<xsl:value-of select="@id" /></xsl:attribute>
			<xsl:apply-templates select="."  mode="extended"/>
		</div> 
		
		
	</xsl:template>
	
	
	<!--  Single Result extended show, TABLE  -->	
	<xsl:template match="tebes:TestResults/tebes:SingleResult" mode="extended">
		<table class="lightbg">
			<tr><td class="TP_tableleft">id</td><td class="TP_tableright"><xsl:value-of select="@id" /></td></tr>
			<tr><td class="TP_tableleft">Name</td><td class="TP_tableright"><xsl:value-of select="@name"/></td></tr>
			<tr><td class="TP_tableleft">line</td><td class="TP_tableright"><xsl:value-of select="@line"/></td></tr>
			<tr><td class="TP_tableleft">Message</td><td class="TP_tableright"><xsl:value-of select="@message" /></td></tr>
			<tr><td class="TP_tableleft">Result</td><td class="TP_tableright"><xsl:value-of select="@result" /></td></tr>
		</table>  
	</xsl:template>	
	
	
	
<!--  Test resource extended show, TABLE  -->	
<xsl:template match="tebes:Test" mode="summary">
	<span style="CURSOR: hand;"  >
			<xsl:attribute name="onclick">document.getElementById('tListTest<xsl:value-of select="../@number" />').style.display= document.getElementById('tListTest<xsl:value-of select="../@number" />').style.display=='none' ? '' : 'none';</xsl:attribute>
			<xsl:attribute name="title">Click here to get <xsl:value-of select="@type"/> features</xsl:attribute>
			<a href='#stay_here'><xsl:value-of select="." /></a>
	</span>		

<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
	<xsl:attribute name="id">tListTest<xsl:value-of select="../@number" /></xsl:attribute>
<xsl:apply-templates select="."  mode="extended"/>
</div> 


</xsl:template>


<!--  Test resource extended show, TABLE  -->	
<xsl:template match="tebes:Test" mode="extended">
 <table class="lightbg">
	<tr><td class="TP_tableleft">Test id</td><td class="TP_tableright"><xsl:value-of select="." /></td></tr>
	<tr><td class="TP_tableleft">Type</td><td class="TP_tableright"><xsl:value-of select="@type"/></td></tr>
	<tr><td class="TP_tableleft">Language</td><td class="TP_tableright"><xsl:value-of select="@lg"/></td></tr>
	<tr><td class="TP_tableleft">Skip prerequisites</td><td class="TP_tableright"><xsl:value-of select="@skipPrerequisites" /></td></tr>
	<tr><td class="TP_tableleft">Location</td><td class="TP_tableright">
		<a target="_blank">
		   <xsl:attribute name="href"><xsl:value-of select="@location" /></xsl:attribute>
		   <xsl:value-of select="@location" />
		</a>		
	</td></tr>
</table>  
</xsl:template>
	
	
	<!-- Prerequisite Result extended show, TABLE  -->	
	<xsl:template match="tebes:Prerequisites/tebes:PrerequisiteResult" mode="summary">
		
		<span style="CURSOR: hand;"  >
			<xsl:attribute name="onclick">document.getElementById('tListPrerequisiteResult<xsl:value-of select="@id" /> (<xsl:value-of select="@description" />) : <xsl:value-of select="@result" />').style.display= document.getElementById('tListPrerequisiteResult<xsl:value-of select="@id" /> (<xsl:value-of select="@description" />) : <xsl:value-of select="@result" />').style.display=='none' ? '' : 'none';</xsl:attribute>
			<xsl:attribute name="title">Click here to get prerequisite result</xsl:attribute>
			<a href='#stay_here'>
				<xsl:choose>
					<xsl:when test="@result = 'fail'">
						<b>	- <xsl:value-of select="@id" /> (<xsl:value-of select="@description" />) : <xsl:value-of select="@result" /></b><br/>
				    </xsl:when>
				    <xsl:otherwise>
				    	- <xsl:value-of select="@id" /> (<xsl:value-of select="@description" />) : <xsl:value-of select="@result" /><br/>	
				    </xsl:otherwise>
				</xsl:choose>
			</a>
		</span>		
	
		<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
			<xsl:attribute name="id">tListPrerequisiteResult<xsl:value-of select="@id" /> (<xsl:value-of select="@description" />) : <xsl:value-of select="@result" /> </xsl:attribute>
			<xsl:apply-templates select="."  mode="extended"/>
		</div> 	
		
	</xsl:template>
	
	<!--  Prerequisite Result extended show, TABLE  -->	
	<xsl:template match="tebes:Prerequisites/tebes:PrerequisiteResult" mode="extended">
		<table class="lightbg">
			<tr><td class="TP_tableleft">id</td><td class="TP_tableright"><xsl:value-of select="@id" /></td></tr>
			<tr><td class="TP_tableleft">Name</td><td class="TP_tableright"><xsl:value-of select="@name"/></td></tr>
			<tr><td class="TP_tableleft">Result</td><td class="TP_tableright"><xsl:value-of select="@result" /></td></tr>
			<tr><td class="TP_tableleft">Single Prerequisite Result</td><td class="TP_tableright">
				<xsl:apply-templates select="tebes:SinglePrerequisiteResult" mode="summary"/>	
			</td></tr>		
		</table>  
	</xsl:template>		
	


	<!-- Single Prerequisite Result extended show, TABLE  -->	
	<xsl:template match="tebes:SinglePrerequisiteResult" mode="summary">
		
		<span style="CURSOR: hand;"  >
			<xsl:attribute name="onclick">document.getElementById('tListSinglePrerequisiteResult<xsl:value-of select="@name" />').style.display= document.getElementById('tListSinglePrerequisiteResult<xsl:value-of select="@name" />').style.display=='none' ? '' : 'none';</xsl:attribute>
			<xsl:attribute name="title">Click here to get single prerequisite result</xsl:attribute>
			<a href='#stay_here'>
					- <xsl:value-of select="@name" /><br/>	
			</a>
		</span>		
		
		<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
			<xsl:attribute name="id">tListSinglePrerequisiteResult<xsl:value-of select="@name" /> </xsl:attribute>
			<xsl:apply-templates select="."  mode="extended"/>
		</div> 	
		
	</xsl:template>
	
	<!--  Single Prerequisite Result extended show, TABLE  -->	
	<xsl:template match="tebes:SinglePrerequisiteResult" mode="extended">
		<table class="lightbg">
			<tr><td class="TP_tableleft">line</td><td class="TP_tableright"><xsl:value-of select="@line" /></td></tr>
			<tr><td class="TP_tableleft">Message</td><td class="TP_tableright"><xsl:value-of select="@message" /></td></tr>
			<tr><td class="TP_tableleft">Result</td><td class="TP_tableright"><xsl:value-of select="@result" /></td></tr>
		</table>  
	</xsl:template>		
	





<!--  Test resource extended show, TABLE  -->	
<xsl:template match="tebes:Inputs/tebes:Input" mode="summary">
 	
<table>
	<tr><td class="TP_tableleft">Input</td>
	    <td class="TP_tableright">
			<span style="CURSOR: hand;"  >
				<xsl:attribute name="onclick">document.getElementById('tListInput<xsl:value-of select="tebes:Name"/>').style.display= document.getElementById('tListInput<xsl:value-of select="tebes:Name"/>').style.display=='none' ? '' : 'none';</xsl:attribute>
				<xsl:attribute name="title">Click here to get <xsl:value-of select="tebes:Name"/> features</xsl:attribute>
				<a href='#stay_here'><xsl:value-of select="tebes:Name" /><br/>
					<xsl:value-of select="tebes:InputDescription[@lg=$enit]" /> (<xsl:value-of select="tebes:SUT/@interaction" />)
				</a>
			</span>			
	</td></tr>
</table>  

	

<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
	<xsl:attribute name="id">tListInput<xsl:value-of select="tebes:Name"/></xsl:attribute>
	<xsl:apply-templates select="."  mode="extended"/>
</div>
 
</xsl:template>

<!--  Test resource extended show, TABLE  -->	
	<xsl:template match="tebes:Inputs/tebes:Input" mode="extended">
<table class="lightbg">
	<tr><td class="TP_tableleft">Input id and name</td><td class="TP_tableright"><xsl:value-of select="tebes:Name" /></td></tr>
	<xsl:if test="tebes:InputDescription">
	   <tr><td class="TP_tableleft">Description</td><td class="TP_tableright"><xsl:value-of select="tebes:InputDescription[@lg=$enit]" /></td></tr>
	</xsl:if>
	<tr><td class="TP_tableleft">Input type</td><td class="TP_tableright"><xsl:value-of select="tebes:SUT/@type" /></td></tr>
	<tr><td class="TP_tableleft">Language</td><td class="TP_tableright"><xsl:value-of select="tebes:SUT/@lg" /></td></tr>
	<tr><td class="TP_tableleft">Interaction mode</td><td class="TP_tableright"><xsl:value-of select="tebes:SUT/@interaction" /></td></tr>
	<tr><td class="TP_tableleft">File id. ref.</td><td class="TP_tableright"><xsl:value-of select="tebes:SUT/@fileIdRef" /></td></tr>
	<xsl:if test="tebes:SUT/@fileSource">
	  <tr><td class="TP_tableleft">File Source</td><td class="TP_tableright">
		 <a target="_blank">
			<xsl:attribute name="href"><xsl:value-of select="tebes:SUT/@fileSource" /></xsl:attribute>
			<xsl:value-of select="tebes:SUT/@fileSource" />
		 </a>		
	  </td></tr>
	</xsl:if>
	<xsl:if test="tebes:GUI">
	  <tr><td class="TP_tableleft">GUI: request input</td><td class="TP_tableright"><xsl:value-of select="tebes:GUI/@reaction" /></td></tr>
	  <tr><td class="TP_tableleft">GUI: message displayed  </td><td class="TP_tableright"><xsl:value-of select="tebes:GUI/tebes:GUIDescription[@lg=$enit]"   disable-output-escaping="yes"/></td></tr>
	</xsl:if>
</table>  
</xsl:template>

<!-- x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x  -->


<!--  Test Results extended show,   -->	
<xsl:template match="tebes:Results" mode="summary">
 	
<span style="CURSOR: hand;"  >
<xsl:attribute name="onclick">document.getElementById('tListResults<xsl:value-of select="../@number" />').style.display= document.getElementById('tListResults<xsl:value-of select="../@number" />').style.display=='none' ? '' : 'none';</xsl:attribute>
<xsl:attribute name="title">Click here to get <xsl:value-of select="@id"/> features</xsl:attribute>
<a href='#stay_here'>
<xsl:value-of select="tebes:TestResult/GlobalResult" /> (SUT id<xsl:value-of select="SUT/@idref" />) - <xsl:value-of select="tebes:TestResult/SpecificResult" />
</a>
</span>			

<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
	<xsl:attribute name="id">tListResults<xsl:value-of select="../@number" /></xsl:attribute>
	<xsl:apply-templates select="."  mode="extended"/>
</div>
 
</xsl:template>

<!--  Test Results extended show, TABLE  -->	
<xsl:template match="tebes:Results" mode="extended">
<table class="lightbg">
	<tr><td class="TP_tableleft">Global result</td><td class="TP_tableright"><xsl:value-of select="tebes:TestResult/GlobalResult" /></td></tr>
	<tr><td class="TP_tableleft">SUT id</td><td class="TP_tableright"><xsl:value-of select="SUT/@idref" /></td></tr>
	<tr><td class="TP_tableleft">Specific result</td><td class="TP_tableright">
					<div   >
					<xsl:apply-templates select="tebes:TestResult/SpecificResult"  mode="extended"/>
					</div>
					</td></tr>
	<tr><td class="TP_tableleft">Prerequisites</td><td class="TP_tableright">
					to be defined
					</td></tr>
</table>  
</xsl:template>

<!--  Test Results extended show, TABLE  -->	
<xsl:template match="SpecificResult" mode="extended">
<table class="lightbg">
	<tr><td class="TP_tableleft">Label</td><td class="TP_tableright"><xsl:value-of select="@label" /></td></tr>
	<tr><td class="TP_tableleft">Message</td><td class="TP_tableright"><xsl:value-of select="@message" /></td></tr>
	<tr><td class="TP_tableleft">Result</td><td class="TP_tableright"><xsl:value-of select="." /></td></tr>
</table>  
</xsl:template>



</xsl:stylesheet>
	

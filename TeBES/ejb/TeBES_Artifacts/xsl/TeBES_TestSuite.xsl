<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- TEST-SUITE
       17/12/2013
        -->



<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:tebes="http://www.ubl-italia.org/TeBES"
	xmlns:taml="http://docs.oasis-open.org/ns/tag/taml-201002/"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	>
	
	
	<!-- Importazione dei template comuni -->
	<xsl:import href="TeBES_commons.xsl"/>
	
	
	<xsl:template match="/">
		<html>
			<head>
				<title>Test SUITE descriptor</title>
				<xsl:call-template name="callCSS"/>
			</head>
			<body>
				<xsl:call-template name="TitleAndlogo">
					<xsl:with-param name="parTitle">Test SUITE descriptor</xsl:with-param>
				</xsl:call-template>
				<xsl:apply-templates select="//taml:testAssertionSet"/> 
			</body>
		</html>
	</xsl:template>
	
	
	
	<!--  Test Assertion set show         -->	
	<xsl:template match="taml:testAssertionSet">
		
		<xsl:call-template name="TestAssertionSetHeaderShow"> 
			<xsl:with-param name="parTestAssertionSet" select="."/>
		</xsl:call-template> 
		<br/>
		
		<!--  Test Assertion list  -->	
		<h1>Test Assertions List</h1>
		<xsl:for-each select="taml:testAssertion">
			<xsl:apply-templates select="."  mode="summary"/>
			<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
				<xsl:attribute name="id">tListAssertion<xsl:value-of select="@id"/></xsl:attribute>
				<xsl:apply-templates select="."  mode="extended"/>
			</div>
		</xsl:for-each>
	</xsl:template>
	
	<!--  Test Assertion Set header show         -->	
	<xsl:template name="TestAssertionSetHeaderShow">
		<xsl:param name="parTestAssertionSet"/>
		<table >
			<tr><td class="TP_tableleft">Test Assertions Set Id, Name, version</td>
				<td class="TP_tableright">
					<span style="CURSOR: hand;"  >
						<xsl:attribute name="onclick">document.getElementById('tListReferences').style.display= document.getElementById('tListReferences').style.display=='none' ? '' : 'none';</xsl:attribute>
						<xsl:attribute name="title">Click here to get <xsl:value-of select="$parTestAssertionSet/@setid"/> Features</xsl:attribute>
						<a href='#stay_here'><xsl:attribute name="title">Click here to get <xsl:value-of select="$parTestAssertionSet/@setid"/> features</xsl:attribute>
							<b><xsl:value-of select="$parTestAssertionSet/@setid"/></b> . <xsl:value-of select="$parTestAssertionSet/@setname"/> (TeBES version <xsl:value-of select="$parTestAssertionSet/@tebes:version"/>)
						</a>
					</span>	
				</td>   
			</tr>
		</table>	
		
		<div  style="DISPLAY:none ;margin-left:2%;width:98%" >
			<xsl:attribute name="id">tListReferences</xsl:attribute>
				<table class="lightbg">	
				<xsl:if test="$parTestAssertionSet/taml:common">
				<tr><td class="TP_tableleft">Common</td> <td class="TP_tableright">
					<!-- NON funziona identificazione dei figli con namespace nell'attributo -->
					<xsl:for-each select="$parTestAssertionSet/taml:common/taml:namespaces">
						<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
						<a target="_blank"><xsl:attribute name="href"><xsl:value-of select="namespace::ublin" /></xsl:attribute>
							<xsl:value-of select="namespace::ublin" />
						</a>	
						<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
						<br/>
					</xsl:for-each>
				</td></tr>
				</xsl:if>
				<xsl:if test="$parTestAssertionSet/taml:testAssertionRefList/taml:testAssertionRef">
					<xsl:for-each select="$parTestAssertionSet/taml:testAssertionRefList">
					<tr><td class="TP_tableleft">Reference list</td> <td class="TP_tableright">
						List name: <xsl:value-of select="@listname" />
						<xsl:for-each select="taml:testAssertionRef">
							<xsl:apply-templates select="."  mode="extended"/>
						</xsl:for-each>
					</td></tr>
					</xsl:for-each>
				</xsl:if>
			</table>
			
		</div>	
	</xsl:template>
	
	
	<!--  Test Assertion show, table         -->	
	<xsl:template match="taml:testAssertion" mode="summary">
		<table >
			<tr><td class="TP_tableleft">
				<span style="CURSOR: hand;"  >
					<xsl:attribute name="onclick">document.getElementById('tListAssertion<xsl:value-of select="@id"/>').style.display= document.getElementById('tListAssertion<xsl:value-of select="@id"/>').style.display=='none' ? '' : 'none';</xsl:attribute>
					<xsl:attribute name="title">Click here to get <xsl:value-of select="@id"/> features</xsl:attribute>
					<a href='#stay_here'><xsl:attribute name="title">Click here to get <xsl:value-of select="@id"/> testAssertion features</xsl:attribute>
					<xsl:value-of select="@id"/>
					</a>
					</span>		
			</td>
				<td class="TP_tableright">
					<span style="CURSOR: hand;"  >	
					<xsl:attribute name="onclick">document.getElementById('tListAssertion<xsl:value-of select="@id"/>').style.display= document.getElementById('tListAssertion<xsl:value-of select="@id"/>').style.display=='none' ? '' : 'none';</xsl:attribute>
						<xsl:attribute name="title">Click here to get <xsl:value-of select="@id"/> features</xsl:attribute>
						<a href='#stay_here'><xsl:attribute name="title">Click here to get <xsl:value-of select="@id"/> testAssertion features</xsl:attribute>
					<xsl:value-of select="@name" disable-output-escaping="yes"/> (target: <xsl:value-of select="taml:target"/>, <xsl:value-of select="taml:target/@type"/>)
					</a>
					</span>
				</td>
				
			</tr>
		</table>
	</xsl:template>
	
	<!--  Test Assertion show, table         -->	
	<xsl:template match="taml:testAssertion" mode="extended">
		<table  class="lightbg">
			
			<tr><td class="TP_tableleft">Test Assertion Id</td>	<td class="TP_tableright"><b><xsl:value-of select="@id"/></b> . <xsl:value-of select="@name"/></td></tr>
			<tr><td class="TP_tableleft">Description</td> <td class="TP_tableright"><xsl:value-of select="taml:description" disable-output-escaping="yes"/></td></tr>
			
			<xsl:for-each select="taml:var" > 
			<tr><td class="TP_tableleft">Var</td> <td class="TP_tableright">
				vname=<b><xsl:value-of select="@vname" /></b>,<br/>
				<xsl:if test="contains(@vtype,'http:')">
					vtype=<a target="_blank"><xsl:attribute name="href"><xsl:value-of select="@vtype" /></xsl:attribute><xsl:value-of select="@vtype" /></a>
				</xsl:if>
				<xsl:if test="not(contains(@vtype,'http:'))">
					vtype=<xsl:value-of select="@vtype" />
				</xsl:if>
				<br/>
				value=
				<xsl:if test="contains(.,'http:')">
					<a target="_blank">
						<xsl:attribute name="href"><xsl:value-of select="." /></xsl:attribute>
						<xsl:value-of select="." />
					</a>
				</xsl:if>
				<xsl:if test="not(contains(.,'http:'))">
					<xsl:value-of select="." />
				</xsl:if>
				
				
			</td></tr>
			</xsl:for-each>
			<tr><td class="TP_tableleft">Normative Sources</td> <td class="TP_tableright">
				<xsl:for-each select="taml:normativeSource">
					<xsl:apply-templates select="."  mode="extended"/>
					
				</xsl:for-each>
			</td></tr>
			<tr><td class="TP_tableleft">Target</td> <td class="TP_tableright"><xsl:value-of select="taml:target"/>
				, type=<xsl:value-of select="taml:target/@type" />
				<xsl:if test="taml:target/@lg">
				, language=<xsl:value-of select="taml:target/@lg" />
				</xsl:if>	
			</td></tr>
			<tr><td class="TP_tableleft">Predicate</td> <td class="TP_tableright">
				
				<span>
				<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
				<xsl:for-each select="taml:var">
					<xsl:if test="@vname=../taml:predicate">
						<xsl:attribute name="title">var <xsl:value-of select="@vname"/>: 
<xsl:value-of select="."/></xsl:attribute>
					</xsl:if>	
				</xsl:for-each>
				<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
				<b><xsl:value-of select="taml:predicate"/></b><br/>
				</span>
			</td>
			</tr>
			<xsl:if test="taml:prerequisite">
				<xsl:for-each select="taml:prerequisite">	
				<tr><td class="TP_tableleft">Prerequisite</td> <td class="TP_tableright">
					<span>
						<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
						<xsl:for-each select="../taml:var">
							<xsl:if test="@vname=../taml:prerequisite">
								<xsl:attribute name="title">var <xsl:value-of select="@vname"/>: 
<xsl:value-of select="."/></xsl:attribute>
							</xsl:if>	
						</xsl:for-each>
						<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
						<b><xsl:value-of select="."/></b>
					</span>	
				</td></tr>
				</xsl:for-each>
			</xsl:if>
			<tr><td class="TP_tableleft">Prescription</td> <td class="TP_tableright"><xsl:value-of select="taml:prescription"/> (level=<xsl:value-of select="taml:prescription/@level"/>)</td></tr>	
			
			<tr><td class="TP_tableleft">Report(s)</td> <td class="TP_tableright">
				<xsl:for-each select="taml:report" >
					<xsl:apply-templates select="."  mode="extended" />
				</xsl:for-each>	
			</td></tr>
			
		</table>
	</xsl:template>	


	<!--  Report show, table         -->	
	<xsl:template match="taml:report"  mode="extended">
		<table  class="lightbg">
			<tr><td class="TP_tableleft">Result label</td> <td class="TP_tableright"><b><xsl:value-of select="@label"/></b></td></tr>
			<tr><td class="TP_tableleft">Report value</td> <td class="TP_tableright"><xsl:value-of select="."/></td></tr>
			<xsl:if test="@when">
				<tr><td class="TP_tableleft">Condition</td>	<td class="TP_tableright"><xsl:value-of select="@when"/></td></tr>
			</xsl:if>
			<tr><td class="TP_tableleft">Message</td> <td class="TP_tableright"><xsl:value-of select="@message"/></td></tr>
			
		</table>
	</xsl:template>


	

	<!--  Test Assertion normativeSource show, table         -->	
	<xsl:template match="taml:normativeSource" mode="extended">
		<table  class="lightbg">
			<tr><td class="TP_tableleft">Source Item</td> <td class="TP_tableright"><xsl:value-of select="taml:textSourceItem"/></td></tr>
			<tr><td class="TP_tableleft">Reference</td>	<td class="TP_tableright"><xsl:value-of select="taml:refSourceItem"/></td></tr>
			<tr><td class="TP_tableleft">Comment</td> <td class="TP_tableright"><xsl:value-of select="taml:comment"/></td></tr>
			
			<tr><td class="TP_tableleft">Normative Sources</td> <td class="TP_tableright">
				<xsl:for-each select="taml:normativeSource">
					<xsl:apply-templates select="."/>
				</xsl:for-each>
			</td></tr>
		</table>
	</xsl:template>
	
	
	<!--  Test Assertion Set testAssertionRef show, table         -->	
	<xsl:template match="taml:testAssertionRef" mode="extended">
		<table  class="lightbg">
			<tr><td class="TP_tableleft">TA ref. ident.</td> <td class="TP_tableright"><xsl:value-of select="@taid"/> (version <xsl:value-of select="@tebes:version"/>)</td></tr>
			<tr><td class="TP_tableleft">TA ref. name</td>	<td class="TP_tableright"><xsl:value-of select="@name"/></td></tr>
			<tr><td class="TP_tableleft">Source document</td> <td class="TP_tableright">
				<a target="_blank">
					<xsl:attribute name="href"><xsl:value-of select="@sourcedoc"/></xsl:attribute>
					<xsl:value-of select="@sourcedoc"/>
				</a>
			</td></tr>
		</table>
	</xsl:template>
	
	
	
 </xsl:stylesheet>
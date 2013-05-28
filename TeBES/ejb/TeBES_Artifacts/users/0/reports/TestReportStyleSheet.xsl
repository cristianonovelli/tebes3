<?xml version='1.0' encoding='ISO-8859-1'?>

 <xsl:stylesheet version='1.0'
    xmlns:xsl='http://www.w3.org/1999/XSL/Transform'
	xmlns:tebes="http://www.ubl-italia.org/TeBES"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.ubl-italia.org/TeBES/xmlschemas/TAMLhttp://www.ubl-italia.org/TeBES/xmlschemas/TAML/testAssertionMarkupLanguage.xsd">

    <xsl:template match='/'>
    <html>
    <head>
    <title>Test Report</title>
    <link rel="stylesheet" type="text/css" href="TestReport_graficaesterna.css"/>  
   
<script>   
function ShowTable(number)
{if(document.getElementById('tablespace'+number).style.display=="none")
   {document.getElementById('tablespace'+number).style.display="table";}
else if(document.getElementById('tablespace'+number).style.display=="table")
       {document.getElementById('tablespace'+number).style.display="none"
	   document.getElementById('TestAction_Description'+number+'table').style.display="none";
	   document.getElementById('TestAction_UserSUT'+number+'table').style.display="none";}
}

function ShowDetail(what,other,number)
{if(document.getElementById(what).style.display=="none")
   {document.getElementById(what).style.display="block";}
else if(document.getElementById(what).style.display=="block")
       {document.getElementById(what).style.display="none";}

document.getElementById(other).style.display="none";

}

</script>
   
    </head>
    <body>
	
    <div class='TP_maintitle'>Sample of a Test Report</div>
		
	<div class='TP_space'></div>
	
    <table class='TP_maintable'>
		    <tr>
			    <td class='TP_maintableleft'>Name</td>           <td class='TP_maintableright'><xsl:value-of select='/tebes:Report/@name'/></td>   
		    </tr>
			<tr>
			    <td class='TP_maintableleft'>Description</td>    <td class='TP_maintableright'><xsl:value-of select='/tebes:Report/@description'/></td>
			</tr>
			<tr>
			    <td class='TP_maintableleft'>id</td>             <td class='TP_maintableright'><xsl:value-of select='/tebes:Report/@id'/></td>
			</tr>
			<tr>
			    <td class='TP_maintableleft'>User ID</td>        <td class='TP_maintableright'><xsl:value-of select='/tebes:Report/@userID'/></td>
			</tr>
			<tr>
			    <td class='TP_maintableleft'>Date time</td>      <td class='TP_maintableright'><xsl:value-of select='/tebes:Report/@datetime'/></td>
			</tr>
			<tr>
			    <td class='TP_maintableleft'>State</td>          <td class='TP_maintableright'><xsl:value-of select='/tebes:Report/@state'/></td>
			</tr>
	</table>
	
	<div class='TP_space'></div>
	
	<div class='TP_listtitle'>Test Session</div>
	
	<div class='TP_space'></div>
	
	<table class='TP_maintable'>
	    <tr>
		    <td class='TP_maintableleft'>id</td>                 <td class='TP_maintableright'><xsl:value-of select='/tebes:Report/tebes:Session/@id'/></td>
	    </tr>
		<tr>
		    <td class='TP_maintableleft'>State</td>              <td class='TP_maintableright'><xsl:value-of select='/tebes:Report/tebes:Session/tebes:State'/></td>
		</tr>
		<tr>
		    <td class='TP_maintableleft'>Start Date Time</td>    <td class='TP_maintableright'><xsl:value-of select='/tebes:Report/tebes:Session/tebes:StarteDateTime'/></td>
		</tr>
	    <tr>
		    <td class='TP_maintableleft'>Last Date Time</td>     <td class='TP_maintableright'><xsl:value-of select='/tebes:Report/tebes:Session/tebes:LastDateTime'/></td>
		</tr>
		<tr>
		    <td rowspan='2' class='TP_maintableleft'>User</td>
            <td class='TP_maintableright'>
			    <table>
                    <tr>
					    <td>id</td>     <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:User/@id'/></td>
					</tr>
                    <tr>
					    <td>Name</td>     <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:User/tebes:Name'/></td>
					</tr>
                    <tr>
					    <td>Surname</td>     <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:User/tebes:Surname'/></td>
					</tr>					
				</table>			
			</td>
		</tr>
		<tr>
		    <td class='TP_maintableright'>
			    <table>
			        <tr>
					    <td rowspan='3'>SUT List</td>   <td>id</td>       <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:User/tebes:SUTList/tebes:SUT/@id'/></td>
					</tr>
					<tr>
					                                    <td>SUT type</td> <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:User/tebes:SUTList/tebes:SUT/tebes:SUTtype'/></td>
					</tr>
					<tr>
					                                    <td>Modality</td> <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:User/tebes:SUTList/tebes:SUT/tebes:Modality/@type'/></td>
					</tr>
			    </table>
			</td>
		</tr>
		<tr>
		    <td class='TP_maintableleft'>Test Plan</td>
			<td class='TP_maintableright'>
			    <table>
				    <tr>
					    <td>id</td>       <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:TestPlan/@id'/></td>
					</tr>
				    <tr>
				    	<td>version</td>  <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:TestPlan/@version'/></td>
					</tr>
				    <tr>
				    	<td>Date Time</td>  <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:TestPlan/tebes:DateTime'/></td>
					</tr>
				    <tr>
				    	<td>Name</td>  <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:TestPlan/tebes:Name'/></td>
					</tr>
				    <tr>
				    	<td>Description</td>  <td><xsl:value-of select='/tebes:Report/tebes:Session/tebes:TestPlan/tebes:Description'/></td>
					</tr>
				</table>
			</td>
		</tr>
    </table>

	<div class='TP_space'></div>
	 
	<div class='TP_listtitle'>Test Action List</div>
 
    <div class='TP_space'></div>

	<xsl:for-each select='tebes:Report/tebes:TestPlanExecution/tebes:TestActionList/tebes:TestAction'>    
        <xsl:call-template name="ActionReport">
            <xsl:with-param name='TestAction_Number' select='@number'/>
		    <xsl:with-param name='TestAction_Name' select='tebes:ActionName'/>
		    <xsl:with-param name='TestAction_Description' select='tebes:ActionDescription'/>
		    <xsl:with-param name='TestAction_Test_lg' select='tebes:Test/@lg'/>
		    <xsl:with-param name='TestAction_Test_type' select='tebes:Test/@type'/>
		    <xsl:with-param name='TestAction_Test_jumpPrerequisites' select='tebes:Test/@jumpPrerequisites'/>
		    <xsl:with-param name='TestAction_Test_location_url' select='tebes:Test/@location'/>
			<xsl:with-param name='TestAction_Test_location' select='tebes:Test'/>
			<xsl:with-param name='TestAction_SUTidref' select='tebes:Results/SUT/@idref'/>
			<xsl:with-param name='TestAction_GlobalResult' select='tebes:Results/tebes:TestResult/GlobalResult'/>
			<xsl:with-param name='TestAction_SpecificResult_label' select='tebes:Results/tebes:TestResult/SpecificResult/@label'/>			
			<xsl:with-param name='TestAction_SpecificResult_message' select='tebes:Results/tebes:TestResult/SpecificResult/@message'/>
			<xsl:with-param name='TestAction_SpecificResult' select='tebes:Results/tebes:TestResult/SpecificResult'/>
			<xsl:with-param name='TestAction_Prerequisite' select='tebes:Results/tebes:PrerequisiteList/Prerequisite'/>
        </xsl:call-template>
	</xsl:for-each>
	
    </body>
    </html>
	</xsl:template>

	<xsl:template name='ActionReport'>
        <xsl:param name='TestAction_Number'/>
	    <xsl:param name='TestAction_Name'/>
	    <xsl:param name='TestAction_Description'/>
		<xsl:param name='TestAction_Test_lg'/>
		<xsl:param name='TestAction_Test_type'/>
		<xsl:param name='TestAction_Test_jumpPrerequisites'/>
        <xsl:param name='TestAction_Test_location_url'/>
		<xsl:param name='TestAction_Test_location'/>
		<xsl:param name='TestAction_SUTidref'/>
		<xsl:param name='TestAction_GlobalResult'/>
		<xsl:param name='TestAction_SpecificResult_label'/>
		<xsl:param name='TestAction_SpecificResult_message'/>
		<xsl:param name='TestAction_SpecificResult'/>
		<xsl:param name='TestAction_Prerequisite'/>		
		
    <div class='TP_listitem'>
        <xsl:attribute name="onclick">ShowTable('<xsl:value-of select="$TestAction_Number"/>')</xsl:attribute>
        <table>
		    <tr>
		        <td>
				    <xsl:value-of select="$TestAction_Number"/>
				</td>
				<td>
				    <xsl:attribute name="title">Click here to get <xsl:value-of select="$TestAction_Name"/> Features</xsl:attribute>
			        <a href='#stay_here'>
	                    <xsl:attribute name="title">Click here to get <xsl:value-of select="$TestAction_Name"/> Features</xsl:attribute>
				        <xsl:value-of select="$TestAction_Name"/>
			        </a>
				</td>
		    </tr>
		</table>
    </div>	
	
	<div style='display:none'>
	    <xsl:attribute name='id'>tablespace<xsl:value-of select="$TestAction_Number"/></xsl:attribute>
		<table class='TP_listtable'>
		    <tr>
			    <td class='TP_listtableleft'>
				    Test Action Number
				</td>            
				<td class='TP_listtableright'>
				    <xsl:value-of select="$TestAction_Number"/>
				</td>
			</tr>
		    <tr>
			    <td class='TP_listtableleft'>
				    Test Action Name
				</td>
				<td class='TP_listtableright'>
				    <xsl:value-of select="$TestAction_Name"/>
				</td>
			</tr>
		    <tr>
			    <td class='TP_listtableleft'>
				    Test Action Description
				</td>
				<td class='TP_listtableright'>
				<table>
				<tr>
				<td class='TP_listtableright_more'>
				    <xsl:value-of select="$TestAction_Description"/>
				</td>
				<td>
						        <a href='#stay_here'>
						        <xsl:attribute name="onclick">
						        ShowDetail('TestAction_Description<xsl:value-of select="$TestAction_Number"/>table','TestAction_UserSUT<xsl:value-of select="$TestAction_Number"/>table','<xsl:value-of select="$TestAction_Number"/>')
						        </xsl:attribute>
						        [More...]
					            </a>
				</td>
				</tr>
				</table>
				</td>
			</tr>
		    <tr>
			    <td class='TP_listtableleft'>
				    Test					  
				</td>
				<td class='TP_listtable'>
				    <table>
					    <tr>
						    <td class='TP_sublisttableleft'>
							    Language
						    </td>
                            <td class='TP_sublisttableright'>
							    <xsl:value-of select="$TestAction_Test_lg"/>
							</td>
						</tr> 
						<tr>
						    <td class='TP_sublisttableleft'>
							    Type
							</td>
                            <td class='TP_sublisttableright'>
							    <xsl:value-of select="$TestAction_Test_type"/>
							</td>
						</tr>
						<tr>
						    <td class='TP_sublisttableleft'>
							    Jump Prerequisites
							</td>
                            <td class='TP_sublisttableright'>
							    <xsl:value-of select="$TestAction_Test_jumpPrerequisites"/>
							</td>
						</tr>
						<tr>
						    <td class='TP_sublisttableleft'>
							    Location
							</td>
                            <td class='TP_sublisttableright'>
						    <a>
			                   <xsl:attribute name="href">
                               <xsl:value-of select="$TestAction_Test_location_url"/>
                               </xsl:attribute>
                               <xsl:value-of select='$TestAction_Test_location'/>
		                    </a>
							</td>
						</tr>							
					</table>
				</td>
			</tr>
		    <tr>
			    <td class='TP_listtableleft'>
				    Results					  
				</td>
				<td class='TP_listtable'>
				    <table>
					    <tr>
						    <td class='TP_sublisttableleft'>
							    SUT idref
						    </td>
                            <td class='TP_sublisttableright'>
							    <xsl:value-of select="$TestAction_SUTidref"/>
							</td>
						</tr> 
						<tr>
						    <td class='TP_sublisttableleft'>
							    Global Result
							</td>
                            <td class='TP_sublisttableright'>
							    <xsl:value-of select="$TestAction_GlobalResult"/>
							</td>
						</tr>
						<tr>
						    <td rowspan='2' class='TP_sublisttableleft'>Specific Result</td>
                            <td class='TP_sublisttableright'>label</td>
							<td class='TP_sublisttableright'><xsl:value-of select="$TestAction_SpecificResult_label"/></td>
						</tr>
						<tr>
                            <td class='TP_sublisttableright'>message</td>
							<td class='TP_sublisttableright'><xsl:value-of select="$TestAction_SpecificResult_message"/></td>
						</tr>
					</table>
				</td>
			</tr>
		    <tr>
			    <td class='TP_listtableleft'>
				    Prerequisite List
				</td>
				<td class='TP_listtableright'>
				    <table>
					    <tr>
						    <td>Prerequisite</td>
							<td><xsl:value-of select="$TestAction_Prerequisite"/></td>
						</tr>
					</table>
				</td>
			</tr>
        </table>
    </div>
	
	<div style='display:none'>
	    <xsl:attribute name='id'>TestAction_Description<xsl:value-of select="$TestAction_Number"/>table</xsl:attribute>
	    <div class='TP_space'></div>
	<div class='TP_infoDescription'>
	        Here more details about <xsl:value-of select="$TestAction_Description"/> are possibly available
	</div>
	</div>
	
	<div class='TP_space'></div>
	
 </xsl:template>




	</xsl:stylesheet>

<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>User Manager</title>
<!-- C:\Java\examples\jsf12-tut-2 -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/main.css" />
</head>

<body>
<f:view locale="#{LocalizationController.locale}">
	<h4>Suppliers</h4>
	
		<h:messages infoClass="infoClass" errorClass="errorClass"
		layout="table" globalOnly="true" />
		
	<h:form>
		<h:commandLink binding="#{registerController.addNewCommand}"
			action="#{registerController.addNew}" value="Add New..." />
	</h:form>

	<h:form binding="#{registerController.form}" rendered="false"
		styleClass="form">

		<h:panelGrid columns="10">
			
			<h:outputLabel value="Code" for="identificationCode" accesskey="f" />
			<h:inputText id="identificationCode" label="Code" required="true"
				value="#{registerController.supplier.identificationCode}" size="10" />												
						
		</h:panelGrid>
		
		<h:panelGrid columns="10">
			<h:outputLabel value="Transmission" for="Transmission" accesskey="f" />
			<h:selectOneMenu id="Transmission" value="#{registerController.tempTransmissionTypeDescription}">
      			<f:selectItems value="#{registerController.transmissionMenuList}" />
			</h:selectOneMenu>
		</h:panelGrid>
			
		<h:panelGrid columns="10">
			<h:outputLabel value="Translation" for="Translation" accesskey="f" />
			<h:selectOneMenu id="Translation" value="#{registerController.tempTranslationTypeDescription}">
      			<f:selectItems value="#{registerController.translationMenuList}" />
			</h:selectOneMenu>
		</h:panelGrid>
			
		<h:panelGroup>
			<h:commandButton binding="#{registerController.persistCommand}"
				action="#{registerController.persist}" />
		</h:panelGroup>
	</h:form>
		
	<BR />
	
	<h:form>
		<h:dataTable value="#{registerController.supplierRegister}" var="supplier"
			rowClasses="oddRow, evenRow"
			rendered="#{not empty registerController.supplierRegister}"
			styleClass="contactTable" headerClass="headerTable"
			columnClasses="normal,centered">
			<h:column>
				<f:facet name="header">
					<h:column>
						<h:outputText value="Id" />
					</h:column>
				</f:facet>
				<h:outputText value="#{supplier.supplierId}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:column>
						<h:outputText value="Code" />
					</h:column>
				</f:facet>
				<h:outputText value="#{supplier.identificationCode}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:column>
						<h:outputText value="Trasmission" />
					</h:column>
				</f:facet>
				<h:outputText value="#{supplier.transmissionType.transmissionTypeDescription}" />
			</h:column>			
			<h:column>
				<f:facet name="header">
					<h:column>
						<h:outputText value="Translation" />
					</h:column>
				</f:facet>
				<h:outputText value="#{supplier.translationType.translationTypeDescription}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:column>
						<h:outputText value="Actions" />
					</h:column>
				</f:facet>
				<h:panelGrid columns="2">
					<h:commandLink value="remove" action="#{registerController.remove}" onclick="return confirm('Are you sure?');">
						<f:setPropertyActionListener
							target="#{registerController.selectedSupplierId}" value="#{supplier.supplierId}" />
					</h:commandLink>
					<h:commandLink value="edit" action="#{registerController.read}">
						<f:setPropertyActionListener
							target="#{registerController.selectedSupplierId}" value="#{supplier.supplierId}" />
					</h:commandLink>
				</h:panelGrid>
			</h:column>

		</h:dataTable>
	</h:form>
</f:view>
</body>

</html>
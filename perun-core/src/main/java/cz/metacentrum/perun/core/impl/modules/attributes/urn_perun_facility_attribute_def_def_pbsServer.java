package cz.metacentrum.perun.core.impl.modules.attributes;

import java.util.ArrayList;
import java.util.List;

import cz.metacentrum.perun.core.api.Attribute;
import cz.metacentrum.perun.core.api.AttributeDefinition;
import cz.metacentrum.perun.core.api.AttributesManager;
import cz.metacentrum.perun.core.api.Facility;
import cz.metacentrum.perun.core.api.exceptions.InternalErrorException;
import cz.metacentrum.perun.core.api.exceptions.WrongAttributeAssignmentException;
import cz.metacentrum.perun.core.api.exceptions.WrongAttributeValueException;
import cz.metacentrum.perun.core.api.exceptions.WrongReferenceAttributeValueException;
import cz.metacentrum.perun.core.impl.PerunSessionImpl;
import cz.metacentrum.perun.core.implApi.modules.attributes.FacilityAttributesModuleAbstract;
import cz.metacentrum.perun.core.implApi.modules.attributes.FacilityAttributesModuleImplApi;

/**
 *
 * @author Michal Šťava <stavamichal@gmail.com>
 */
public class urn_perun_facility_attribute_def_def_pbsServer extends FacilityAttributesModuleAbstract implements FacilityAttributesModuleImplApi {

	public void checkAttributeValue(PerunSessionImpl perunSession, Facility facility, Attribute attribute) throws InternalErrorException, WrongAttributeValueException, WrongReferenceAttributeValueException {
		String pbsServer = null;
		if(attribute.getValue() != null) {
			pbsServer = (String) attribute.getValue();
		} else {
			throw new WrongAttributeValueException(attribute, "PbsServer cannot be null.");
		}

		//TODO better method for searching Facility by querry in DB
		List<Facility> allFacilities = new ArrayList<Facility>();
		allFacilities = perunSession.getPerunBl().getFacilitiesManagerBl().getFacilities(perunSession);
		boolean success = false;
		for(Facility f: allFacilities) {
			if(f.getName().equals(pbsServer)) {
				success = true;
				break;
			}
		}
		if(!success) throw new WrongAttributeValueException(attribute, "There is no such facility with the same name like this pbsServer");
	}

	public Attribute fillAttribute(PerunSessionImpl session, Facility facility, AttributeDefinition attribute) throws InternalErrorException, WrongAttributeAssignmentException {
		return new Attribute(attribute);
	}

	public AttributeDefinition getAttributeDefinition() {
		AttributeDefinition attr = new AttributeDefinition();
		attr.setNamespace(AttributesManager.NS_FACILITY_ATTR_DEF);
		attr.setFriendlyName("pbsServer");
		attr.setDisplayName("PBS server");
		attr.setType(String.class.getName());
		attr.setDescription("PBS server which controls this facility.");
		return attr;
	}

}

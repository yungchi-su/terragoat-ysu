package com.cox.cb.cbma.identityservice.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cox.cb.cbma.starter.config.ApplicationConfig;
import com.cox.cb.cbma.common.exception.CBMAUnauthorizedException;
import com.cox.cb.cbma.identityservice.enums.SSOPageIdType;
import com.cox.cb.cbma.identityservice.integration.okta.OktaAlarmSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaCBFOProductBuyFlowSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaCbCoreSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaEssentialOnlineBackupProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaIBillSSOProviderImpl;
// checkov:skip=CKV_SECRET_6<Not a secret key>
import com.cox.cb.cbma.identityservice.integration.okta.OktaMalblockSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSSOPersistProvider;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSbwgSSoproviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSdwanRSSSO3ProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSdwanRSSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSdwanSSOProviderImpl;
// checkov:skip=CKV_SECRET_6<Not a secret key>
import com.cox.cb.cbma.identityservice.integration.okta.OktaManagedRouterSecuritySSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSmartBillSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaTollFreeSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaUWPSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.VSBAppDirectProviderImpl;
// checkov:skip=CKV_SECRET_6<Not a secret key>
import com.cox.cb.cbma.identityservice.integration.okta.OktaManagedSecuritySSOProviderImpl;


@Component
public class PageIdFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(PageIdFactory.class);
	
	@Autowired
	private OktaTollFreeSSOProviderImpl oktaTollFreeSSOProviderImpl;
	
	@Autowired
	private OktaSdwanSSOProviderImpl oktaSdwanSSOProviderImpl;
	
	@Autowired
	private OktaSdwanRSSSOProviderImpl oktaSdwanSSORSProviderImpl;
	
	@Autowired
	private OktaSdwanRSSSO3ProviderImpl oktaSdwanSSORS3ProviderImpl;
	
	@Autowired
	private OktaIBillSSOProviderImpl oktaIBillSSOProviderImpl;
	
	@Autowired
	private OktaMalblockSSOProviderImpl oktaMalblockSSOProviderImpl;
	
	@Autowired
	private OktaAlarmSSOProviderImpl oktaAlarmSSOProviderImpl; 
	
	@Autowired
	private OktaUWPSSOProviderImpl oktaUWPSSOProviderImpl;
	
	@Autowired
	private OktaCbCoreSSOProviderImpl oktaCbCoreSSOProviderImpl;
	
	@Autowired
	private OktaManagedSecuritySSOProviderImpl oktaManagedSecuritySSOProviderImpl;
	
	@Autowired
	private OktaSmartBillSSOProviderImpl oktaSmartBillSSOProviderImpl;
	
	@Autowired
	private OktaManagedRouterSecuritySSOProviderImpl oktaManagedRouterSecuritySSOProviderImpl;
	
	@Autowired
	private OktaEssentialOnlineBackupProviderImpl oktaEssentialOnlineBackupProviderImpl;
	
	@Autowired
	private OktaCBFOProductBuyFlowSSOProviderImpl oktaCBFOProductBuyFlowSSOProviderImpl;

	@Autowired
	private OktaSbwgSSoproviderImpl oktaSbwgSSoproviderImpl;
	
	
	@Autowired
	private VSBAppDirectProviderImpl vsbAppDirectProviderImpl;
	
	private static final String TOLLFREE = "TOLLFREE";
	
	private static final String SDWAN = "SDWAN";
	
	private static final String RSSDWAN = "RSSDWAN";
	
	private static final String RSSDWAN3 = "RSSDWAN3";
	
	private static final String IBILL = "IBILL";
	
	private static final String AKAMAI_BASIC = "AKAMAI_BASIC";
	
	private static final String AKAMAI_PREMIUM = "AKAMAI_PREMIUM";
	
	private static final String ALARM = "ALARM";
	
	private static final String CB_CORE = "CB_CORE";
	
	private static final String UNIFIED_WIFI_PORTAL = "UNIFIED_WIFI_PORTAL";
	
	private static final String UNIFIED_WIFI_PORTAL_HN = "UNIFIED_WIFI_PORTAL_HN";
	
	private static final String SMART_BILL = "SMART_BILL";
	
	private static final String MANAGED_ROUTER_SECURITY = "MANAGED_ROUTER_SECURITY";
	
	private static final String RAPID_SCALE = "RAPID_SCALE";
	
	private static final String CBFORCE = "CBFORCE";
	
	private static final String ENDPOINT_PROTECT = "ENDPOINT_PROTECT";
	
	private static final String VSB_APPDIRECT = "VSB_APPDIRECT";

	private static final String SBWG = "SBWG";
	
	private static final String MALBLOCK_CBFORCE = "MALBLOCK_CBFORCE";
	
	
	public OktaSSOPersistProvider samlPersistence(SSOPageIdType pageId) throws CBMAUnauthorizedException {
		LOGGER.info("Inside PageIdFactory, determining sso provider : {}", pageId);
		switch (pageId.toString()) {
		case TOLLFREE:
			return oktaTollFreeSSOProviderImpl;
		case SDWAN:
			return oktaSdwanSSOProviderImpl;
		case RSSDWAN:
			return oktaSdwanSSORSProviderImpl; 
		case AKAMAI_BASIC:
			return oktaMalblockSSOProviderImpl;
		case AKAMAI_PREMIUM:
			return oktaMalblockSSOProviderImpl;
		case ALARM:
			return oktaAlarmSSOProviderImpl;
		case CB_CORE:
			return oktaCbCoreSSOProviderImpl;
		case RSSDWAN3:
			return oktaSdwanSSORS3ProviderImpl;
		case IBILL:
			return oktaIBillSSOProviderImpl;
		case MALBLOCK_CBFORCE:
			return oktaCBFOProductBuyFlowSSOProviderImpl;
		default:
			return samlPersistenceupdate(pageId);
		}
	}
	
	public OktaSSOPersistProvider samlPersistenceupdate(SSOPageIdType pageId) throws CBMAUnauthorizedException {
		switch (pageId.toString()) {
		case UNIFIED_WIFI_PORTAL:
			return oktaUWPSSOProviderImpl;
		case UNIFIED_WIFI_PORTAL_HN:
			return oktaUWPSSOProviderImpl;
		case SMART_BILL:
			return oktaSmartBillSSOProviderImpl;
		case MANAGED_ROUTER_SECURITY:
			return oktaManagedSecuritySSOProviderImpl;
		case RAPID_SCALE:
			return oktaManagedRouterSecuritySSOProviderImpl;
		case ENDPOINT_PROTECT:
			return oktaEssentialOnlineBackupProviderImpl;
		default:
			return samlPersistenceupdates(pageId);
		}
	}
	
	public OktaSSOPersistProvider samlPersistenceupdates(SSOPageIdType pageId) throws CBMAUnauthorizedException {
		switch (pageId.toString()) {
		case VSB_APPDIRECT:
			return vsbAppDirectProviderImpl;
		case SBWG:
			return oktaSbwgSSoproviderImpl;
		default:
			String pageIdList = ApplicationConfig.getProperty("cbforce.pageId.list");
			List<String> pageIds =  Arrays.asList(pageIdList.split(","));
			if(pageIds.contains(pageId.toString())) {
				return oktaCBFOProductBuyFlowSSOProviderImpl;
			}
			else {
				throw new CBMAUnauthorizedException("Not a valid SSO");
			}
			
		}
	}
}
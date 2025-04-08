package com.cox.cb.cbma.identityservice.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cox.cb.cbma.common.model.BaseResponse;
import com.cox.cb.cbma.connectors.cacheservice.CacheServiceAdaptor;
import com.cox.cb.cbma.connectors.cacheservice.bean.Token;
import com.cox.cb.cbma.connectors.cacheservice.bean.Token.TokenType;
import com.cox.cb.cbma.connectors.coxbiz.domain.BaseAccount;
import com.cox.cb.cbma.identityservice.config.CacheRestClientConfig;
import com.cox.cb.cbma.identityservice.enums.SSOPageIdType;
import com.cox.cb.cbma.identityservice.integration.cache.CacheSSOPersistProviderImpl;
import com.cox.cb.cbma.identityservice.integration.coxbiz.ExternalOktaUserCoxBizProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.IdentityServiceOktaProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.IntrospectTokenResponse;
import com.cox.cb.cbma.identityservice.integration.okta.OktaAlarmSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaCBFOProductBuyFlowSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaCbCoreSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaEssentialOnlineBackupProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaIBillSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaMalblockSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSSOProviderHelper;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSbwgSSoproviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSdwanRSSSO3ProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSdwanRSSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSdwanSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaSmartBillSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaManagedRouterSecuritySSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaTollFreeSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.OktaUWPSSOProviderImpl;
import com.cox.cb.cbma.identityservice.integration.okta.VSBAppDirectProviderImpl;
import com.cox.cb.cbma.identityservice.model.AlarmSso;
import com.cox.cb.cbma.identityservice.model.CBCoreSso;
import com.cox.cb.cbma.identityservice.model.CBFOBuyFlowSso;
import com.cox.cb.cbma.identityservice.model.EssentialOnlineBackupFlowSso;
import com.cox.cb.cbma.identityservice.model.IBillSso;
import com.cox.cb.cbma.identityservice.model.MalblockSso;
import com.cox.cb.cbma.identityservice.model.ManagedRouterSecuritySSO;
import com.cox.cb.cbma.identityservice.integration.okta.OktaManagedSecuritySSOProviderImpl;
import com.cox.cb.cbma.identityservice.model.ManagedSecuritySso;
import com.cox.cb.cbma.identityservice.model.RSSdwan3Sso;
import com.cox.cb.cbma.identityservice.model.SamlEnrichmentAttributes;
import com.cox.cb.cbma.identityservice.model.SamlEnrichmentRequest;
import com.cox.cb.cbma.identityservice.model.SamlEnrichmentResponse;
import com.cox.cb.cbma.identityservice.model.SamlfetchDetailsRequest;
import com.cox.cb.cbma.identityservice.model.SbwgSso;
import com.cox.cb.cbma.identityservice.model.SdwanSso;
import com.cox.cb.cbma.identityservice.model.TollFreeSso;
import com.cox.cb.cbma.identityservice.model.UWPSSODetails;
import com.cox.cb.cbma.identityservice.model.VSBAppDirectFlowSso;
import com.cox.cb.cbma.starter.interceptor.ServiceResponseBuilder;
import com.cox.cb.cbma.common.model.RequestHeaders;
import com.cox.cb.cbma.common.model.ResponseStatus;  
import com.cox.cb.cbma.starter.util.security.UserTokenHelper;

/**
 * @author hitasvi.sutar
 *
 */
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;


/**
 * The Class IdentityServiceTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { com.cox.cb.cbma.identityservice.service.IdentityServiceImpl.class,
		com.cox.cb.cbma.identityservice.service.PageIdFactory.class, com.cox.cb.cbma.starter.config.ApplicationConfig.class, CacheRestClientConfig.class})

@TestPropertySource(locations = "classpath:application-test.properties")
public class IdentityServiceTest {

	@Autowired
    private IdentityService identityService;
	
	
	@MockBean
	private OktaCBFOProductBuyFlowSSOProviderImpl oktaCBFOProductBuyFlowSSOProviderImpl;
	
	@MockBean
	private OktaEssentialOnlineBackupProviderImpl oktaEssentialOnlineBackupProviderImpl;
	
	@MockBean
	private OktaIBillSSOProviderImpl oktaIBillSSOProviderImpl;

	@MockBean
	private ExternalOktaUserCoxBizProviderImpl externalOktaUserCoxBizProviderImpl;
	
	@MockBean
	private UserTokenHelper userTokenHelper;
		
	@MockBean
	private OktaAlarmSSOProviderImpl oktaAlarmSSOProviderImpl;
	
	@MockBean
	IdentityServiceOktaProviderImpl oktaProvider;
	
	@MockBean
	CacheServiceAdaptor cacheAdaptor;
	
	@MockBean
	CacheSSOPersistProviderImpl cacheSSOPersistProviderImpl;
	
	@MockBean
	private OktaTollFreeSSOProviderImpl oktaTollFreeSSOProviderImpl;
	
	@MockBean
	private OktaSdwanSSOProviderImpl oktaSdwanSSOProviderImpl;
	
	@MockBean
	private OktaSdwanRSSSO3ProviderImpl oktaSdwanRSSSO3ProviderImpl;
	
	@MockBean
	private OktaSdwanRSSSOProviderImpl OktaSdwanRSSSOProviderImpl;
	
	@MockBean
	private OktaSSOProviderHelper oktaSSOProviderHelper;
	
	@MockBean
	private OktaMalblockSSOProviderImpl oktaMalblockSSOProviderImpl;
	
	@MockBean
	private OktaCbCoreSSOProviderImpl oktaCbCoreSSOProviderImpl;
	
	@MockBean
	private OktaManagedSecuritySSOProviderImpl oktaManagedSecuritySSOProviderImpl;
	
	@MockBean
	private OktaSmartBillSSOProviderImpl oktaSmartBillSSOProviderImpl;
	
	@MockBean
	private OktaUWPSSOProviderImpl oktaUWPSSOProviderImpl;
	
	@MockBean
	private OktaManagedRouterSecuritySSOProviderImpl oktaManagedRouterSecuritySSOProviderImpl;
	
	@MockBean
	private VSBAppDirectProviderImpl vSBAppDirectProviderImpl;
	
	@MockBean
	private ServiceResponseBuilder responseBuilder;

	@MockBean
	private OktaSbwgSSoproviderImpl oktaSbwgSSoproviderImpl;
	
	@Test
	public void testValidateTokenForTokenNotEmpty() throws Exception {
		ResponseBuilder responseBuilder = Response.status(HttpStatus.SC_OK);
		String ttl=""+System.currentTimeMillis();
		Token token=new Token("id1234", "hitsvi.sutar@cox.com", TokenType.ACCESS, ttl, "132344");
		responseBuilder.entity(token);
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		boolean validateToken=identityService.validateToken("132344", "cl12334", "s132244", "hitsvi.sutar@cox.com",false,Collections.singletonMap("", ""),false);
		
	}
	
	@Test
	public void testValidateTokenTtlEmpty() throws Exception {
		ResponseBuilder responseBuilder = Response.status(HttpStatus.SC_OK);
		String ttl="1349";
		Token token=new Token("id1234", "hitsvi.sutar@cox.com", TokenType.ACCESS, ttl, "132344");
		responseBuilder.entity(token);
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		identityService.validateToken("132344", "cl12334", "s132244", "hitsvi.sutar@cox.com",false,Collections.singletonMap("", ""),false);
	}
	
	@Test
	public void testValidateTokenForTokenEqualBearerToken() throws Exception {
		ResponseBuilder responseBuilder = Response.status(HttpStatus.SC_OK);
		String ttl=""+System.currentTimeMillis();
		Token token=new Token("id1234", "hitsvi.sutar@cox.com", TokenType.ACCESS, ttl, "132344");
		responseBuilder.entity(token);
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		identityService.validateToken("555555", "cl12334", "s132244", "hitsvi.sutar@cox.com",false,Collections.singletonMap("", ""),false);
	}
	
	@Test
	public void testValidateTokenForTokenNotEmpty4() throws Exception {
		ResponseBuilder responseBuilder = Response.status(HttpStatus.SC_OK);
		String ttl=""+System.currentTimeMillis();
		Token token=new Token("id1234", "abc@cox.com", TokenType.ACCESS, ttl, "132344");
		responseBuilder.entity(token);
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		identityService.validateToken("132344", "cl12334", "s132244", "hitsvi.sutar@cox.com",false,Collections.singletonMap("", ""),false);
	}
	
	@Test
	public void testValidateTokenForTokenNotEmpty5() throws Exception {
		ResponseBuilder responseBuilder = Response.status(HttpStatus.SC_OK);
		String ttl=""+System.currentTimeMillis();
		Token token=new Token("id1234", "abc@cox.com", TokenType.ACCESS, ttl, "132344");
		token.setTooken(null);
		responseBuilder.entity(token);
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		identityService.validateToken("132344", "cl12334", "s132244", "hitsvi.sutar@cox.com",false,Collections.singletonMap("", ""),false);
	}
	
	@Test
	public void testValidateTokenForTokenEmpty() throws Exception {
		ResponseBuilder responseBuilder = Response.status(HttpStatus.SC_OK);
		responseBuilder.entity(null);
		IntrospectTokenResponse oktaTokenResponse=new IntrospectTokenResponse();
		oktaTokenResponse.setUsername("hitsvi.sutar@cox.com");
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		Mockito.when( oktaProvider.introspectToken(Mockito.anyString(), Mockito.anyString())).thenReturn(oktaTokenResponse);
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		boolean validateToken=identityService.validateToken("132344", "cl12334", "s132244", "hitsvi.sutar@cox.com",false,Collections.singletonMap("", ""),false);
		Assert.assertTrue(validateToken);
	}
	
	@Test
	public void testValidateTokenForTokenEmptyUsername() throws Exception {
		ResponseBuilder responseBuilder = Response.status(HttpStatus.SC_OK);
		responseBuilder.entity(null);
		IntrospectTokenResponse oktaTokenResponse=new IntrospectTokenResponse();
		oktaTokenResponse.setUsername("abc@cox.com");
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		Mockito.when( oktaProvider.introspectToken(Mockito.anyString(), Mockito.anyString())).thenReturn(oktaTokenResponse);
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		identityService.validateToken("132344", "cl12334", "s132244", "hitsvi.sutar@cox.com",false,Collections.singletonMap("", ""),false);
	}
	
	@Test
	public void testValidateTokenForTokenNotEmpty2() throws Exception {
		ResponseBuilder responseBuilder = Response.status(HttpStatus.SC_OK);
		String ttl=""+System.currentTimeMillis();
		Token token=new Token("id1234", "hitsvi.sutar@cox.com", TokenType.ACCESS, ttl, "132344");
		token.setTooken(null);
		responseBuilder.entity(token);
		IntrospectTokenResponse oktaTokenResponse=new IntrospectTokenResponse();
		oktaTokenResponse.setUsername("hitsvi.sutar@cox.com");
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		Mockito.when( oktaProvider.introspectToken(Mockito.anyString(), Mockito.anyString())).thenReturn(oktaTokenResponse);
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		boolean validateToken=identityService.validateToken("132344", "cl12334", "s132244", "hitsvi.sutar@cox.com",false,Collections.singletonMap("", ""),false);
		Assert.assertTrue(validateToken);
	}
	
	@Test
	public void testValidateTokenForTokenNotEmpty3() throws Exception {
		ResponseBuilder responseBuilder = Response.status(HttpStatus.SC_OK);
		String ttl=""+System.currentTimeMillis();
		Token token=new Token("id1234", "hitsvi.sutar@cox.com", TokenType.ACCESS, ttl, "132344");
		token.setTooken("weferfet5terfre");
		responseBuilder.entity(token);
		IntrospectTokenResponse oktaTokenResponse=new IntrospectTokenResponse();
		oktaTokenResponse.setUsername("hitsvi.sutar@cox.com");
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		Mockito.when( oktaProvider.introspectToken(Mockito.anyString(), Mockito.anyString())).thenReturn(oktaTokenResponse);
		Mockito.when(cacheAdaptor.processCacheData(Mockito.any(),Mockito.anyString())).thenReturn(responseBuilder.build());
		boolean validateToken=identityService.validateToken("132344", "cl12334", "s132244", "hitsvi.sutar@cox.com",false,Collections.singletonMap("", ""),false);
		Assert.assertTrue(validateToken);
	}
	

	
	/**
	 * @throws Exception
	 */
	@Test
	public void testSamlPersistenceTOLLFREE() throws Exception {
		ResponseStatus responseStatus = new ResponseStatus();
	 	Mockito.when(oktaAlarmSSOProviderImpl.samlPersistence(Mockito.any(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(responseStatus);
	 	identityService.samlPersistence(SSOPageIdType.TOLLFREE, "userGuid_abcd", "sessionId_abcd", "accountNo_abcd","sampleuser");
	}
	
	@Test
	public void testSamlPersistenceIBILL() throws Exception {
		ResponseStatus responseStatus = new ResponseStatus();
	 	Mockito.when(oktaAlarmSSOProviderImpl.samlPersistence(Mockito.any(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(responseStatus);
	 	identityService.samlPersistence(SSOPageIdType.IBILL, "userGuid_abcd", "sessionId_abcd", "accountNo_abcd","sampleuser");
	}
	
	@Test
	public void testSamlPersistenceTOLLFREETest() throws Exception {
		ResponseStatus responseStatus = new ResponseStatus();
	 	Mockito.when(oktaAlarmSSOProviderImpl.samlPersistence(Mockito.any(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(responseStatus);
	 	identityService.samlPersistence(SSOPageIdType.UNIFIED_WIFI_PORTAL, "userGuid_abcd", "sessionId_abcd", "accountNo_abcd","sampleuser");
	}
	@Test
	public void testSamlPersistenceDefaultTest() throws Exception {
		ResponseStatus responseStatus = new ResponseStatus();
	 	Mockito.when(oktaAlarmSSOProviderImpl.samlPersistence(Mockito.any(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(responseStatus);
	 	identityService.samlPersistence(SSOPageIdType.UNIFIED_WIFI_PORTAL, "userGuid_abcd", "sessionId_abcd", "accountNo_abcd","sampleuser");
	}
	
	@Test
	public void testSamlPersistenceSDWAN() throws Exception {
		ResponseStatus responseStatus = new ResponseStatus();
	 	Mockito.when(oktaSdwanSSOProviderImpl.samlPersistence(Mockito.any(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(responseStatus);
	 	ResponseStatus response=identityService.samlPersistence(SSOPageIdType.SDWAN, "userGuid_abcd", "sessionId_abcd", "accountNo_abcd","sampleuser");
	 	Assert.assertNotNull(response);
	}
	
	@Test
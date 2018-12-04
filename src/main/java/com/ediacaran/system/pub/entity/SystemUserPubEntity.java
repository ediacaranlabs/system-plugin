package com.ediacaran.system.pub.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Constructor;
import org.brandao.brutos.annotation.MappingTypes;

import com.ediacaran.system.Constants;
import com.ediacaran.system.entity.SystemUser;
import com.ediacaran.system.registry.SystemUserRegistry;
import com.ediacaran.system.util.PasswordUtil;

import br.com.uoutec.application.EntityContext;
import br.com.uoutec.application.validation.CommonValidation;
import br.com.uoutec.i18n.MessageBundleUtils;
import br.com.uoutec.i18n.MessageLocale;
import br.com.uoutec.i18n.MessagesConstants;
import br.com.uoutec.i18n.ValidationException;
import br.com.uoutec.pub.entity.AbstractPubEntity;
import br.com.uoutec.pub.entity.DataValidation;
import br.com.uoutec.pub.entity.Equal;
import br.com.uoutec.pub.entity.IdValidation;

public class SystemUserPubEntity extends AbstractPubEntity<SystemUser>{

	private static final long serialVersionUID = 1391868122764939558L;

	@NotNull(groups = IdValidation.class)
	private Integer id;

	@Basic(bean = Constants.SYSTEM_USER_BEAN_NAME, 
			scope = Constants.SYSTEM_USER_SCOPE, mappingType = MappingTypes.VALUE)
	private SystemUser systemUser;
	
	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String firstName;

	@NotNull(groups={DataValidation.class})
	@Pattern(regexp=CommonValidation.EMAIL, groups={DataValidation.class})
	private String email;
	
	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String lastName;
	
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String organization;

	@NotNull(groups={CountryValidatorGroup.class, DataValidation.class})
	private CountryPubEntity country;
	
	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Pattern(regexp=CommonValidation.ADDRESS_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String address;
	
	@Pattern(regexp=CommonValidation.ADDRESS_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String complement;
	
	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String city;
	
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String region;
	
	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Pattern(regexp=CommonValidation.ZIP, groups={DataValidatorGroup.class, DataValidation.class})
	private String zip;
	
	@Pattern(regexp=CommonValidation.PHONE, groups={DataValidatorGroup.class, DataValidation.class})
	private String phone;

	@Equal(field="passwordConf")
	@NotNull(groups={DataValidation.class, PasswordValidatorGroup.class})
	@Pattern(regexp=PasswordUtil.PASSWORD, groups={DataValidation.class, PasswordValidatorGroup.class})
	private String password;

	@NotNull(groups={DataValidation.class, PasswordValidatorGroup.class})
	private String passwordConf;
	
	@Constructor
	public SystemUserPubEntity(){
	}
	
	public SystemUserPubEntity(SystemUser e){
		this.address = e.getAddress();
		this.city = e.getCity();
		this.complement = e.getComplement();
		this.country = e.getCountry() == null? null : new CountryPubEntity(e.getCountry());
		this.email = e.getEmail();
		this.firstName = e.getFirstName();
		this.id = e.getId();
		this.lastName = e.getLastName();
		this.organization = e.getOrganization();
		this.phone = e.getPhone();
		this.region = e.getRegion();
		this.zip = e.getZip();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SystemUser getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public CountryPubEntity getCountry() {
		return country;
	}

	public void setCountry(CountryPubEntity country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConf() {
		return passwordConf;
	}

	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}

	@Override
	protected boolean isEqualId(SystemUser instance) throws Throwable {
		return instance.getId() <= 0? 
				this.id == null :
				this.id != null && instance.getId() == this.id;
	}

	@Override
	protected boolean hasId(SystemUser instance) throws Throwable {
		return instance.getId() > 0;
	}

	@Override
	protected SystemUser reloadEntity() throws Throwable {
		SystemUserRegistry registry = EntityContext.getEntity(SystemUserRegistry.class);
		return registry.findById(this.id);
	}

	@Override
	protected void throwReloadEntityFail() throws Throwable {
		String error = MessageBundleUtils
				.getMessageResourceString(
						MessagesConstants.RESOURCE_BUNDLE,
						MessagesConstants.system.error.not_equal_message, 
						new Object[]{"id"},
						MessageLocale.getLocale());
		throw new ValidationException(error);
	}

	@Override
	protected SystemUser createNewInstance() throws Throwable {
		return new SystemUser();
	}

	@Override
	protected void copyTo(SystemUser o, boolean reload, boolean override,
			boolean validate) throws Throwable {
		
		o.setEmail(this.email);
		
		if(this.password != null){
			o.setPassword(this.password);
		}
		
		o.setFirstName(this.firstName);
		o.setLastName(this.lastName);
		o.setAddress(this.address);
		o.setCity(this.city);
		o.setComplement(this.complement);
		o.setCountry(this.getCountry() == null? null : this.country.rebuild(reload, false, true));
		o.setOrganization(this.organization);
		o.setPhone(this.phone);
		o.setRegion(this.region);
		o.setZip(this.zip);
	}

	/*
	protected void validate(boolean reload, boolean override, 
			Class<?> ... groups) throws ValidationException{
		
		super.validate(reload, override, groups);
		
		if(this.systemUser.getCountry() != null){
			if(this.country == null || this.country.getId() == null|| 
			   this.systemUser.getCountry().getId() != this.country.getId()){
				throw new ValidationException(
					"país inválido: " + (country == null? "null" : country.getId()));
			}
		}
	}
	 */
	
	public interface CountryValidatorGroup{
	}
	
	public interface PasswordValidatorGroup{
	}
	
	public interface DataValidatorGroup{
	}
	
}

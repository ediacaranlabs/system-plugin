package com.ediacaran.system.pub.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ediacaran.system.entity.Country;
import com.ediacaran.system.pub.entity.SystemUserPubEntity.DataValidatorGroup;
import com.ediacaran.system.registry.CountryRegistry;

import br.com.uoutec.application.EntityContext;
import br.com.uoutec.application.validation.CommonValidation;
import br.com.uoutec.pub.entity.AbstractPubEntity;
import br.com.uoutec.pub.entity.DataValidation;
import br.com.uoutec.pub.entity.IdValidation;

public class CountryPubEntity extends AbstractPubEntity<Country>{

	private static final long serialVersionUID = -3736040334614132134L;

	@NotNull(groups = IdValidation.class)
	private Integer id;

	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String name;

	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Size(min=2,max=2, groups={DataValidatorGroup.class, DataValidation.class})
	private String isoAlpha2;

	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Size(min=2,max=2, groups={DataValidatorGroup.class, DataValidation.class})
	private String isoAlpha3;

	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Size(min=2,max=6, groups={DataValidatorGroup.class, DataValidation.class})
	private String tld;

	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Size(min=2,max=6, groups={DataValidatorGroup.class, DataValidation.class})
	private String iso4217;

	@NotNull
	private RegionPubEntity region;

	public CountryPubEntity(){
	}
	
	public CountryPubEntity(Country e){
		this.id = e.getId();
		this.iso4217 = e.getIso4217();
		this.isoAlpha2 = e.getIsoAlpha2();
		this.isoAlpha3 = e.getIsoAlpha3();
		this.name = e.getName();
		this.region = e.getRegion() == null? null : new RegionPubEntity(e.getRegion());
		this.tld = e.getTld();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsoAlpha2() {
		return isoAlpha2;
	}

	public void setIsoAlpha2(String isoAlpha2) {
		this.isoAlpha2 = isoAlpha2;
	}

	public String getIsoAlpha3() {
		return isoAlpha3;
	}

	public void setIsoAlpha3(String isoAlpha3) {
		this.isoAlpha3 = isoAlpha3;
	}

	public String getTld() {
		return tld;
	}

	public void setTld(String tld) {
		this.tld = tld;
	}

	public String getIso4217() {
		return iso4217;
	}

	public void setIso4217(String iso4217) {
		this.iso4217 = iso4217;
	}

	public RegionPubEntity getRegion() {
		return region;
	}

	public void setRegion(RegionPubEntity region) {
		this.region = region;
	}

	@Override
	protected boolean isEqualId(Country instance) throws Throwable {
		return this.id == null? instance.getId() <= 0 : instance.getId() == this.id.intValue();
	}

	@Override
	protected boolean hasId(Country instance) throws Throwable {
		return instance.getId() > 0;
	}

	@Override
	protected Country reloadEntity() throws Throwable {
		
		if(this.id == null || this.id == 0){
			return null;
		}
		
		CountryRegistry registry = EntityContext.getEntity(CountryRegistry.class);
		return registry.getCountry(this.id);
	}

	@Override
	protected void throwReloadEntityFail() throws Throwable {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Country createNewInstance() throws Throwable {
		return new Country();
	}

	@Override
	protected void copyTo(Country o, boolean reload, boolean override,
			boolean validate) throws Throwable {
		o.setId(this.id == null? 0 : this.id);
		o.setIso4217(this.iso4217);
		o.setIsoAlpha2(this.isoAlpha2);
		o.setIsoAlpha3(this.isoAlpha3);
		o.setName(this.name);
		o.setRegion(this.region == null? null :  this.region.rebuild(reload, override, validate));
		o.setTld(this.tld);
	}
	
}
